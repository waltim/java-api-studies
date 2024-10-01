package br.com.waltim.api.repositories;

import br.com.waltim.api.domain.Books;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Books, Long> {
    Optional<Books> findByTitle(String title);
}
