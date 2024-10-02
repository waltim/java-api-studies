package br.com.waltim.api.domain;

import br.com.waltim.api.domain.vo.Address;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Objects;


@Entity
public class Users extends RepresentationModel<Users> implements Serializable {

    private static final long serialVersionUID = 3L;

    public Users(Long key, String name, String email, Address address, String password) {
        this.key = key;
        this.name = name;
        this.email = email;
        this.address = address;
        this.password = password;
    }

    public Users() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long key;
    private String name;

    @Column(unique = true)
    private String email;

    @Embedded
    private Address address;

    private String password;

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Users users = (Users) o;
        return Objects.equals(key, users.key) && Objects.equals(name, users.name) && Objects.equals(email, users.email) && Objects.equals(address, users.address) && Objects.equals(password, users.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), key, name, email, address, password);
    }
}
