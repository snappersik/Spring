package com.aptproject.springlibraryproject;

import com.aptproject.springlibraryproject.dbexample.dao.BookDaoJDBC;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringLibraryProjectApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringLibraryProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception{
        BookDaoJDBC bookdaoJDBC = new BookDaoJDBC();
        bookdaoJDBC.findBookById(3);
    }

}

