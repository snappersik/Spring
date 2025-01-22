package com.aptproject.springlibraryproject.library.config;

import com.aptproject.springlibraryproject.library.service.userdetails.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

import static com.aptproject.springlibraryproject.library.constants.SecurityConstants.*;
import static com.aptproject.springlibraryproject.library.constants.UserRoleConstants.ADMIN;
import static com.aptproject.springlibraryproject.library.constants.UserRoleConstants.LIBRARIAN;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;   // После CustomUserDetails
    private final CustomUserDetailsService customUserDetailsService; // После CustomUserDetails

    public WebSecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, CustomUserDetailsService customUserDetailsService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.customUserDetailsService = customUserDetailsService;
    } // После CustomUserDetails

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception { // является builder'ом
        httpSecurity
                .cors().disable() // Cross-Origin Resource Sharing - Это механизм браузера, который позволяет определить
                // список ресурсов, к которым страница может получить доступ.
                .csrf().disable() // Cross-Site Request Forgery
                //Настройка http-запросов - кому/куда можно/нельзя
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(RESOURCES_WHITE_LIST.toArray(String[]::new)).permitAll()
                        .requestMatchers(BOOKS_WHITE_LIST.toArray(String[]::new)).permitAll()
                        .requestMatchers(USERS_WHITE_LIST.toArray(String[]::new)).permitAll()
                        .requestMatchers(BOOKS_PERMISSION_LIST.toArray(String[]::new)).hasAnyRole(ADMIN, LIBRARIAN)
                        .anyRequest().authenticated() // Все прочие запросы доступны аутентифицированным пользователям
                )
                //Настройка для входа в систему
                .formLogin((form) -> form
                        .loginPage("/login")
                        //Перенаправляем на главную страницу после успеха
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                );

        return httpSecurity.build();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
