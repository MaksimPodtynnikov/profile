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

@WebServlet(name = "FriendsController", urlPatterns = "/addFriend")
public class FriendsController extends HttpServlet {
    /**
     * @param request question
     * @param response answer
     */
    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        DatabaseAccess databaseAccess;
        try {
            Cookie cookie=null;
                for(Cookie c: request.getCookies()) {
                    if("id".equals(c.getName())) {
                        cookie = c;
                        break;
                    }

                }
            databaseAccess = new DatabaseAccess();
            assert cookie != null;
            databaseAccess.addFriend(Integer.parseInt(cookie.getValue()), Integer.parseInt(request.getParameter("idF")));
            User user = databaseAccess.getUser(Integer.parseInt(request.getParameter("idF")));
            user.setAsRequestAttributes(request);
            response.setCharacterEncoding("UTF-8");
            request.getRequestDispatcher("/WEB-INF/views/profile.jsp").include(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}