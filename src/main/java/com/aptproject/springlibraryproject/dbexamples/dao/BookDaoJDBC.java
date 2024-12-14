package com.aptproject.springlibraryproject.dbexamples.dao;

import com.aptproject.springlibraryproject.dbexamples.db.DBConnection;
import com.aptproject.springlibraryproject.dbexamples.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDaoJDBC {
    public void findBookById(Integer bookId) {
        try (Connection connection = DBConnection.INSTANCE.getConnection()){
            if (connection != null) {
                System.out.println("Ура мы внутри");
            }
            else {
                System.out.println("бд нэт");
            }

            String select = "select * from books where id= ?";
            PreparedStatement selectQuery = connection.prepareStatement(select);
            selectQuery.setInt(1, bookId);
            ResultSet resultSet = selectQuery.executeQuery(); // Результат запроса

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setAuthor(resultSet.getString("author"));
                book.setTitle(resultSet.getString("title"));
                book.setDateAdded(resultSet.getDate("date_added"));
                System.out.println(book);
            }
        }




            // Запрос к базе данных




         catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}