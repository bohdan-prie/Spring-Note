package com.example.demo.repository;

import com.example.demo.entity.ToDoLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ToDoLineRepository extends JpaRepository<ToDoLine, String> {
    @Transactional
    void deleteAllByUserLogin(String login);

    @Query("SELECT u FROM Note u WHERE u.user.login = :login AND " +
            "((upper(u.title) LIKE CONCAT('%', upper(:title), '%')) OR (upper(u.body) LIKE CONCAT('%', upper(:body), '%'))) " +
            "ORDER BY u.timeChange")
    List<ToDoLine> findByPattern(
            @Param("login") String login,
            @Param("title") String title,
            @Param("body") String body
    );

    List<ToDoLine> findByUser_LoginOrderByTimeCreation(String login);

    List<ToDoLine> findByUser_LoginOrderByTimeChange(String login);
}
