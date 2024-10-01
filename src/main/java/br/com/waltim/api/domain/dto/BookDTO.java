package br.com.waltim.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@JsonPropertyOrder({"id","title","author","price","published"})
public class BookDTO extends RepresentationModel<BookDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long key;
    private String title;
    private String author;
    private Double price;
    private LocalDateTime published;

    public BookDTO() {}

    public BookDTO(Long key, String title, String author, Double price, LocalDateTime published) {
        this.key = key;
        this.title = title;
        this.author = author;
        this.price = price;
        this.published = published;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getPublished() {
        return published;
    }

    public void setPublished(LocalDateTime published) {
        this.published = published;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BookDTO bookDTO = (BookDTO) o;
        return Objects.equals(key, bookDTO.key) && Objects.equals(title, bookDTO.title) && Objects.equals(author, bookDTO.author) && Objects.equals(price, bookDTO.price) && Objects.equals(published, bookDTO.published);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), key, title, author, price, published);
    }
}
