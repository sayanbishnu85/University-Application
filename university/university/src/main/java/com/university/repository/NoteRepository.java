package com.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.university.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

}
