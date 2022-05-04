package com.example.moais_todolist.web3;


import lombok.AllArgsConstructor;
import lombok.val;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


@CrossOrigin
//@AllArgsConstructor
@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    UserService userService ;

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
    public Map doLogin(HttpSession session, @RequestParam(name = "id") String id, @RequestParam(name = "password") String password) {
        HashMap<Boolean, String> result = new HashMap<>();

        boolean regex = loginRegex(id) && engPassNumRegex(password); // id password 정규식을통해 정상적으로 로그인 하는지 확인
        if (id == null || password == null || id.isEmpty() || password.isEmpty() || !regex) { // 넘어온 값이 잘못됐을 경우
            result.put(false, "잘못된 값이 입력되었습니다.");
            return result;
        }

        int value = userService.login(id, password);
        if (value != 0) {
            result.put(false, "아이디 또는 비밀번호가 일치하지 않습니다.");
            return result;
        }

        session.setMaxInactiveInterval(60 * 10); // 10분 세션

        result.put(true, "로그인 되었습니다.");
        return result;
    }

    /**
     * 사용자 등록
     */
    @PostMapping("/join/do")
    public Map doJoin(@RequestParam HashMap data) {
        HashMap<Boolean, String> result = new HashMap<>();

        HashMap<Boolean, String> check = dataNullCheck(data, result);
        if (check != null) return check;

        /**아이디 중복체크 */
        int checkCode = userService.checkUserId(data);

        if (checkCode != 0) {
            result.put(false, "아이디 중복입니다.");
            return result;
        }

        int resultCode = userService.join(data);
        if (resultCode != 0) {
            result.put(false, "회원가입에 실패 했습니다.");
            return result;
        }
        return result;
    }

    /**
     * 데이터가 잘 넘어 왔는지 검사
     *
     * @param data
     * @param result
     * @return
     */
    @Nullable
    private HashMap<Boolean, String> dataNullCheck(HashMap data, HashMap<Boolean, String> result) {
        if (data == null) { // 데이터가 넘어오지 못한 경우
            result.put(false, "데이터가 없습니다.");
            return result;
        } else if (data.isEmpty()) { // 넘어온 데이터가 0개인 경우
            result.put(false, "잘못된 데이터입니다.");
            return result;
        }

        if (!engPassNumRegex(data.get("oldPass").toString()) || !engPassNumRegex(data.get("newPass").toString()) || !engPassNumRegex(data.get("newPass1").toString())) { // 넘어온 값이 잘못됐을 경우
            result.put(false, "잘못된 값이 입력되었습니다.(특수문자는 !@#$%&*만 가능합니다.)");
            return result;
        }
        return null;
    }


    /**
     * 로그인 정규식 확인
     */
    public Boolean loginRegex(String data) {
        return Pattern.matches("^[a-zA-Z0-9]*$", data);
    }

    /**
     * 영문과 숫자 특수문자 가능 정규식 확인 또는 공백
     */
    public Boolean engPassNumRegex(String data) {
        return Pattern.matches("^[a-zA-Z0-9-!@#$%&*]*$", data) || Pattern.matches("^\\s*", data);
    }
}
