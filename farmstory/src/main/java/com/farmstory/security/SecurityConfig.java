package com.farmstory.security;

import com.farmstory.oauth2.MyOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor // 생성자 어노테이션
@Configuration
public class SecurityConfig {

    /*
        스프링 시큐리티
         - Spring 프레임워크에서 제공하는 보안관련 처리를 위한 프레임워크
         - Spring 기반 프로젝트는 Spring Security로 보안(인증, 인가)처리 권장

        인증설정
         - 로그인, 로그아웃 페이지 및 요청주소 사용자 설정
         - 기본 인증(로그인,로그아웃)은 Security가 제공하는 기본 인증페이지 동작
         - REST API 기반 인증에서는 토큰방식을 이용하기 때문에 로그인,로그아웃 비활성

        인가설정
         - MyUserDetails의 사용자 권한 목록을 제공하는 getAuthorities()에서 반드시 접두어로 ROLE_ 붙여야 됨
         - ROLE_ 접두어를 안붙이면 hasAuthority(), hasAnyAuthority()로 권한 설정
         - 존재하지 않은 요청주소에 대해서 시큐리티는 로그인페이지로 기본 redirect 수행하기 때문에 마지막에 anyRequest().permitAll() 권한 설정
    */

    private final MyOauth2UserService myOauth2UserService;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        // 로그인 설정
        http.formLogin(login -> login
                .loginPage("/user/login")
                .defaultSuccessUrl("/article/list")
                .failureUrl("/user/login?success=100")
                .usernameParameter("uid")
                .passwordParameter("pass"));

        // 로그아웃 설정
        http.logout(logout -> logout
                .invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/user/login?success=101"));

        // OAuth2 설정
        http.oauth2Login(login -> login.loginPage("/user/login")
                .userInfoEndpoint(endpoint -> endpoint.userService(myOauth2UserService)));
        // oauth2 등록을 위한 서비스라고 보면 됨


        // 인가 설정
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/article/**").authenticated()
                .requestMatchers("/user/**").permitAll()
                .anyRequest().permitAll());

        // 기타 보안 설정
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}