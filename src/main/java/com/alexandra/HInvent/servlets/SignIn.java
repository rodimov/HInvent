package com.alexandra.HInvent.servlets;

import com.alexandra.HInvent.entities.User;
import com.alexandra.HInvent.services.UserService;
import com.alexandra.HInvent.utils.Localization;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/sign_in")
public class SignIn extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Localization.setLocal(request, response);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/sign_in.jsp");
            requestDispatcher.forward(request, response);
        } else {
            response.sendRedirect("/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Localization.setLocal(request, response);
        HttpSession session = request.getSession();

        if ((User) session.getAttribute("user") != null) {
            response.sendRedirect("/");
            return;
        }

        UserService userService = new UserService();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userService.findUserByEmail(email);

        if (user != null && user.getPassword().equals(password) && user.isBlocked() == 0) {
            session.setAttribute("user", user);
            response.sendRedirect("/");
        } else if (user != null && user.isBlocked() != 0) {
            String message = "Пользователь заблокирован!";
            request.setAttribute("message", message);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/sign_in.jsp");
            requestDispatcher.forward(request, response);
        } else {
            String message = "Неправильный email или пароль!";
            request.setAttribute("message", message);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/sign_in.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
