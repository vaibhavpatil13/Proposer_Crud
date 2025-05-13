package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserAddress;

public interface UserAddressRepository extends JpaRepository<UserAddress, Integer> {

	//Optional<List<UserAddress>> findByUserId(Integer userId);

}
