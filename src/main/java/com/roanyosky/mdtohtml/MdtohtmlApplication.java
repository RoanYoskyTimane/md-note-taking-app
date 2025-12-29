package com.roanyosky.mdtohtml;

import com.roanyosky.mdtohtml.entities.Note;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MdtohtmlApplication {

	public static void main(String[] args) {
		SpringApplication.run(MdtohtmlApplication.class, args);
		Note note = new Note();
		note.setId(1);
		note.setFileName("labulu");
		note.setContent("Labubu are the thing");
		System.out.println("The id:"+note.getId());
		System.out.println("the file name:"+note.getFileName());
	}

}
