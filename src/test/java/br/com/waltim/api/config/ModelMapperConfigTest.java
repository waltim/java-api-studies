package br.com.waltim.api.config;

import br.com.waltim.api.domain.Books;
import br.com.waltim.api.domain.Permission;
import br.com.waltim.api.domain.User;
import br.com.waltim.api.domain.dto.BookDTO;
import br.com.waltim.api.domain.dto.UserDTO;
import br.com.waltim.api.domain.vo.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

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
        User user = new User();
        user.setKey(1L);
        user.setName("Craig Walls");
        user.setEmail("walls@test.com");
        user.setAddress(new Address("Street 1", "123", "next to the school", "Brasília", "DF", "Brasil"));
        user.setPassword("password");

        Permission permission = new Permission();
        permission.setDescription("ADMIN");

        user.setUserName("walli");
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setFullName("walli");
        user.setPermissions(List.of(permission));

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        assertNotNull(userDTO);
        assertEquals(user.getKey(), userDTO.getKey());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getAddress(), userDTO.getAddress());
        assertEquals(user.getPassword(), userDTO.getPassword());
        assertEquals(user.getUserName(), userDTO.getUserName());
        assertEquals(user.getAccountNonExpired(), userDTO.getAccountNonExpired());
        assertEquals(user.getAccountNonLocked(), userDTO.getAccountNonLocked());
        assertEquals(user.getCredentialsNonExpired(), userDTO.getCredentialsNonExpired());
        assertEquals(user.getEnabled(), userDTO.getEnabled());
        assertEquals(user.getFullName(), userDTO.getFullName());
        assertEquals(user.getPermissions(), userDTO.getPermissions());
    }

    @Test
    public void shouldMapUserDTOToUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setKey(1L);
        userDTO.setName("Craig Walls");
        userDTO.setEmail("walls@test.com");
        userDTO.setAddress(new Address("Street 1", "123", "next to the school", "Brasília", "DF", "Brasil"));
        userDTO.setPassword("password");

        Permission permission = new Permission();
        permission.setDescription("ADMIN");

        userDTO.setUserName("walli");
        userDTO.setAccountNonExpired(true);
        userDTO.setAccountNonLocked(true);
        userDTO.setCredentialsNonExpired(true);
        userDTO.setEnabled(true);
        userDTO.setFullName("walli");
        userDTO.setPermissions(List.of(permission));

        User user = modelMapper.map(userDTO, User.class);

        assertNotNull(user);
        assertEquals(user.getKey(), userDTO.getKey());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getAddress(), userDTO.getAddress());
        assertEquals(user.getPassword(), userDTO.getPassword());
        assertEquals(user.getUserName(), userDTO.getUserName());
        assertEquals(user.getAccountNonExpired(), userDTO.getAccountNonExpired());
        assertEquals(user.getAccountNonLocked(), userDTO.getAccountNonLocked());
        assertEquals(user.getCredentialsNonExpired(), userDTO.getCredentialsNonExpired());
        assertEquals(user.getEnabled(), userDTO.getEnabled());
        assertEquals(user.getFullName(), userDTO.getFullName());
        assertEquals(user.getPermissions(), userDTO.getPermissions());

    }
}