package com.alexandra.HInvent.servlets;

import com.alexandra.HInvent.entities.File;
import com.alexandra.HInvent.entities.Item;
import com.alexandra.HInvent.entities.User;
import com.alexandra.HInvent.services.FileService;
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
import java.util.LinkedList;
import java.util.List;

@WebServlet("/item_files")
public class ItemFiles extends HttpServlet {
    public static final String SAVE_DIRECTORY = "uploadedItemsFiles";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Localization.setLocal(request, response);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
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

            if(item == null) {
                response.sendRedirect("/");
                return;
            }

            FileService fileService = new FileService();
            List<File> files = fileService.findFilesByItem(item);
            List<File> filteredFiles = new LinkedList<>();

            String appPath = request.getServletContext().getRealPath("");
            appPath = appPath.replace('\\', '/');

            for (File file : files) {
                String fullSavePath = null;

                if (appPath.endsWith("/")) {
                    fullSavePath = appPath + SAVE_DIRECTORY + "/" + file.getId();
                } else {
                    fullSavePath = appPath + "/" + SAVE_DIRECTORY + "/" + file.getId();
                }

                java.io.File filePointer = new java.io.File(fullSavePath + "/" + file.getName());

                if (filePointer.exists()) {
                    filteredFiles.add(file);
                }
            }

            request.setAttribute("username", user.getSecondName() + " " + user.getFirstName());
            request.setAttribute("user_type", user.getType());
            request.setAttribute("files", filteredFiles);
            request.setAttribute("page_type", "item_files");

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/index.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
