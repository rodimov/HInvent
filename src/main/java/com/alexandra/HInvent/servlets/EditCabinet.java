package com.alexandra.HInvent.servlets;

import com.alexandra.HInvent.entities.Cabinet;
import com.alexandra.HInvent.entities.User;
import com.alexandra.HInvent.services.CabinetService;
import com.alexandra.HInvent.utils.Localization;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/edit_cab")
public class EditCabinet extends HttpServlet {
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

            request.setAttribute("user_type", user.getType());
            request.setAttribute("page_type", "edit_cabinet");
            request.setAttribute("cabinet", cabinet);

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

        if (user == null || user.getType() != 0) {
            response.sendRedirect("/");
            return;
        }

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String cabinetIdParamentr = request.getParameter("cabinet_id");

        if (name.isEmpty()) {
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

        if (cabinet == null) {
            response.sendRedirect("/");
            return;
        }

        cabinet.setName(name);
        cabinet.setDescription(description);

        cabinetService.updateCabinet(cabinet);

        response.sendRedirect("/");
    }
}
