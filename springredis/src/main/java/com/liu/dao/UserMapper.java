package com.liu.dao;

import com.liu.test.User;

import java.util.List;

public interface UserMapper {
    List<User> findAll();
    User findById(String id);
    void delete(String id);
}
