package dev.skonan.bank.repositories;

import dev.skonan.bank.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
