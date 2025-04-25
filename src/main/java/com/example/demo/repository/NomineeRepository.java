package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.NomineeDetailsEntity;

public interface NomineeRepository extends JpaRepository<NomineeDetailsEntity, Integer> {

	public List<NomineeDetailsEntity> getAllByProposerId(Integer proposerId);

	public Optional<List<NomineeDetailsEntity>> findAllByProposerId(Integer proposerId);

	public Optional<NomineeDetailsEntity> findByProposerId(Integer proposerId);

	public Optional<NomineeDetailsEntity> findByProposerIdAndNomieeStatus(Integer proposerId, String string);

}
