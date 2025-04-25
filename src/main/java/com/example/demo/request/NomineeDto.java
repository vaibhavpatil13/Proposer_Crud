package com.example.demo.request;

import java.sql.Date;

import com.example.demo.enums.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class NomineeDto {
	
	private Integer proposerId;
	
	private String nomineeName;
	
	private Gender gender;
	
	private Date dateOfBirth;
	
	private String address;
	
	private String relationWithProposer;
	
	private Long mobileNo;

	public Integer getProposerId() {
		return proposerId;
	}

	public void setProposerId(Integer proposerId) {
		this.proposerId = proposerId;
	}

	public String getNomineeName() {
		return nomineeName;
	}

	public void setNomineeName(String nomineeName) {
		this.nomineeName = nomineeName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRelationWithProposer() {
		return relationWithProposer;
	}

	public void setRelationWithProposer(String relationWithProposer) {
		this.relationWithProposer = relationWithProposer;
	}

	public Long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	

}
