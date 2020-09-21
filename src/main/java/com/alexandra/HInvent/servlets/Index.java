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
import java.util.List;

@WebServlet("/")
public class Index extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Localization.setLocal(request, response);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            request.setAttribute("user_type", user.getType());
            request.setAttribute("page_type", "index");

            CabinetService cabinetService = new CabinetService();
            List<Cabinet> cabinets = cabinetService.findAllCabinets();
            request.setAttribute("cabinets", cabinets);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/index.jsp");
            requestDispatcher.forward(request, response);
        } else {
            response.sendRedirect("/sign_in");
        }
    }
}
