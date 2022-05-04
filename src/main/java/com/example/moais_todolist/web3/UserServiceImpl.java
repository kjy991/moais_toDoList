package com.example.moais_todolist.web3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao = new UserDao();

    @Override
    public int login(String id, String password) {
        return userDao.login(id,password);

    }

    @Override
    public int checkUserId(HashMap data) {
        return userDao.checkUserId(data);
    }

    @Override
    public int join(HashMap data) {
        return userDao.join(data);
    }
}
