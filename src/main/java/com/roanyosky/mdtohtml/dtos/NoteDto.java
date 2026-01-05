package com.roanyosky.mdtohtml.dtos;

import lombok.Data;

import java.time.Instant;

@Data
public class NoteDto {
    private Integer id;
    private String fileName;
    private String content;
    private Instant createdAt;
}
