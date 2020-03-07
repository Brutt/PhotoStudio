package com.photostudio.dao;

import com.photostudio.entity.user.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    void add(User user);

    User getUserById(long id);

    void edit(User user);

    void delete(long id);

    User getByLogin(String login);

    User getByEmail(String email);
}
