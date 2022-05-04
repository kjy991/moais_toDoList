package com.example.moais_todolist.web3;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@CrossOrigin
@AllArgsConstructor
@Controller
@RequestMapping("/")
public class UserController {

    /**
     * 로그인 페이지로 이동
     */
    @GetMapping("/login")
    public String login(HttpSession session) {
        if (session.getAttribute("userInfo") != null) {
            return "redirect:/";
        }
        return "/login";
    }

    /**
     * 로그인 페이지로 이동
     */
    @GetMapping("/login/do")
    public String doLogin(HttpSession session, @RequestParam(name = "id") String id, @RequestParam(name = "password") String password) {



        return "/list";
    }

    /**
     * 사용자 등록 페이지로 이동
     */
    @GetMapping("/join")
    public String Join() {
        return "/join";
    }

}
