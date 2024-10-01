package br.com.waltim.api.services.impl;

import br.com.waltim.api.domain.Books;
import br.com.waltim.api.domain.dto.BookDTO;
import br.com.waltim.api.repositories.BookRepository;
import br.com.waltim.api.services.exceptions.DataIntegrityViolationException;
import br.com.waltim.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

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

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    private BookDTO bookDTO;
    private Books book;
    private Optional<Books> optionalBook;

    @BeforeEach
    void setUp() {
        startBooks();
    }

    @Test
    void shouldReturnABookWhenFindById() {
        when(bookRepository.findById(anyLong())).thenReturn(optionalBook);
        when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);

        BookDTO response = bookService.findById(KEY);

        assertNotNull(response);
        assertNotNull(response.getLinks());

        assertEquals(bookDTO.getKey(), response.getKey());
        assertEquals(bookDTO.getTitle(), response.getTitle());
        assertEquals(bookDTO.getAuthor(), response.getAuthor());
        assertEquals(bookDTO.getPublished(), response.getPublished());
        assertEquals(bookDTO.getPrice(), response.getPrice());

        verify(bookRepository, times(1)).findById(anyLong());
        verify(modelMapper, times(1)).map(any(Books.class), eq(BookDTO.class));
    }

    @Test
    void shouldThrowExceptionWhenBookNotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
            bookService.findById(KEY);
        });

        assertNotNull(exception);
        assertEquals(BOOK_NOT_FOUND, exception.getMessage());

        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    void shouldReturnAListOfBooksWhenFindAll() {
        when(bookRepository.findAll()).thenReturn(List.of(book));
        when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);

        List<BookDTO> response = bookService.findAll();

        assertNotNull(response);
        assertEquals(response.size(), 1);
        assertEquals(bookDTO.getKey(), response.getFirst().getKey());
        assertEquals(bookDTO.getTitle(), response.getFirst().getTitle());
        assertEquals(bookDTO.getAuthor(), response.getFirst().getAuthor());
        assertEquals(bookDTO.getPublished(), response.getFirst().getPublished());
        assertEquals(bookDTO.getPrice(), response.getFirst().getPrice());

        verify(bookRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(any(Books.class), eq(BookDTO.class));
    }

    @Test
    void shouldReturnASuccessWhenCreateABook() {
        when(modelMapper.map(bookDTO, Books.class)).thenReturn(book);
        when(bookRepository.save(any(Books.class))).thenReturn(book);
        when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);

        BookDTO response = bookService.create(bookDTO);

        assertNotNull(response);
        assertNotNull(response.getLinks());

        assertEquals(bookDTO.getKey(), response.getKey());
        assertEquals(bookDTO.getTitle(), response.getTitle());
        assertEquals(bookDTO.getAuthor(), response.getAuthor());
        assertEquals(bookDTO.getPublished(), response.getPublished());
        assertEquals(bookDTO.getPrice(), response.getPrice());

        verify(bookRepository, times(1)).save(any(Books.class));
        verify(modelMapper, times(1)).map(any(Books.class), eq(BookDTO.class));
    }

    @Test
    void shouldReturnThrowsDataIntegrityViolationExceptionWhenBookAlreadyExists() {
        when(bookRepository.findByTitle(anyString())).thenReturn(Optional.of(book));
        when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            bookService.create(bookDTO);
        });

        assertNotNull(exception);
        assertEquals(REGISTRED_IN_THE_SYSTEM, exception.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenTitleIsNull() {
        bookDTO.setTitle(null); // Simulando título nulo

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            bookService.create(bookDTO);
        });

        assertNotNull(exception);
        assertEquals(TITLE_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenAuthorIsNull() {
        bookDTO.setAuthor(null); // Simulando autor nulo

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            bookService.create(bookDTO);
        });

        assertNotNull(exception);
        assertEquals(AUTHOR_NAME_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenPriceIsNull() {
        bookDTO.setPrice(null); // Simulando preço nulo

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            bookService.create(bookDTO);
        });

        assertNotNull(exception);
        assertEquals(PRICE_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenPublishedIsNull() {
        bookDTO.setPublished(null); // Simulando data de lançamento nula

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            bookService.create(bookDTO);
        });

        assertNotNull(exception);
        assertEquals(DATE_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenTitleIsEmpty() {
        bookDTO.setTitle(" "); // Simulando título vazio

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            bookService.create(bookDTO);
        });

        assertNotNull(exception);
        assertEquals(TITLE_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenAuthorIsEmpty() {
        bookDTO.setAuthor(" "); // Simulando autor vazio

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            bookService.create(bookDTO);
        });

        assertNotNull(exception);
        assertEquals(AUTHOR_NAME_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldReturnASuccessWhenUpdateABook() {
        when(bookRepository.save(any())).thenReturn(book);
        when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);
        when(bookRepository.findById(anyLong())).thenReturn(optionalBook);

        BookDTO response = bookService.update(bookDTO);

        assertNotNull(response);
        assertNotNull(response.getLinks());

        assertEquals(bookDTO.getKey(), response.getKey());
        assertEquals(bookDTO.getTitle(), response.getTitle());
        assertEquals(bookDTO.getAuthor(), response.getAuthor());
        assertEquals(bookDTO.getPublished(), response.getPublished());
        assertEquals(bookDTO.getPrice(), response.getPrice());

        verify(bookRepository, times(1)).save(any());
        verify(modelMapper, times(2)).map(book, BookDTO.class);
    }

    @Test
    void shouldThrowExceptionBookNotFoundWhenUpdateABook() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
            bookService.update(bookDTO);
        });
        assertNotNull(exception);
        assertEquals(BOOK_NOT_FOUND, exception.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenUpdatingWithNullTitle() {
        bookDTO.setTitle(null); // Simulando título nulo

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            bookService.update(bookDTO);
        });

        assertNotNull(exception);
        assertEquals(TITLE_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenUpdatingWithNullAuthor() {
        bookDTO.setAuthor(null); // Simulando autor nulo

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            bookService.update(bookDTO);
        });

        assertNotNull(exception);
        assertEquals(AUTHOR_NAME_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenUpdatingWithNullPrice() {
        bookDTO.setPrice(null); // Simulando preço nulo

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            bookService.update(bookDTO);
        });

        assertNotNull(exception);
        assertEquals(PRICE_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenUpdatingWithNullPublished() {
        bookDTO.setPublished(null); // Simulando data de lançamento nula

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            bookService.update(bookDTO);
        });

        assertNotNull(exception);
        assertEquals(DATE_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenUpdatingWithEmptyTitle() {
        bookDTO.setTitle(" "); // Simulando título vazio

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            bookService.update(bookDTO);
        });

        assertNotNull(exception);
        assertEquals(TITLE_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenUpdatingWithEmptyAuthor() {
        bookDTO.setAuthor(" "); // Simulando autor vazio

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            bookService.update(bookDTO);
        });

        assertNotNull(exception);
        assertEquals(AUTHOR_NAME_CANNOT_BE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    void shouldReturnSuccessWhenDeleteABook() {
        when(bookRepository.findById(anyLong())).thenReturn(optionalBook);
        when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);
        doNothing().when(bookRepository).deleteById(anyLong());
        bookService.delete(bookDTO.getKey());

        verify(bookRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void shouldThrowObjectNotFoundWhenDeleteABook() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
            bookService.delete(bookDTO.getKey());
        });

        assertNotNull(exception);
        assertEquals(BOOK_NOT_FOUND, exception.getMessage());
    }

    private void startBooks() {
        book = new Books(KEY, TITLE, AUTHOR, PRICE, PUBLISHED);
        bookDTO = new BookDTO(KEY, TITLE, AUTHOR, PRICE, PUBLISHED);
        optionalBook = Optional.of(book);
    }

}