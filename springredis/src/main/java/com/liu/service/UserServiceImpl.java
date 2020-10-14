package com.liu.service;

import com.liu.dao.UserMapper;
import com.liu.test.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Override

    public List<User> findAll() {
        return  userMapper.findAll();
    }

    @Override

    public User findById(String id) {
        return userMapper.findById(id);
    }

    @Override

    public void delete(String id) {
        userMapper.delete(id);
    }
}
