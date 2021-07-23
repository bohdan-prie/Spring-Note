package com.example.demo.repository;

import com.example.demo.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, String> {

    @Transactional
    void deleteAllByUserLogin(String login);

    List<Note> findByUser_LoginAndTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(
            @Param("login") String login,
            @Param("title") String title,
            @Param("body") String body
    );


    List<Note> findByUser_LoginOrderByTimeCreation(String login);

    List<Note> findByUser_LoginOrderByTimeChange(String login);
}