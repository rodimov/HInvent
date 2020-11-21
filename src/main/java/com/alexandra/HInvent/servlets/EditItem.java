package com.alexandra.HInvent.servlets;

import com.alexandra.HInvent.entities.Cabinet;
import com.alexandra.HInvent.entities.Item;
import com.alexandra.HInvent.entities.User;
import com.alexandra.HInvent.services.CabinetService;
import com.alexandra.HInvent.services.ItemService;
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
import java.util.List;

@WebServlet("/edit_item")
public class EditItem extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Localization.setLocal(request, response);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || user.getType() != 0) {
            response.sendRedirect("/");
        } else {
            ItemService itemService = new ItemService();

            String idParametr = request.getParameter("id");
            Item item = null;

            if (idParametr != null) {
                try {
                    item = itemService.findItem(Integer.parseInt(idParametr));
                } catch (Exception e) {
                    response.sendRedirect("/");
                    return;
                }
            }

            if(item == null) {
                response.sendRedirect("/");
                return;
            }

            UserService userService = new UserService();
            List<User> users = userService.findAllUsers();

            request.setAttribute("page_type", "edit_item");
            request.setAttribute("item", item);
            request.setAttribute("user", user);
            request.setAttribute("users", users);
            request.setAttribute("user_type", user.getType());

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/index.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Localization.setLocal(request, response);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ItemService itemService = new ItemService();
        CabinetService cabinetService = new CabinetService();
        UserService userService = new UserService();

        if (user == null || user.getType() != 0) {
            response.sendRedirect("/");
            return;
        }

        String itemIdParamentr = request.getParameter("item_id");

        String name = request.getParameter("name");
        String userIdParamentr = request.getParameter("user_id");
        String coastParametr = request.getParameter("coast");
        String picture = request.getParameter("pic");
        String status = request.getParameter("status");

        if (name.isEmpty() || picture.isEmpty() || status.isEmpty()) {
            response.sendRedirect("/");
            return;
        }

        if (!(status.equals("Списано") || status.equals("В эксплуатации")
                || status.equals("Недостача"))) {
            response.sendRedirect("/");
            return;
        }

        User userResponsible = null;

        if (userIdParamentr != null) {
            try {
                userResponsible = userService.findUser(Integer.parseInt(userIdParamentr));
            } catch (Exception e) {
                response.sendRedirect("/");
                return;
            }
        }

        Item item = null;

        if (itemIdParamentr != null) {
            try {
                item = itemService.findItem(Integer.parseInt(itemIdParamentr));
            } catch (Exception e) {
                response.sendRedirect("/");
                return;
            }
        }

        if (item == null) {
            response.sendRedirect("/");
            return;
        }

        item.setCoast(Integer.parseInt(coastParametr));
        item.setPicture(picture);
        item.setName(name);
        item.setUser(userResponsible);
        item.setStatus(status);

        itemService.updateItem(item);

        response.sendRedirect("/cab?id=" + item.getCabinet().getId());
    }
}
