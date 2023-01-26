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
import java.util.List;
import java.util.Objects;

@WebServlet(name = "UserController", urlPatterns = "/profile")
public class UserController extends HttpServlet {
    /**
     * @param request question
     * @param response answer
     */
    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            Cookie cookie=null;
            for(Cookie c: request.getCookies()) {
                if("id".equals(c.getName())) {
                    cookie = c;
                    break;
                }
            }
            DatabaseAccess databaseAccess = new DatabaseAccess();
            User user = databaseAccess.getUser(Integer.parseInt(request.getParameter("id")));
            user.setAsRequestAttributes(request);
            List<String> friends = databaseAccess.getFriends(user.id);
            if(!friends.isEmpty())
                request.setAttribute("friends", friends);
            assert cookie != null;
            if(!Objects.equals(cookie.getValue(), request.getParameter("id"))) {
                request.setAttribute("idF",request.getParameter("id"));
                request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
            }
            else request.getRequestDispatcher("/WEB-INF/views/userProfile.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
