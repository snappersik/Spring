package com.aptproject.springlibraryproject;

import com.aptproject.springlibraryproject.dbexample.dao.BookDAOBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@SpringBootApplication
public class SpringLibraryProjectApplication implements CommandLineRunner {

    private BookDAOBean bookDAOBean;

    public SpringLibraryProjectApplication(BookDAOBean bookDAOBean) {
        this.bookDAOBean = bookDAOBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringLibraryProjectApplication.class, args);
    }

//    @Autowired
//    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        bookDAOBean.findBookById(4);


    }
}


