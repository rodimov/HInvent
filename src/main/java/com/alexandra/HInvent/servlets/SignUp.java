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

@WebServlet("/sign_up")
public class SignUp extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Localization.setLocal(request, response);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || user.getType() != 0) {
            response.sendRedirect("/");
        } else {
            request.setAttribute("username", user.getSecondName() + " " + user.getFirstName());
            request.setAttribute("user_type", user.getType());
            request.setAttribute("page_type", "sign_up");

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/index.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Localization.setLocal(request, response);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        UserService userService = new UserService();

        if (user == null || user.getType() != 0) {
            response.sendRedirect("/");
            return;
        }

        String firstName = request.getParameter("first_name");
        String secondName = request.getParameter("second_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (userService.findUserByEmail(email) != null) {
            String message = "Данный email уже зарегистрирован!";
            request.setAttribute("user_type", user.getType());
            request.setAttribute("page_type", "sign_up");
            request.setAttribute("message", message);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/index.jsp");
            requestDispatcher.forward(request, response);
            return;
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setFirstName(firstName);
        newUser.setSecondName(secondName);
        newUser.setPassword(password);
        newUser.setType(1);
        newUser.setBlockState(0);

        userService.saveUser(newUser);

        response.sendRedirect("/");
    }
}
