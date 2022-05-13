package com.example.moais_todolist.web3;

import java.util.Map;

interface UserService {

    Boolean login(String id, String password);

    int checkUserId(Map<String, String> data);

    int join(Map<String, String> data);
}


