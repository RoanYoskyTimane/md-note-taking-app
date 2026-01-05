package com.roanyosky.mdtohtml.controllers;

import com.roanyosky.mdtohtml.dtos.NoteCreateDto;
import com.roanyosky.mdtohtml.services.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/notes")
public class NoteController {
    private NoteService noteService;

    @PostMapping
    public ResponseEntity<?> addNote(@RequestBody NoteCreateDto noteCreateDto, UriComponentsBuilder uriComponentsBuilder)
    {
        var createdNote = noteService.createNote(noteCreateDto);
        var builder = uriComponentsBuilder.path("api/v1/notes").build(createdNote.getId());
        return ResponseEntity.created(builder).body(createdNote);
    }

}
