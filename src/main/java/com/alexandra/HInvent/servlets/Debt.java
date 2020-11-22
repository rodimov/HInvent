package com.alexandra.HInvent.servlets;

import com.alexandra.HInvent.entities.Item;
import com.alexandra.HInvent.entities.User;
import com.alexandra.HInvent.services.ItemService;
import com.alexandra.HInvent.utils.Localization;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/debt")
public class Debt extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Localization.setLocal(request, response);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("/sign_in");
        } else {
            ItemService itemService = new ItemService();
            List<Item> items = itemService.findItemsByUserDebt(user);

            request.setAttribute("username", user.getSecondName() + " " + user.getFirstName());
            request.setAttribute("user_type", user.getType());
            request.setAttribute("items", items);
            request.setAttribute("page_type", "show_items");

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/index.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
