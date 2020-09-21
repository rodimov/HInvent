package com.alexandra.HInvent.services;

import com.alexandra.HInvent.dao.ItemDAO;
import com.alexandra.HInvent.entities.Cabinet;
import com.alexandra.HInvent.entities.Item;
import com.alexandra.HInvent.entities.User;

import java.util.List;

public class ItemService {
    private ItemDAO itemDAO = new ItemDAO();

    public ItemService() {}

    public Item findItem(int id) {
        return itemDAO.findById(id);
    }

    public List<Item> findItemsByUser(User user) {
        return itemDAO.findByUser(user);
    }

    public List<Item> findItemsByUserDebt(User user) {
        return itemDAO.findByUserDebt(user);
    }

    public List<Item> findItemsByCabinet(Cabinet cabinet) {
        return itemDAO.findByCabinet(cabinet);
    }

    public void saveItem(Item item) {
        itemDAO.save(item);
    }

    public void deleteItem(Item item) {
        itemDAO.delete(item);
    }

    public void updateItem(Item item) {
        itemDAO.update(item);
    }

    public List<Item> findAllItems() {
        return itemDAO.findAll();
    }
}
