package org.example.profile.controllers;

import org.example.profile.database.DatabaseAccess;
import org.example.profile.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "EditController", urlPatterns = "/userEdit")
@MultipartConfig
public class EditController extends HttpServlet {
    /**
     * @param request question
     * @param response answer
     */
    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        User user = User.fromRequestParameters(request);
        DatabaseAccess databaseAccess;
        try {
            databaseAccess = new DatabaseAccess();
            Part filePart = request.getPart("file");
            if(filePart!=null){
                user.photoStream = filePart.getInputStream();
            }
            databaseAccess.editUser(user);
            user = databaseAccess.getUser(user.id);
            List<String> friends = databaseAccess.getFriends(user.id);
            if(!friends.isEmpty())
                request.setAttribute("friends", friends);
            user.setAsRequestAttributes(request);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String url = "/WEB-INF/views/userProfile.jsp";
        request.getRequestDispatcher(url).forward(request, response);
    }

}