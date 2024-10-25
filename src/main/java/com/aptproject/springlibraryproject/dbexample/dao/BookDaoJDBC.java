package com.aptproject.springlibraryproject.dbexample.dao;

import com.aptproject.springlibraryproject.dbexample.db.DBConnection;
import com.aptproject.springlibraryproject.dbexample.model.Book;
import java.sql.*;

public class BookDaoJDBC {
    public void findBookById(Integer bookId) {
        try (Connection connection = DBConnection.INSTANCE.getConnection()){
            if (connection != null) {
                System.out.println("РЈСЂР° РјС‹ РІРЅСѓС‚СЂРё");
            }
            else {
                System.out.println("Р±Рґ РЅСЌС‚");
            }

            String select = "select * from books where id= ?";
            PreparedStatement selectQuery = connection.prepareStatement(select);
            selectQuery.setInt(1, bookId);
            ResultSet resultSet = selectQuery.executeQuery(); // Р РµР·СѓР»СЊС‚Р°С‚ Р·Р°РїСЂРѕСЃР°

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setAuthor(resultSet.getString("author"));
                book.setTitle(resultSet.getString("title"));
                book.setDateAdded(resultSet.getDate("date_added"));
                System.out.println(book);
            }
        }

        catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}