package br.com.waltim.api.domain.vo;

import br.com.waltim.api.services.exceptions.HandleIllegalArgumentException;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Address {

    private String street;
    private String number;
    private String complement;
    private String city;
    private String state;
    private String country;

    public Address(String street, String number, String complement, String city, String state, String country) {
        if ((street == null || street.isEmpty()) || (number == null || number.isEmpty()) || (city == null || city.isEmpty()) || (state == null || state.isEmpty()) || (country == null || country.isEmpty())) {
            throw new HandleIllegalArgumentException("Street, city, state, and country cannot be null or empty");
        }
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public Address() {

    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(number, address.number) && Objects.equals(complement, address.complement) && Objects.equals(city, address.city) && Objects.equals(state, address.state) && Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number, complement, city, state, country);
    }
}
