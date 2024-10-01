package br.com.waltim.api.services.impl;

import br.com.waltim.api.domain.Books;
import br.com.waltim.api.domain.dto.BookDTO;
import br.com.waltim.api.repositories.BookRepository;
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
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    public static final long KEY = 1L;
    public static final String TITLE = "Spring in Action";
    public static final String AUTHOR = "Craig Walls";
    public static final double PRICE = 29.99;
    public static final LocalDateTime PUBLISHED = LocalDateTime.of(2023, 1, 15, 10, 0);

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
        assertThrows(ObjectNotFoundException.class, () -> {
            bookService.findById(KEY);
        });

        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void startBooks(){
        book = new Books(KEY, TITLE, AUTHOR, PRICE, PUBLISHED);
        bookDTO = new BookDTO(KEY, TITLE, AUTHOR, PRICE, PUBLISHED);
        optionalBook = Optional.of(book);
    }

}