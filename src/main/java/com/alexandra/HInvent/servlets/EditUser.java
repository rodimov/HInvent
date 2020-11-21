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

@WebServlet("/edit_user")
public class EditUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Localization.setLocal(request, response);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String idParameter = request.getParameter("id");

        if (user == null || user.getType() != 0) {
            response.sendRedirect("/all_users");
        } else {
            request.setAttribute("user_type", user.getType());
            request.setAttribute("page_type", "edit_user");

            UserService userService = new UserService();
            User userEditable = null;

            if (idParameter != null) {
                try {
                    userEditable = userService.findUser(Integer.parseInt(idParameter));
                } catch (Exception e) {
                    response.sendRedirect("/all_users");
                    return;
                }
            }

            if(userEditable == null || userEditable.getType() == 0) {
                response.sendRedirect("/all_users");
                return;
            }

            request.setAttribute("user_editable", userEditable);

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
            response.sendRedirect("/all_users");
            return;
        }

        String userEditId = request.getParameter("user_edit_id");
        String firstName = request.getParameter("first_name");
        String secondName = request.getParameter("second_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User userEditable = null;

        if (userEditId != null) {
            try {
                userEditable = userService.findUser(Integer.parseInt(userEditId));
            } catch (Exception e) {
                response.sendRedirect("/all_users");
                return;
            }
        }

        if(userEditable == null || userEditable.getType() == 0) {
            response.sendRedirect("/all_users");
            return;
        }

        User userByEmail = userService.findUserByEmail(email);

        if (userByEmail != null && !userByEmail.getEmail().equals(userEditable.getEmail())) {
            String message = "Данный email уже зарегистрирован!";
            request.setAttribute("user_type", user.getType());
            request.setAttribute("page_type", "edit_user");
            request.setAttribute("message", message);
            request.setAttribute("user_editable", userEditable);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/index.jsp");
            requestDispatcher.forward(request, response);
            return;
        }

        userEditable.setEmail(email);
        userEditable.setFirstName(firstName);
        userEditable.setSecondName(secondName);
        userEditable.setPassword(password);

        userService.updateUser(userEditable);

        response.sendRedirect("/all_users");
    }
}
