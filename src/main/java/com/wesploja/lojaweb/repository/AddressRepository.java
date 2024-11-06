package com.wesploja.lojaweb.repository;

import com.wesploja.lojaweb.doman.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository  extends JpaRepository<Address, Long> {
}
