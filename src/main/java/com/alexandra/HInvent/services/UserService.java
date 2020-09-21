package com.alexandra.HInvent.services;

import com.alexandra.HInvent.dao.UserDAO;
import com.alexandra.HInvent.entities.User;

import java.util.List;

public class UserService {
    private UserDAO usersDao = new UserDAO();

    public UserService() {}

    public User findUser(int id) {
        return usersDao.findById(id);
    }

    public User findUserByEmail(String email) {
        return usersDao.findUserByEmail(email);
    }

    public void saveUser(User user) {
        usersDao.save(user);
    }

    public void deleteUser(User user) {
        usersDao.delete(user);
    }

    public void updateUser(User user) {
        usersDao.update(user);
    }

    public List<User> findAllUsers() {
        return usersDao.findAll();
    }
}
