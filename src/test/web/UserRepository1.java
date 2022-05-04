package com.example.moais_todolist.web;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository1 extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByEmail(String email);
}