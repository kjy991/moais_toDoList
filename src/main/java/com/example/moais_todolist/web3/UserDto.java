package com.example.moais_todolist.web3;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    int no ;
    String id;
    String passowrd;
    String title;
    String status;
    Date createDate;
    Date deleteDate;
}
