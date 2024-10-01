package br.com.waltim.api.services.impl;

import br.com.waltim.api.domain.Books;
import br.com.waltim.api.domain.dto.BookDTO;
import br.com.waltim.api.repositories.BookRepository;
import br.com.waltim.api.resources.BookResource;
import br.com.waltim.api.services.BookService;
import br.com.waltim.api.services.exceptions.DataIntegrityViolationException;
import br.com.waltim.api.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServiceImpl implements BookService {

    //Injeção de depêndencia de forma tradicional
    private final BookRepository bookRepository;
    private final ModelMapper mapper;

    public BookServiceImpl(BookRepository bookRepository, ModelMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    @Override
    public BookDTO findById(Long id) {
        Optional<Books> book = bookRepository.findById(id);
        BookDTO bookDTO = mapper.map(book.orElseThrow(() -> new ObjectNotFoundException("Book not found.")), BookDTO.class);

        bookDTO.add(linkTo(methodOn(BookResource.class).findById(id)).withSelfRel());
        bookDTO.add(linkTo(methodOn(BookResource.class).findAll()).withRel("all-books"));

        return bookDTO;
    }

    @Override
    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream().map(
                b -> {
                    BookDTO bookDTO = mapper.map(b, BookDTO.class);
                    bookDTO.add(linkTo(methodOn(BookResource.class).findById(bookDTO.getKey())).withSelfRel());
                    bookDTO.add(linkTo(methodOn(BookResource.class).findAll()).withRel("all-books"));
                    return bookDTO;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public BookDTO create(BookDTO bookDTO) {
        validateBookDTO(bookDTO);
        findByTitle(bookDTO.getTitle());
        return mapper.map(bookRepository.save(mapper.map(bookDTO, Books.class)),
                BookDTO.class);
    }

    @Override
    public BookDTO update(BookDTO bookDTO) {
        validateBookDTO(bookDTO);
        findById(bookDTO.getKey());
        return mapper.map(bookRepository.save(mapper.map(bookDTO, Books.class)),
                BookDTO.class);
    }

    @Override
    public void delete(Long id) {
        findById(id);
        bookRepository.deleteById(id);
    }

    private void findByTitle(String title) {
        Optional<Books> book = bookRepository.findByTitle(title);
        if (book.isPresent()) {
            BookDTO bookDTO = mapper.map(book.get(), BookDTO.class);
            if (bookDTO.getTitle().equals(book.get().getTitle())) {
                throw new DataIntegrityViolationException("The book has been registred in the System.");
            }
        }
    }

    private void validateBookDTO(BookDTO bookDTO) {
        if (bookDTO.getTitle() == null || bookDTO.getTitle().trim().isEmpty()) {
            throw new DataIntegrityViolationException("The title cannot be null or empty.");
        }
        if (bookDTO.getAuthor() == null || bookDTO.getAuthor().trim().isEmpty()) {
            throw new DataIntegrityViolationException("The author name cannot be null or empty.");
        }
        if (bookDTO.getPrice() == null) {
            throw new DataIntegrityViolationException("The price cannot be null or empty.");
        }
        if (bookDTO.getPublished() == null) {
            throw new DataIntegrityViolationException("The launch date cannot be null or empty.");
        }
    }
}
