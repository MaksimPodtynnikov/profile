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

@WebServlet(name = "RegisterController", urlPatterns = "/userRegister")
@MultipartConfig
public class RegisterController extends HttpServlet {
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
        Part filePart = request.getPart("photo");
        user.photoStream = filePart.getInputStream();
        List<String> violations;
        violations = user.validate();
        try {
        DatabaseAccess databaseAccess=new DatabaseAccess();
        if(existing(user.nickname))
            violations.add("Логин уже занят");
        if(violations.isEmpty())
            user.id=databaseAccess.addUser(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        user.setAsRequestAttributes(request);
        if (!violations.isEmpty()) {
            request.setAttribute("violations", violations);
        }
        else{
            request.setAttribute("nickname", user.nickname);
            request.setAttribute("password", user.password);
        }

        String url = determineUrl(violations);
        request.getRequestDispatcher(url).forward(request, response);
    }

    /**
     * @param userName name of user
     * @return existing same names in database
     */
    public boolean existing(String userName) throws SQLException {
        DatabaseAccess databaseAccess=new DatabaseAccess();
        for (String name: databaseAccess.getNames()) {
            if(userName.equals(name))
                return true;
        }
        return false;
    }

    /**
     * @param violations list
     * @return url
     */
    private String determineUrl(List<String> violations) {
        if (!violations.isEmpty()) {
            return "/WEB-INF/views/register.jsp";
        } else {
            return "/WEB-INF/views/enter.jsp";
        }
    }
}
