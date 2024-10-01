package br.com.waltim.api.config;

import br.com.waltim.api.domain.Books;
import br.com.waltim.api.domain.Users;
import br.com.waltim.api.domain.dto.BookDTO;
import br.com.waltim.api.domain.dto.UserDTO;
import br.com.waltim.api.domain.vo.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ModelMapperConfigTest {

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
    }

    @Test
    public void shouldMapBookToBookDTO() {
        Books book = new Books();
        book.setKey(1L);
        book.setTitle("Spring in Action");
        book.setAuthor("Craig Walls");
        book.setPrice(29.99);
        book.setPublished(LocalDateTime.of(2023, 1, 15, 10, 0));

        BookDTO bookDTO = modelMapper.map(book, BookDTO.class);

        assertNotNull(bookDTO);
        assertEquals(book.getKey(), bookDTO.getKey());
        assertEquals(book.getTitle(), bookDTO.getTitle());
        assertEquals(book.getAuthor(), bookDTO.getAuthor());
        assertEquals(book.getPrice(), bookDTO.getPrice());
        assertEquals(book.getPublished(), bookDTO.getPublished());
    }

    @Test
    public void shouldMapBookDTOToBook() {
        // Dado um BookDTO
        BookDTO bookDTO = new BookDTO();
        bookDTO.setKey(1L);
        bookDTO.setTitle("Spring in Action");
        bookDTO.setAuthor("Craig Walls");
        bookDTO.setPrice(29.99);
        bookDTO.setPublished(LocalDateTime.of(2023, 1, 15, 10, 0));

        Books book = modelMapper.map(bookDTO, Books.class);

        assertNotNull(book);
        assertEquals(bookDTO.getKey(), book.getKey());
        assertEquals(bookDTO.getTitle(), book.getTitle());
        assertEquals(bookDTO.getAuthor(), book.getAuthor());
        assertEquals(bookDTO.getPrice(), book.getPrice());
        assertEquals(bookDTO.getPublished(), book.getPublished());
    }

    @Test
    public void shouldMapUsersToUserDTO() {
        Users user = new Users();
        user.setKey(1L);
        user.setName("Craig Walls");
        user.setEmail("walls@test.com");
        user.setAddress(new Address("Street 1", "123", "next to the school", "Brasília", "DF", "Brasil"));
        user.setPassword("password");

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        assertNotNull(userDTO);
        assertEquals(user.getKey(), userDTO.getKey());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getAddress(), userDTO.getAddress());
        assertEquals(user.getPassword(), userDTO.getPassword());
    }

    @Test
    public void shouldMapUserDTOToUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setKey(1L);
        userDTO.setName("Craig Walls");
        userDTO.setEmail("walls@test.com");
        userDTO.setAddress(new Address("Street 1", "123", "next to the school", "Brasília", "DF", "Brasil"));
        userDTO.setPassword("password");

        Users user = modelMapper.map(userDTO, Users.class);

        assertNotNull(user);
        assertEquals(user.getKey(), userDTO.getKey());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getAddress(), userDTO.getAddress());
        assertEquals(user.getPassword(), userDTO.getPassword());
    }
}