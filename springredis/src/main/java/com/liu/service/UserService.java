package com.liu.service;

import com.liu.test.User;


import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(String id);
    void delete(String id);
}
