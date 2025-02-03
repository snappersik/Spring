package com.aptproject.springlibraryproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class SpringLibraryProjectApplication implements CommandLineRunner {

    @Value("${server.port}")
    private String serverPort;

    public static void main(String[] args) {
        SpringApplication.run(SpringLibraryProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("admin"));

        System.out.println("swagger path: http://localhost:" + serverPort + "/swagger-ui/index.html");
        System.out.println("Application path: http://localhost:" + serverPort + "/");
    }

}
