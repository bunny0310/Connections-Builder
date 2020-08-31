package com.cb.business.services;

import db.UsersDAO;
import model.User;

public class UsersService {
    private UsersDAO usersDAO;

    public UsersService(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    public User getUser(int id) {
        return this.usersDAO.getUser(id);
    }

    public void addUser(User user) {
        this.usersDAO.addUser(user);
    }

    public void updateUser(User user, int id) {
        this.usersDAO.updateUser(user, id);
    }

    public void deleteUser(int id) {
        this.usersDAO.deleteUser(id);
    }
}
