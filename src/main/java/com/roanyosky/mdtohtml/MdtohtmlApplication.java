package com.roanyosky.mdtohtml;

import com.roanyosky.mdtohtml.entities.Note;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

@SpringBootApplication
public class MdtohtmlApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
				.directory("./")
				.ignoreIfMissing()
				.load();

		dotenv.entries().forEach(entry -> {System.setProperty(entry.getKey(), entry.getValue());});
		SpringApplication.run(MdtohtmlApplication.class, args);
	}

}
