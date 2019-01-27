package ua.test.service;

import ua.test.dao.CustomUser;

public interface UserService {
    CustomUser getUserByLogin(String login);

    boolean existsByLogin(String login);

    void addUser(CustomUser customUser);

    void updateUser(CustomUser customUser);
}