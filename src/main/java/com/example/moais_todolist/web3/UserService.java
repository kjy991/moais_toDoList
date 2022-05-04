package com.example.moais_todolist.web3;

import java.util.HashMap;

interface UserService {

    int login(String id, String password);

    int checkUserId(HashMap data);

    int join(HashMap data);
}


