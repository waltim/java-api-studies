package br.com.waltim.api.domain.dto;

import br.com.waltim.api.domain.vo.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Objects;

@JsonPropertyOrder({"id","name","email","address","password"})
public class UserDTO extends RepresentationModel<UserDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long key;
    private String name;
    private String email;
    private Address address;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public UserDTO() {}

    public UserDTO(Long key, String name, String email, Address address, String password) {
        this.key = key;
        this.name = name;
        this.email = email;
        this.address = address;
    }

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(key, userDTO.key) && Objects.equals(name, userDTO.name) && Objects.equals(email, userDTO.email) && Objects.equals(address, userDTO.address) && Objects.equals(password, userDTO.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), key, name, email, address, password);
    }
}
