package com.example.demo.request;

import java.sql.Date;

import com.example.demo.entity.NomineeDetailsEntity;
import com.example.demo.enums.Gender;
import com.example.demo.enums.Marital_Status;
import com.example.demo.enums.Title;

public class RequiredDto {
	
	private Title title;
	private String proposerFullName;
	private Gender proposerGender;
	private Date dateOfBirth;
	private String panNumber;
	private Long aadharNo;
	private Marital_Status maritalStatus;
	
	private String proposerEmail;
	private Long proposerMobileNo;
	private Long alterMobileNo;
	
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private Long pincode;
	private String city;
	private String state;
	private String status ="Yes";
	
	private NomineeDto nomineeDetailsEntity;
	
	
	public NomineeDto getNomineeDetailsEntity() {
		return nomineeDetailsEntity;
	}
	public void setNomineeDetailsEntity(NomineeDto nomineeDetailsEntity) {
		this.nomineeDetailsEntity = nomineeDetailsEntity;
	}
	public Title getTitle() {
		return title;
	}
	public void setTitle(Title title) {
		this.title = title;
	}
	public String getProposerFullName() {
		return proposerFullName;
	}
	public void setProposerFullName(String proposerFullName) {
		this.proposerFullName = proposerFullName;
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
	
	

}
