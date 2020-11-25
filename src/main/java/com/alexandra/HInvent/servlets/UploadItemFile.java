package com.alexandra.HInvent.servlets;

import com.alexandra.HInvent.entities.File;
import com.alexandra.HInvent.entities.Item;
import com.alexandra.HInvent.entities.User;
import com.alexandra.HInvent.services.FileService;
import com.alexandra.HInvent.services.ItemService;
import com.alexandra.HInvent.utils.Localization;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/upload_item_file")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UploadItemFile extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String SAVE_DIRECTORY = "uploadedItemsFiles";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Localization.setLocal(request, response);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || user.getType() != 0) {
            response.sendRedirect("/");
        } else {
            ItemService itemService = new ItemService();

            String idParameter = request.getParameter("id");
            Item item = null;

            if (idParameter != null) {
                try {
                    item = itemService.findItem(Integer.parseInt(idParameter));
                } catch (Exception e) {
                    response.sendRedirect("/");
                    return;
                }
            }

            if(item == null) {
                response.sendRedirect("/");
                return;
            }

            request.setAttribute("username", user.getSecondName() + " " + user.getFirstName());
            request.setAttribute("user_type", user.getType());
            request.setAttribute("page_type", "upload_item_file");
            request.setAttribute("item", item);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/index.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Localization.setLocal(request, response);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || user.getType() != 0) {
            response.sendRedirect("/");
            return;
        }


        String description = request.getParameter("description");
        String itemIdParameter = request.getParameter("item_id");

        if (description.isEmpty()) {
            response.sendRedirect("/");
            return;
        }

        Item item = null;
        ItemService itemService = new ItemService();

        if (itemIdParameter != null) {
            try {
                item = itemService.findItem(Integer.parseInt(itemIdParameter));
            } catch (Exception e) {
                response.sendRedirect("/");
                return;
            }
        }

        if (item == null) {
            response.sendRedirect("/");
            return;
        }

        ArrayList<Part> parts = new ArrayList<>(request.getParts());

        if (parts.size() == 0) {
            response.sendRedirect("/");
            return;
        }

        File file = new File();
        file.setName(extractFileName(parts.get(0)));
        file.setDescription(description);
        file.setItem(item);

        FileService fileService = new FileService();
        fileService.saveFile(file);

        try {
            String appPath = request.getServletContext().getRealPath("");
            appPath = appPath.replace('\\', '/');

            // The directory to save uploaded file
            String fullSavePath = null;
            if (appPath.endsWith("/")) {
                fullSavePath = appPath + SAVE_DIRECTORY;

                java.io.File fileSaveDir = new java.io.File(fullSavePath);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdir();
                }

                fullSavePath = appPath + SAVE_DIRECTORY + "/" + file.getId();
            } else {
                fullSavePath = appPath + "/" + SAVE_DIRECTORY;

                java.io.File fileSaveDir = new java.io.File(fullSavePath);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdir();
                }

                fullSavePath = appPath + "/" + SAVE_DIRECTORY + "/" + file.getId();
            }

            // Creates the save directory if it does not exists
            java.io.File fileSaveDir = new java.io.File(fullSavePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }

            // Part list (multi files).
            for (Part part : request.getParts()) {
                String fileName = extractFileName(part);
                if (fileName != null && fileName.length() > 0) {
                    String filePath = fullSavePath + java.io.File.separator + fileName;
                    // Write to file
                    part.write(filePath);
                }
            }

            response.sendRedirect("/item_files?id=" + item.getId());
        } catch (Exception e) {
            request.setAttribute("username", user.getSecondName() + " " + user.getFirstName());
            request.setAttribute("user_type", user.getType());
            request.setAttribute("page_type", "upload_item_file");
            request.setAttribute("item", item);
            request.setAttribute("message", "Error: " + e.getMessage());

            fileService.deleteFile(file);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/index.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] items = contentDisposition.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                clientFileName = clientFileName.replace("\\", "/");
                int i = clientFileName.lastIndexOf('/');
                return clientFileName.substring(i + 1);
            }
        }
        return null;
    }

}
