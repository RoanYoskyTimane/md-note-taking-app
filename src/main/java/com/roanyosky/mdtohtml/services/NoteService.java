package com.roanyosky.mdtohtml.services;

import com.roanyosky.mdtohtml.dtos.NoteCreateDto;
import com.roanyosky.mdtohtml.dtos.NoteDto;
import com.roanyosky.mdtohtml.dtos.NoteUpdateDto;
import com.roanyosky.mdtohtml.entities.Note;
import com.roanyosky.mdtohtml.mappers.NoteMapper;
import com.roanyosky.mdtohtml.repositories.NoteRepository;
import lombok.AllArgsConstructor;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;
import org.commonmark.parser.Parser;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NoteService {
    private final NoteMapper noteMapper;
    private final NoteRepository noteRepository;

    public List<NoteDto> getAllNotes(){
        return noteRepository.findAll().stream().map(noteMapper::toDto).collect(Collectors.toList());
    }

    public NoteDto createNote(NoteCreateDto noteCreateDto)
    {
        Note newNote = noteMapper.toEntity(noteCreateDto);
        return noteMapper.toDto(noteRepository.save(newNote));
    }

    public NoteDto updateNote(NoteUpdateDto noteUpdateDto, String fileName)
    {
        Note noteToUpdate = noteRepository.findByFileName(fileName);
        noteMapper.updateDto(noteUpdateDto, noteToUpdate);
        return noteMapper.toDto(noteRepository.save(noteToUpdate));
    }

    public String convertFromMarkdownToHtml(String identifier)
    {
        Note desiredNote = noteRepository.findByFileName(identifier);
        Parser parser = Parser.builder().build();
		Node document = parser.parse(desiredNote.getContent());
		HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}
