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

@WebServlet("/add_item")
public class AddItem extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Localization.setLocal(request, response);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || user.getType() != 0) {
            response.sendRedirect("/");
        } else {
            CabinetService cabinetService = new CabinetService();

            String idParametr = request.getParameter("id");
            Cabinet cabinet = null;

            if (idParametr != null) {
                try {
                    cabinet = cabinetService.findCabinet(Integer.parseInt(idParametr));
                } catch (Exception e) {
                    response.sendRedirect("/");
                    return;
                }
            }

            if(cabinet == null) {
                response.sendRedirect("/");
                return;
            }

            UserService userService = new UserService();
            List<User> users = userService.findAllUsers();

            request.setAttribute("user_type", user.getType());
            request.setAttribute("page_type", "add_item");
            request.setAttribute("cabinet", cabinet);
            request.setAttribute("users", users);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/index.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Localization.setLocal(request, response);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        CabinetService cabinetService = new CabinetService();
        UserService userService = new UserService();

        if (user == null || user.getType() != 0) {
            response.sendRedirect("/");
            return;
        }

        String name = request.getParameter("name");
        String cabinetIdParamentr = request.getParameter("cabinet_id");
        String userIdParamentr = request.getParameter("user_id");
        String coastParametr = request.getParameter("coast");
        String picture = request.getParameter("pic");

        if (name.isEmpty() || picture.isEmpty()) {
            response.sendRedirect("/");
            return;
        }

        Cabinet cabinet = null;

        if (cabinetIdParamentr != null) {
            try {
                cabinet = cabinetService.findCabinet(Integer.parseInt(cabinetIdParamentr));
            } catch (Exception e) {
                response.sendRedirect("/");
                return;
            }
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

        Item item = new Item();
        item.setCabinet(cabinet);
        item.setCoast(Integer.parseInt(coastParametr));
        item.setPicture(picture);
        item.setName(name);
        item.setUser(userResponsible);
        item.setStatus("В эксплуатации");

        ItemService itemService = new ItemService();
        itemService.saveItem(item);

        response.sendRedirect("/cab?id=" + cabinetIdParamentr);
    }
}
