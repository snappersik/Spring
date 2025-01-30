package com.aptproject.springlibraryproject.library.repository;

import com.aptproject.springlibraryproject.library.model.User;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends GenericRepository<User> {

    User findUserByLogin(String login);

    User findUserByEmail(String email);

    User findUserByChangePasswordToken(String uuid);

    @Query(nativeQuery = true,
            value = """
            select distinct email
            from users u join book_rent_info bri on u.id = bri.user_id
            where bri.return_date < now()
            and bri.returned = false
            and u.is_deleted = false
            """)
    List<String> getDelayedEmails();
}
