package com.roanyosky.mdtohtml.services;

import com.roanyosky.mdtohtml.dtos.NoteCreateDto;
import com.roanyosky.mdtohtml.dtos.NoteDto;
import com.roanyosky.mdtohtml.entities.Note;
import com.roanyosky.mdtohtml.mappers.NoteMapper;
import com.roanyosky.mdtohtml.repositories.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NoteService {
    private final NoteMapper noteMapper;
    private final NoteRepository noteRepository;

    public NoteDto createNote(NoteCreateDto noteCreateDto)
    {
        Note newNote = noteMapper.toEntity(noteCreateDto);
        return noteMapper.toDto(noteRepository.save(newNote));
    }
}
