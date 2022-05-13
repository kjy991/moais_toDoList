package com.example.moais_todolist.web3;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    UserDao userDao;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UserVo userVo = userDao.get(id);

        SecurityUser securityUser = new SecurityUser();

        if (userVo != null) {
            securityUser.setName(userVo.getId());
            securityUser.setPassword(userVo.getPassword());  // credetial
//            securityUser.setUsername(userVo.get());     // principal

        }

        return securityUser; // 여기서 return된 UserDetails는 SecurityContext의 Authentication에 등록되어 인증 정보를 갖고 있는다.
    }


    @Override
    public Boolean login(String id, String password) {
        return userDao.login(id,password);
    }

    @Override
    public int checkUserId(Map<String, String> data) {
        return userDao.checkUserId(data);
    }

    @Override
    public int join(Map<String, String> data) {
        data.put("password", new BCryptPasswordEncoder().encode(data.get("password")));
        return userDao.join(data);
    }
}
