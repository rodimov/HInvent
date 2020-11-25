package com.alexandra.HInvent.services;

import com.alexandra.HInvent.dao.FileDAO;
import com.alexandra.HInvent.entities.File;
import com.alexandra.HInvent.entities.Item;

import java.util.List;

public class FileService {
    private FileDAO fileDAO = new FileDAO();

    public FileService() {}

    public File findFile(int id) {
        return fileDAO.findById(id);
    }

    public void saveFile(File file) {
        fileDAO.save(file);
    }

    public void deleteFile(File file) {
        fileDAO.delete(file);
    }

    public void updateFile(File file) {
        fileDAO.update(file);
    }

    public List<File> findFilesByItem(Item item) {
        return fileDAO.findByItem(item);
    }

    public List<File> findAllFiles() {
        return fileDAO.findAll();
    }
}
