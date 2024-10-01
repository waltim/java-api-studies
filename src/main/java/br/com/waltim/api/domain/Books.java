package br.com.waltim.api.domain;

import br.com.waltim.api.services.exceptions.HandleIllegalArgumentException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Books extends RepresentationModel<Books> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long key;
    @NotNull(message = "The title cannot be null or empty.")
    private String title;
    @NotNull(message = "The author name cannot be null or empty.")
    private String author;
    @NotNull(message = "The price cannot be null or empty.")
    private Double price;
    @NotNull(message = "The launch date cannot be null or empty.")
    @Column(name = "launch_date")
    private LocalDateTime published;

    public Books(Long key, String title, String author, Double price, LocalDateTime published) {
        if(title == null || title.trim().isEmpty() || author == null || author.trim().isEmpty() || price == null || published == null){
            throw new HandleIllegalArgumentException(" Title, author, price, and published cannot be null or empty");
        }

        this.key = key;
        this.title = title;
        this.author = author;
        this.price = price;
        this.published = published;
    }

    public Books() {}

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public @NotNull(message = "The title cannot be null or empty.") String getTitle() {
        return title;
    }

    public void setTitle(@NotNull(message = "The title cannot be null or empty.") String title) {
        this.title = title;
    }

    public @NotNull(message = "The author name cannot be null or empty.") String getAuthor() {
        return author;
    }

    public void setAuthor(@NotNull(message = "The author name cannot be null or empty.") String author) {
        this.author = author;
    }

    public @NotNull(message = "The price cannot be null or empty.") Double getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "The price cannot be null or empty.") Double price) {
        this.price = price;
    }

    public @NotNull(message = "The launch date cannot be null or empty.") LocalDateTime getPublished() {
        return published;
    }

    public void setPublished(@NotNull(message = "The launch date cannot be null or empty.") LocalDateTime published) {
        this.published = published;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Books books = (Books) o;
        return Objects.equals(key, books.key) && Objects.equals(title, books.title) && Objects.equals(author, books.author) && Objects.equals(price, books.price) && Objects.equals(published, books.published);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), key, title, author, price, published);
    }
}
