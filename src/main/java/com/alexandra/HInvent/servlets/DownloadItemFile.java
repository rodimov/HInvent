package com.alexandra.HInvent.servlets;

import com.alexandra.HInvent.entities.File;
import com.alexandra.HInvent.entities.User;
import com.alexandra.HInvent.services.FileService;
import com.alexandra.HInvent.utils.Localization;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/download_item_file")
public class DownloadItemFile extends HttpServlet {
    public static final String SAVE_DIRECTORY = "uploadedItemsFiles";
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Localization.setLocal(request, response);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("/sign_in");
            return;
        }

        String idParametr = request.getParameter("id");
        FileService fileService = new FileService();
        File file = null;

        if (idParametr != null) {
            try {
                file = fileService.findFile(Integer.parseInt(idParametr));
            } catch (Exception e) {
                response.sendRedirect("/");
                return;
            }
        }

        if(file == null) {
            response.sendRedirect("/");
            return;
        }

        String appPath = request.getServletContext().getRealPath("");
        appPath = appPath.replace('\\', '/');

        String fullSavePath = null;

        if (appPath.endsWith("/")) {
            fullSavePath = appPath + SAVE_DIRECTORY + "/" + file.getId();
        } else {
            fullSavePath = appPath + "/" + SAVE_DIRECTORY + "/" + file.getId();
        }

        fullSavePath += "/" + file.getName();
        java.io.File filePointer = new java.io.File(fullSavePath);

        if (!filePointer.exists() || !filePointer.isFile()) {
            response.sendRedirect("/");
            return;
        }

        try {

            String contentType = getServletContext().getMimeType(fullSavePath);

            response.setHeader("Content-Type", contentType);

            response.setHeader("Content-Length", String.valueOf(filePointer.length()));

            response.setHeader("Content-Disposition", "inline; filename=\"" + filePointer.getName() + "\"");

            InputStream is = new FileInputStream(filePointer);

            byte[] bytes = new byte[1024];
            int bytesRead;

            while ((bytesRead = is.read(bytes)) != -1) {
                response.getOutputStream().write(bytes, 0, bytesRead);
            }

            is.close();

            response.sendRedirect("/item_files?id=" + file.getItem().getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
