package dev.skonan.bank.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {
    private String street;

    @Column(name = "house_number")
    private Integer houseNumber;

    @Column(name = "zip_code")
    private Integer zipCode;

    private String city;

    private String county;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(houseNumber, address.houseNumber) && Objects.equals(zipCode, address.zipCode) && Objects.equals(city, address.city) && Objects.equals(county, address.county);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), street, houseNumber, zipCode, city, county);
    }
}
