package com.example.demo.entity;

import java.sql.Date;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import com.example.demo.enums.Gender;
import com.example.demo.enums.Marital_Status;
import com.example.demo.enums.Title;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "proposal")
public class ProposerDetailsEntity {

	// personal details
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "proposer_id")
	private Integer proposerId;
	@Enumerated(EnumType.STRING)
	@Column(name = "title")
	private Title title;
	
	@Column(name = "proposer_first_name")
	private String proposerFirstName ;
	@Column(name = "proposer_middle_name")
	private String proposerMiddleName;
	@Column(name = "proposer_last_name")
	private String proposerLastName;

	@Enumerated(EnumType.STRING)
	@Column(name = "proposer_gender")
	private Gender proposerGender;

	@Column(name = "date_of_birth")
	private Date dateOfBirth;
	@Column(name = "pan_number")
	private String panNumber;
	@Column(name = "aadhar_no")
	private Long aadharNo;
	@Enumerated(EnumType.STRING)
	@Column(name = "marital_status")
	private Marital_Status maritalStatus;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDate createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDate updatedAt;

	public Integer getProposerId() {
		return proposerId;
	}

	public void setProposerId(Integer proposerId) {
		this.proposerId = proposerId;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public Gender getProposerGender() {
		return proposerGender;
	}

	public void setProposerGender(Gender proposerGender) {
		this.proposerGender = proposerGender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public Long getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(Long aadharNo) {
		this.aadharNo = aadharNo;
	}

	public Marital_Status getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(Marital_Status maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	

	public String getProposerFirstName() {
		return proposerFirstName;
	}

	public void setProposerFirstName(String proposerFirstName) {
		this.proposerFirstName = proposerFirstName;
	}

	public String getProposerMiddleName() {
		return proposerMiddleName;
	}

	public void setProposerMiddleName(String proposerMiddleName) {
		this.proposerMiddleName = proposerMiddleName;
	}

	public String getProposerLastName() {
		return proposerLastName;
	}

	public void setProposerLastName(String proposerLastName) {
		this.proposerLastName = proposerLastName;
	}



	// contact details
	@Column(name = "proposer_email")
	private String proposerEmail;
	@Column(name = "proposer_mobile_no")
	private Long proposerMobileNo;
	@Column(name = "alter_mobile_no")
	private Long alterMobileNo;

	public String getProposerEmail() {
		return proposerEmail;
	}

	public void setProposerEmail(String proposerEmail) {
		this.proposerEmail = proposerEmail;
	}

	public Long getProposerMobileNo() {
		return proposerMobileNo;
	}

	public void setProposerMobileNo(Long proposerMobileNo) {
		this.proposerMobileNo = proposerMobileNo;
	}

	public Long getAlterMobileNo() {
		return alterMobileNo;
	}

	public void setAlterMobileNo(Long alterMobileNo) {
		this.alterMobileNo = alterMobileNo;
	}

	// address details
	@Column(name = "address_line1")
	private String addressLine1;
	@Column(name = "address_line2")
	private String addressLine2;
	@Column(name = "address_line3")
	private String addressLine3;
	@Column(name = "pincode")
	private Long pincode;
	@Column(name = "city")
	private String city;
	@Column(name = "state")
	private String state;
	@Column(name = "status")
	private String status = "Yes";

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public Long getPincode() {
		return pincode;
	}

	public void setPincode(Long pincode) {
		this.pincode = pincode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "ProposerDetailsEntity [proposerId=" + proposerId + ", title=" + title + ", proposerFullName="
				 + ", proposerGender=" + proposerGender + ", dateOfBirth=" + dateOfBirth
				+ ", panNumber=" + panNumber + ", aadharNo=" + aadharNo + ", maritalStatus=" + maritalStatus
				+ ", proposerEmail=" + proposerEmail + ", proposerMobileNo=" + proposerMobileNo + ", alterMobileNo="
				+ alterMobileNo + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2
				+ ", addressLine3=" + addressLine3 + ", pincode=" + pincode + ", city=" + city + ", state=" + state
				+ "]";
	}

	public ProposerDetailsEntity(Integer proposerId, Title title, String proposerFullName, Gender proposerGender,
			Date dateOfBirth, String panNumber, Long aadharNo, Marital_Status maritalStatus, String proposerEmail,
			Long proposerMobileNo, Long alterMobileNo, String addressLine1, String addressLine2, String addressLine3,
			Long pincode, String city, String state) {
		super();
		this.proposerId = proposerId;
		this.title = title;
		this.proposerGender = proposerGender;
		this.dateOfBirth = dateOfBirth;
		this.panNumber = panNumber;
		this.aadharNo = aadharNo;
		this.maritalStatus = maritalStatus;
		this.proposerEmail = proposerEmail;
		this.proposerMobileNo = proposerMobileNo;
		this.alterMobileNo = alterMobileNo;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.addressLine3 = addressLine3;
		this.pincode = pincode;
		this.city = city;
		this.state = state;
	}

	public ProposerDetailsEntity() {
		// TODO Auto-generated constructor stub
	}

}
