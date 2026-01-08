package com.roanyosky.mdtohtml.controllers;

import com.roanyosky.mdtohtml.dtos.GrammarCheckDto;
import com.roanyosky.mdtohtml.dtos.NoteCreateDto;
import com.roanyosky.mdtohtml.dtos.NoteDto;
import com.roanyosky.mdtohtml.dtos.NoteUpdateDto;
import com.roanyosky.mdtohtml.services.GrammarService;
import com.roanyosky.mdtohtml.services.NoteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/notes")
public class NoteController {
    private NoteService noteService;
    private GrammarService grammarService;

    @GetMapping
    public Iterable<NoteDto> getAllNotes()
    {
        return noteService.getAllNotes();
    }

    @PostMapping
    public ResponseEntity<?> addNote(@RequestBody NoteCreateDto noteCreateDto, UriComponentsBuilder uriComponentsBuilder)
    {
        var createdNote = noteService.createNote(noteCreateDto);
        var builder = uriComponentsBuilder.path("api/v1/notes").build(createdNote.getId());
        return ResponseEntity.created(builder).body(createdNote);
    }

    @PutMapping("/{fileName}")
    public ResponseEntity<NoteDto> updateNoteConent(@PathVariable String fileName, @RequestBody NoteUpdateDto noteUpdateDto)
    {
        var noteWithUpdatedContent = noteService.updateNote(noteUpdateDto, fileName);
        return ResponseEntity.ok(noteWithUpdatedContent);
    }

    @GetMapping(value = "/{fileName}/render", produces = MediaType.TEXT_HTML_VALUE)
    public String renderNote(@PathVariable String fileName) {
        return noteService.convertFromMarkdownToHtml(fileName);
    }

    @GetMapping("/{fileName}/download")
    public ResponseEntity<byte[]> downloadNote(@PathVariable String fileName) {
        byte[] content = noteService.getNoteContentAsBytes(fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.TEXT_MARKDOWN)
                .body(content);
    }

    @GetMapping("/{fileName}/grammar-check")
    public ResponseEntity<GrammarCheckDto> grammaCheckContent(@PathVariable String fileName, HttpServletRequest request)
    {
        String content = noteService.getContentAsString(fileName);
        String clientIp = request.getRemoteAddr();
        var checkingGramma = grammarService.checkGrammar(content, clientIp);
        return ResponseEntity.ok(checkingGramma);
    }
}
