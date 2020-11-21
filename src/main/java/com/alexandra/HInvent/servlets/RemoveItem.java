package com.alexandra.HInvent.servlets;

import com.alexandra.HInvent.entities.Item;
import com.alexandra.HInvent.entities.User;
import com.alexandra.HInvent.services.ItemService;
import com.alexandra.HInvent.utils.Localization;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/remove_item")
public class RemoveItem extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Localization.setLocal(request, response);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || user.getType() != 0) {
            response.sendRedirect("/sign_in");
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

            if (item == null) {
                response.sendRedirect("/");
                return;
            }

            item.setStatus("Списано");
            itemService.updateItem(item);

            response.sendRedirect("/cab?id=" + item.getCabinet().getId());
        }
    }
}
