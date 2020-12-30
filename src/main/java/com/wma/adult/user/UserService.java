package com.wma.adult.user;

public interface UserService {
    void register(User user);

    User getUserByAccount(String account);

    void updateUser(User user);

    User getUserById(String id);

    void resetPassword(String id, String password);
}