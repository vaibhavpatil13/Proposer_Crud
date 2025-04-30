package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ErrorDetails;
import com.example.demo.entity.NomineeDetailsEntity;

public interface ErrorDetailsRepository extends JpaRepository<ErrorDetails, Integer>{

}
