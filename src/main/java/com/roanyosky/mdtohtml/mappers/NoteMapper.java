package com.roanyosky.mdtohtml.mappers;

import com.roanyosky.mdtohtml.dtos.NoteCreateDto;
import com.roanyosky.mdtohtml.dtos.NoteDto;
import com.roanyosky.mdtohtml.dtos.NoteUpdateDto;
import com.roanyosky.mdtohtml.entities.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    NoteDto toDto(Note note);

    @Mapping(target = "id", ignore = true)
    @Mapping(target ="createdAt", ignore = true)
    Note toEntity(NoteCreateDto noteCreateDto);

    void updateDto(NoteUpdateDto noteUpdateDto, @MappingTarget Note note);
}
