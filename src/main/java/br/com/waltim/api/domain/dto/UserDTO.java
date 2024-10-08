package br.com.waltim.api.domain.dto;

import br.com.waltim.api.domain.Permission;
import br.com.waltim.api.domain.vo.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@JsonPropertyOrder({"id", "userName", "fullName", "email", "address", "password",
        "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"})
public class UserDTO extends RepresentationModel<UserDTO> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long key;

    private String userName;

    private String fullName;

    private String email;

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private Boolean credentialsNonExpired;

    private Boolean enabled;

    private String name;

    private Address address;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private List<Permission> permissions;

    public UserDTO() {
    }

    public UserDTO(Long key, String userName, String fullName, String email, Boolean accountNonExpired, Boolean accountNonLocked, Boolean credentialsNonExpired, Boolean enabled, String name, Address address, String password, List<Permission> permissions) {
        this.key = key;
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.name = name;
        this.address = address;
        this.password = password;
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(key, userDTO.key) && Objects.equals(userName, userDTO.userName) && Objects.equals(fullName, userDTO.fullName) && Objects.equals(email, userDTO.email) && Objects.equals(accountNonExpired, userDTO.accountNonExpired) && Objects.equals(accountNonLocked, userDTO.accountNonLocked) && Objects.equals(credentialsNonExpired, userDTO.credentialsNonExpired) && Objects.equals(enabled, userDTO.enabled) && Objects.equals(name, userDTO.name) && Objects.equals(address, userDTO.address) && Objects.equals(password, userDTO.password) && Objects.equals(permissions, userDTO.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), key, userName, fullName, email, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled, name, address, password, permissions);
    }
}
