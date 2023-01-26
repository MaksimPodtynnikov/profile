package org.example.profile.controllers;

import org.example.profile.database.DatabaseAccess;
import org.example.profile.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "EnterController", urlPatterns = "/user")
public class EnterController extends HttpServlet {
    /**
     * @param request question
     * @param response answer
     */
    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        User user;
        DatabaseAccess databaseAccess;
        try {
            databaseAccess = new DatabaseAccess();
            user = databaseAccess.getUser(request.getParameter("nickname"),request.getParameter("password"));
            List<String> friends = databaseAccess.getFriends(user.id);
            List<String> violations=new ArrayList<>();
            if(user==null)
                violations.add("Пользователь не найден");
            else {
                databaseAccess.addUserEnters(user.enters,user.id);
                user.enters++;
                user.setAsRequestAttributes(request);
                response.addCookie(new Cookie("id",String.valueOf(user.id)));
                if(!friends.isEmpty())
                    request.setAttribute("friends", friends);
            }
            String url = determineUrl(violations);
            request.getRequestDispatcher(url).forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param violations list
     * @return url
     */
    private String determineUrl(List<String> violations) {
        if (!violations.isEmpty()) {
            return "/WEB-INF/views/enter.jsp";
        } else {
            return "/WEB-INF/views/userProfile.jsp";
        }
    }
}
