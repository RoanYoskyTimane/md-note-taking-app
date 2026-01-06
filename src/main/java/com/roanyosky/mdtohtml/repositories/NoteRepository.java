package com.roanyosky.mdtohtml.repositories;

import com.roanyosky.mdtohtml.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note,Integer> {
    Note findByFileName(String fileName);
}
