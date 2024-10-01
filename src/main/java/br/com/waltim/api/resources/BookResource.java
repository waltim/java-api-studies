package br.com.waltim.api.resources;

import br.com.waltim.api.domain.dto.BookDTO;
import br.com.waltim.api.services.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/books")
@Tag(name = "Books", description = "Endpoints for Books.")
public class BookResource {

    public static final String ID = "/{id}";
    public static final String APPLICATION_X_YAML = "application/x-yaml";
    public static final String BOOK_V1 = "/v1/book/";

    @Autowired
    private BookService bookService;

    @GetMapping(value = ID, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, APPLICATION_X_YAML})
    public ResponseEntity<BookDTO> findById(Long id) {
        return ResponseEntity.ok().body(bookService.findById(id));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, APPLICATION_X_YAML})
    public ResponseEntity<List<BookDTO>> findAll() {
        return ResponseEntity.ok().body(bookService.findAll());
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, APPLICATION_X_YAML},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, APPLICATION_X_YAML})
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO bookDTO) {
        BookDTO createdBook = bookService.create(bookDTO);
        URI location = URI.create(BOOK_V1 + createdBook.getKey()); // Altere para o formato adequado
        return ResponseEntity.created(location).body(createdBook);
    }

    @PutMapping(value = ID, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, APPLICATION_X_YAML},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, APPLICATION_X_YAML})
    public ResponseEntity<BookDTO> update(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        bookDTO.setKey(id);
        return ResponseEntity.ok().body(bookService.update(bookDTO));
    }

    @DeleteMapping(value = ID)
    public ResponseEntity<BookDTO> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
