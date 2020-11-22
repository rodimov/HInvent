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
import java.util.LinkedList;
import java.util.List;

@WebServlet("/all_users")
public class AllUsers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Localization.setLocal(request, response);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("/sign_in");
        } else {
            if (user.getType() != 0) {
                response.sendRedirect("/");
                return;
            }

            UserService userService = new UserService();
            List<User> users = userService.findAllUsers();
            List<User> filteredUsers = new LinkedList<>();

            for (User filterUser : users) {
                if (filterUser.isBlocked() == 0) {
                    filteredUsers.add(filterUser);
                }
            }

            request.setAttribute("username", user.getSecondName() + " " + user.getFirstName());
            request.setAttribute("user_type", user.getType());
            request.setAttribute("page_type", "show_users");
            request.setAttribute("users", filteredUsers);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/index.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
