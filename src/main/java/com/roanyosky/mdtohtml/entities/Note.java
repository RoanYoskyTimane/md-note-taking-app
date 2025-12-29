package com.roanyosky.mdtohtml.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Note {
    private Integer id;
    private String fileName;
    private String content;
    private Date createdAt;
}
