package br.com.waltim.api.services;

import br.com.waltim.api.domain.dto.BookDTO;

import java.util.List;

public interface BookService {
    BookDTO findById(Long id);
    List<BookDTO> findAll();
    BookDTO create(BookDTO bookDTO);
    BookDTO update(BookDTO bookDTO);
    void delete(Long id);
}
