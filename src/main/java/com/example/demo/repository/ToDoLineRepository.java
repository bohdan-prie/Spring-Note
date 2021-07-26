package com.example.demo.repository;

import com.example.demo.entity.ToDoLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ToDoLineRepository extends JpaRepository<ToDoLine, String> {
    @Transactional
    void deleteAllByUserLogin(String login);

    List<ToDoLine> findByUser_LoginOrderByTimeCreation(String login);

    List<ToDoLine> findByUser_LoginOrderByTimeChange(String login);
}
