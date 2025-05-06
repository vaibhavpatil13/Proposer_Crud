package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.BatchQueue;

public interface BatchQueueRepository extends JpaRepository<BatchQueue, Integer> {

	List<BatchQueue> findByIsProcess(String isProcess);

}
