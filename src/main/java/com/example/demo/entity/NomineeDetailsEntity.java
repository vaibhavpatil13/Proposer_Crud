package com.example.demo.entity;

import java.sql.Date;

import com.example.demo.enums.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "nominee")
public class NomineeDetailsEntity {
	
	private Integer proposerId;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "nominee_id")
	private Integer nomineeId;
	@Column(name = "nominee_name")
	private String nomineeName;
	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private Gender gender;
	@Column(name = "date_of_birth")
	private Date dateOfBirth;
	
	@Column(name = "address")
	private String address;
	@Column(name = "relation_with_proposer")
	private String relationWithProposer;
	@Column(name = "mobile_no")
	private Long mobileNo;
	@Column(name = "nominee_status")
	private String nomieeStatus ="Yes";
	
	
	
	public String getNomieeStatus() {
		return nomieeStatus;
	}
	public void setNomieeStatus(String nomieeStatus) {
		this.nomieeStatus = nomieeStatus;
	}
	public Integer getProposerId() {
		return proposerId;
	}
	public void setProposerId(Integer proposerId) {
		this.proposerId = proposerId;
	}
	public Integer getNomineeId() {
		return nomineeId;
	}
	public void setNomineeId(Integer nomineeId) {
		this.nomineeId = nomineeId;
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
	public NomineeDetailsEntity(Integer proposerId, Integer nomineeId, String nomineeName, Gender gender,
			Date dateOfBirth, String address, String relationWithProposer, Long mobileNo) {
		super();
		this.proposerId = proposerId;
		this.nomineeId = nomineeId;
		this.nomineeName = nomineeName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		address = address;
		this.relationWithProposer = relationWithProposer;
		this.mobileNo = mobileNo;
	}
	
	
	public NomineeDetailsEntity() {
		// TODO Auto-generated constructor stub
	}

}
