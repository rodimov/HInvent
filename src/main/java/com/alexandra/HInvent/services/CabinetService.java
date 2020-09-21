package com.alexandra.HInvent.services;

import com.alexandra.HInvent.dao.CabinetDAO;
import com.alexandra.HInvent.dao.UserDAO;
import com.alexandra.HInvent.entities.Cabinet;
import com.alexandra.HInvent.entities.User;

import java.util.List;

public class CabinetService {
    private CabinetDAO cabinetDAO = new CabinetDAO();

    public CabinetService() {}

    public Cabinet findCabinet(int id) {
        return cabinetDAO.findById(id);
    }

    public void saveCabinet(Cabinet cabinet) {
        cabinetDAO.save(cabinet);
    }

    public void deleteCabinet(Cabinet cabinet) {
        cabinetDAO.delete(cabinet);
    }

    public void updateCabinet(Cabinet cabinet) {
        cabinetDAO.update(cabinet);
    }

    public List<Cabinet> findAllCabinets() {
        return cabinetDAO.findAll();
    }
}
