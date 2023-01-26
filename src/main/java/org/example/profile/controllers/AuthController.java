package org.example.profile.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "AuthController", urlPatterns = "/auth")
public class AuthController extends HttpServlet {
    /**
     * @param request question
     * @param response answer
     */
    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String url = "/WEB-INF/views/enter.jsp";
        if(Objects.equals(request.getParameter("auth"), "reg"))
            url = "/WEB-INF/views/register.jsp";
        else {
            request.setAttribute("nickname","");
            request.setAttribute("password","");
        }
        request.getRequestDispatcher(url).forward(request, response);
    }
}
