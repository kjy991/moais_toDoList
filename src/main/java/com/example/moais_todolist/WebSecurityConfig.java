//https://jungeunlee95.github.io/java/2019/07/17/2-Spring-Security/

package com.example.moais_todolist;


import com.example.moais_todolist.web3.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableWebSecurity //시큐리티 필터가 등록
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근을 하면 권한 및 인증을 미리 체크
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // antMatchers
        web.ignoring().antMatchers("/assets/**");
        web.ignoring().antMatchers("/favicon.ico");

        //super.configure(web); // 아무런 작업을 하지 않음

        // 스프링 시큐리티의 필터 연결을 설정하기 위한 오버라이딩이다.
        // 예외가 웹접근 URL를 설정한다.
        // ACL(Access Control List - 접근 제어 목록)의 예외 URL을 설정
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 1. ACL 설정
        http.authorizeRequests()
                .antMatchers("/user/update", "/user/logout").authenticated()
                .antMatchers("/board/write", "/board/delte", "/board/modify").authenticated()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll();

        // Temporary for Testing 임시로 csrf 설정 막기
        http.csrf().disable();

        // 2. 로그인 설정
        http
                .formLogin()
                .loginPage("/user/login") 	// 로그인 페이지 url
                .loginProcessingUrl("/user/auth")  // view form의 action과 맞아야함
                .failureUrl("/user/login?result=fail") // 로그인 실패시 redirect
                .defaultSuccessUrl("/", true) // 로그인 성공시
                .usernameParameter("email")  // 로그인 요청시 id용 파라미터 (메소드 이름이 usernameParameter로 무조건 써야하지만, 파라미터는 email이든 id이든 name이든 상관없다.)
                .passwordParameter("password");	// 로그인 요청시 password용 파라미터

        // 3. 로그아웃 설정
        http
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/") // 로그아웃 성공시
                .invalidateHttpSession(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 사용자 세부 서비스를 설정하기 위한 오버라이딩이다.
        auth.userDetailsService(userServiceImpl);

    }
}
