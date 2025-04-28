package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.ProposerDetailsEntity;

public interface ProposerDetailsRepository extends JpaRepository<ProposerDetailsEntity, Integer>{

	@Query("select p from ProposerDetailsEntity p where p.status = :status")
	public List<ProposerDetailsEntity> getAllActiveStatus(@Param("status") String status);
	
	public Page<ProposerDetailsEntity> findByStatus(String status, Pageable pageable);

	public Optional<ProposerDetailsEntity> findByProposerIdAndStatus(Integer id, String status);
	
	public boolean existsByProposerMobileNo(Long proposerMobileNo);
	
	public boolean existsByAadharNo(Long aadharNo);
	
	public boolean existsByPanNumber(String panNumber);
	
	public boolean existsByProposerEmail(String proposerEmail);
	
	@Query("select p from ProposerDetailsEntity p where p.proposerId = :proposerId and p.status =:status")
	public Optional<ProposerDetailsEntity> findById(@Param("proposerId") Integer proposerId, @Param("status") String status);
	
}
