package com.example.moais_todolist.web3;

import lombok.*;

import java.util.Date;

@Data
public class UserVo {
    private int no ;
    private String id;
    private String password;
    private String title;
    private String status;
//    private String role;
    private String email;
    private Date createDate;
    private Date deleteDate;

}
