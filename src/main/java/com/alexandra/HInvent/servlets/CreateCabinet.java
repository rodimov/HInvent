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

@WebServlet("/create_cab")
public class CreateCabinet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Localization.setLocal(request, response);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || user.getType() != 0) {
            response.sendRedirect("/");
        } else {
            request.setAttribute("user_type", user.getType());
            request.setAttribute("page_type", "create_cabinet");

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

        Cabinet cabinet = new Cabinet();
        cabinet.setName(name);
        cabinet.setDescription(description);

        cabinetService.saveCabinet(cabinet);

        response.sendRedirect("/");
    }
}
