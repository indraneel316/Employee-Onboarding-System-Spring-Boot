package com.lister.employeeonboarding.repository;


import com.lister.employeeonboarding.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Responsible for Address Table
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {



}