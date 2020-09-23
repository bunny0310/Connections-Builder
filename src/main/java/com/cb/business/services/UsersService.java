package com.cb.business.services;

import db.UsersDAO;
import model.User;

import java.util.List;

public class UsersService {
    private UsersDAO usersDAO;

    public UsersService(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    public User getUser(int id) {
        return this.usersDAO.getUser(id);
    }

    public List<User> getUsers() {
        return this.usersDAO.getUsers();
    }

    public User addUser(User user) {
       this.usersDAO.addUser(user);
       return this.usersDAO.getRecentlyAddedUser();
    }

    public User updateUser(User user, int id) {
        this.usersDAO.updateUser(user, id);
        return this.usersDAO.getUser(id);
    }

    public void deleteUser(int id) {
        this.usersDAO.deleteUser(id);
    }

    public boolean verifyUser(User user) {
        List<User> users = this.usersDAO.verifyUser(user.getEmail(), user.getPassword());
        if(users.size() == 1) {
            return true;
        }
        return false;
    }
}
