package br.com.waltim.api.resources;

import br.com.waltim.api.domain.dto.BookDTO;
import br.com.waltim.api.services.BookService;
import br.com.waltim.api.services.exceptions.DataIntegrityViolationException;
import br.com.waltim.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
class BookResourceTest {

    @InjectMocks
    private BookResource bookResource;
    @Mock
    private BookService bookService;

    private BookDTO bookDTO;


    public static final long KEY = 1L;
    public static final String TITLE = "Spring in Action";
    public static final String AUTHOR = "Craig Walls";
    public static final double PRICE = 29.99;
    public static final LocalDateTime PUBLISHED = LocalDateTime.of(2023, 1, 15, 10, 0);

    public static final String BOOK_NOT_FOUND = "Book not found.";
    public static final String REGISTRED_IN_THE_SYSTEM = "The book has been registred in the System.";
    public static final String TITLE_CANNOT_BE_NULL_OR_EMPTY = "The title cannot be null or empty.";
    public static final String AUTHOR_NAME_CANNOT_BE_NULL_OR_EMPTY = "The author name cannot be null or empty.";
    public static final String PRICE_CANNOT_BE_NULL_OR_EMPTY = "The price cannot be null or empty.";
    public static final String DATE_CANNOT_BE_NULL_OR_EMPTY = "The launch date cannot be null or empty.";

    @BeforeEach
    void setUp() {
        startBooks();
    }

    @Test
    void shouldReturnABookWhenFindById() {
        when(bookService.findById(anyLong())).thenReturn(bookDTO);

        ResponseEntity<BookDTO> response = bookResource.findById(KEY);

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookDTO, response.getBody());
        assertEquals(Objects.requireNonNull(response.getBody()).getClass(), BookDTO.class);

        assertEquals(response.getBody().getKey(), bookDTO.getKey());
        assertEquals(response.getBody().getTitle(), bookDTO.getTitle());
        assertEquals(response.getBody().getAuthor(), bookDTO.getAuthor());
        assertEquals(response.getBody().getPrice(), bookDTO.getPrice());
        assertEquals(response.getBody().getPublished(), bookDTO.getPublished());
    }

    @Test
    void shouldReturnObjectNotFoundExceptionWhenFindById() {
        when(bookService.findById(anyLong())).thenThrow(new ObjectNotFoundException(BOOK_NOT_FOUND));
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> bookService.findById(KEY));

        assertNotNull(exception);
        assertEquals(BOOK_NOT_FOUND, exception.getMessage());
    }

    @Test
    void shouldReturnAListOfBooksWhenFindAll() {
        when(bookService.findAll()).thenReturn(Collections.singletonList(bookDTO));

        ResponseEntity<List<BookDTO>> response = bookResource.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookDTO, Objects.requireNonNull(response.getBody()).getFirst());

        assertEquals(Objects.requireNonNull(response.getBody()).getFirst().getClass(), BookDTO.class);
        assertEquals(response.getBody().getFirst().getKey(), bookDTO.getKey());
        assertEquals(response.getBody().getFirst().getTitle(), bookDTO.getTitle());
        assertEquals(response.getBody().getFirst().getAuthor(), bookDTO.getAuthor());
        assertEquals(response.getBody().getFirst().getPrice(), bookDTO.getPrice());
        assertEquals(response.getBody().getFirst().getPublished(), bookDTO.getPublished());
    }

    @Test
    void shouldReturnABookWhenCreateOne() {
        when(bookService.create(any())).thenReturn(bookDTO);

        ResponseEntity<BookDTO> response = bookResource.create(bookDTO);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertEquals(bookDTO, response.getBody());
        assertEquals(Objects.requireNonNull(response.getBody()).getClass(), BookDTO.class);
        assertEquals(response.getBody().getKey(), bookDTO.getKey());
        assertEquals(response.getBody().getTitle(), bookDTO.getTitle());
        assertEquals(response.getBody().getAuthor(), bookDTO.getAuthor());
        assertEquals(response.getBody().getPrice(), bookDTO.getPrice());
        assertEquals(response.getBody().getPublished(), bookDTO.getPublished());
    }

    @Test
    void shouldReturnThrowsDataIntegrityViolationExceptionWhenBookAlreadyExists() {
        when(bookService.create(any())).thenThrow(new DataIntegrityViolationException(REGISTRED_IN_THE_SYSTEM));
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> bookResource.create(bookDTO));

        assertNotNull(exception);
        assertEquals(REGISTRED_IN_THE_SYSTEM, exception.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenCreateWithTitleIsNull() {
        when(bookService.create(any())).thenThrow(new DataIntegrityViolationException(TITLE_CANNOT_BE_NULL_OR_EMPTY));

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> bookResource.create(bookDTO));

        assertNotNull(exception);
        assertEquals(TITLE_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenCreateWithAuthorNameIsNull() {
        when(bookService.create(any())).thenThrow(new DataIntegrityViolationException(AUTHOR_NAME_CANNOT_BE_NULL_OR_EMPTY));

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> bookResource.create(bookDTO));
        assertNotNull(exception);
        assertEquals(AUTHOR_NAME_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenCreateWithPriceIsNull() {
        when(bookService.create(any())).thenThrow(new DataIntegrityViolationException(PRICE_CANNOT_BE_NULL_OR_EMPTY));

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> bookResource.create(bookDTO));
        assertNotNull(exception);
        assertEquals(PRICE_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenCreateWithPublishedIsNull() {
        when(bookService.create(any())).thenThrow(new DataIntegrityViolationException(DATE_CANNOT_BE_NULL_OR_EMPTY));

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> bookResource.create(bookDTO));
        assertNotNull(exception);
        assertEquals(DATE_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldReturnABookWhenUpdateOne() {
        bookDTO.setKey(KEY);
        when(bookService.update(any())).thenReturn(bookDTO);

        ResponseEntity<BookDTO> response = bookResource.update(KEY,bookDTO);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookDTO, response.getBody());

        assertEquals(KEY, Objects.requireNonNull(response.getBody()).getKey());
        assertEquals(TITLE, response.getBody().getTitle());
        assertEquals(AUTHOR, response.getBody().getAuthor());
        assertEquals(PRICE, response.getBody().getPrice());
        assertEquals(PUBLISHED, response.getBody().getPublished());
    }

    @Test
    void shouldReturnObjectNotFoundExceptionWhenUpdateWithFindById() {
        when(bookService.update(any())).thenThrow(new ObjectNotFoundException(BOOK_NOT_FOUND));
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> bookResource.update(KEY,bookDTO));

        assertNotNull(exception);
        assertEquals(BOOK_NOT_FOUND, exception.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenUpdateWithTitleIsNull() {
        when(bookService.update(any())).thenThrow(new DataIntegrityViolationException(TITLE_CANNOT_BE_NULL_OR_EMPTY));

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> bookResource.update(KEY,bookDTO));

        assertNotNull(exception);
        assertEquals(TITLE_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenUpdateWithAuthorNameIsNull() {
        when(bookService.update(any())).thenThrow(new DataIntegrityViolationException(AUTHOR_NAME_CANNOT_BE_NULL_OR_EMPTY));

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> bookResource.update(KEY,bookDTO));
        assertNotNull(exception);
        assertEquals(AUTHOR_NAME_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenUpdateWithPriceIsNull() {
        when(bookService.update(any())).thenThrow(new DataIntegrityViolationException(PRICE_CANNOT_BE_NULL_OR_EMPTY));

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> bookResource.update(KEY,bookDTO));
        assertNotNull(exception);
        assertEquals(PRICE_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenUpdateWithPublishedIsNull() {
        when(bookService.update(any())).thenThrow(new DataIntegrityViolationException(DATE_CANNOT_BE_NULL_OR_EMPTY));

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> bookResource.update(KEY,bookDTO));
        assertNotNull(exception);
        assertEquals(DATE_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldReturnSuccessWhenDeleteABook() {
        doNothing().when(bookService).delete(any());
        ResponseEntity<BookDTO> response = bookResource.delete(KEY);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldReturnObjectNotFoundExceptionWhenDeleteWithFindById() {
        doThrow(new ObjectNotFoundException(BOOK_NOT_FOUND)).when(bookService).delete(anyLong());
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> bookResource.delete(KEY));
        assertNotNull(exception);
        assertEquals(BOOK_NOT_FOUND, exception.getMessage());
    }

    private void startBooks() {
        bookDTO = new BookDTO(KEY, TITLE, AUTHOR, PRICE, PUBLISHED);
        bookDTO.add(Collections.emptyList());
    }
}