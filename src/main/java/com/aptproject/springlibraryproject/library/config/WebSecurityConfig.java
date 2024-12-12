package com.aptproject.springlibraryproject.library.config;

import com.aptproject.springlibraryproject.library.service.userdetails.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

import static com.aptproject.springlibraryproject.library.constants.UserRoleConstants.ADMIN;
import static com.aptproject.springlibraryproject.library.constants.UserRoleConstants.LIBRARIAN;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    public WebSecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, CustomUserDetailsService customUserDetailsService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.customUserDetailsService = customUserDetailsService;
    }

    private final List<String> RESOURCES_WHITE_LIST = List.of(
            "/resources/**",
            "/static/**",
            "/js/**",
            "/css/**",
            "/",
            "/swagger-ui/**" //todo протестировать / перед Swagger
    );
    private final List<String> BOOKS_WHITE_LIST= List.of("/books");
    private final List<String> BOOKS_PERMISSIONS_LIST = List.of(
            "/book/add",
            "/books/update",
            "/books/delete"
    );
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .cors().disable() //TODO ТЕСТ БЕЗ CORS/CSRF
                .csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(RESOURCES_WHITE_LIST.toArray(String[]::new)).permitAll()
                        .requestMatchers(BOOKS_WHITE_LIST.toArray(String[]::new)).permitAll()
                        .requestMatchers(BOOKS_PERMISSIONS_LIST.toArray(String[]::new)).hasAnyRole(ADMIN, LIBRARIAN)
                        .anyRequest().authenticated() //Все прочие запросы доступны аутентефецированным пользователям
                )
                //Настраиваем вход в систему
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout((logout) ->logout
                        .logoutUrl("/logout")
                        .logoutUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                );

        return httpSecurity.build();
    }

    @Autowired
    protected  void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

}
