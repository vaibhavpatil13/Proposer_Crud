package com.example.demo.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ProposerDetailsEntity;
import com.example.demo.repository.ProposerDetailsRepository;
import com.example.demo.request.RequestDto;

@Component
public class Validations {

    @Autowired 
	private ProposerDetailsRepository proposerDetailsRepository;
	
	public boolean panCardValidation(String panCardNo) {
		
		char ch[] = panCardNo.toCharArray();
		
		int countLetter =0;
		int countDigit =0;
		
		for(int i=0; i<5; i++) {
			if(Character.isLetter(ch[i])) {
				countLetter++;
			}
			else {
				return false;
			}
		}
		
		for(int i=5; i<9; i++) {
			if(Character.isDigit(ch[i])) {
				countDigit++;
			}
			else {
				return false;
			}
		}
		
		if(!Character.isLetter(ch[9])) {
			return false;
		}
		
		return true;
	}
	
	public String checkConditions(RequestDto requestDto, List<String> missingFields) {
		
		if(requestDto.getTitle()==null || requestDto.getTitle().toString().isEmpty() ){
			missingFields.add("title");
		}
//		String proposerFullName = requestDto.getProposerFullName();
//		if(proposerFullName == null || proposerFullName.isEmpty()){
//			missingFields.add("proposerFullName");
//		}
//		
		if(requestDto.getProposerGender() == null || requestDto.getProposerGender().toString().isEmpty()){
			missingFields.add("proposerGender");
		}
		
		if(requestDto.getDateOfBirth() == null || requestDto.getDateOfBirth().toString().isEmpty()){
			missingFields.add("dateOfBirth");
		}
		
		if(requestDto.getPanNumber() == null || requestDto.getPanNumber().isEmpty()){
			missingFields.add("panNumber");
		}
		
//		if(!Validations.panCardValidation(requestDto.getPanNumber())) {
//			missingFields.add("pancard format is wrong");
//		}
		
		if(!requestDto.getPanNumber().matches("^[A-Z]{5}[0-9]{4}[A-Z]$")) {
			missingFields.add("pancard format is wrong");
		}
		
		if(requestDto.getPanNumber().length()!=10) {
			 missingFields.add("pancard number is not valid");
		}
		
		if(proposerDetailsRepository.existsByPanNumber(requestDto.getPanNumber())) {
			missingFields.add("Pan card number exists");
		}
		
		if(requestDto.getAadharNo() == null || requestDto.getAadharNo().toString().isEmpty()){
			 missingFields.add("aadharNo");
		}
		
		if(requestDto.getAadharNo().toString().length()!=12) {
			 missingFields.add("aadharcard number is not valid");
		}
		
		if(proposerDetailsRepository.existsByAadharNo(requestDto.getAadharNo())) {
			missingFields.add("aadhard card number exists");
		}
		
		if(requestDto.getMaritalStatus() == null || requestDto.getMaritalStatus().toString().isEmpty()){
			missingFields.add("maritalStatus");
		}
		
		if(requestDto.getProposerEmail() == null || requestDto.getProposerEmail().isEmpty()){
			missingFields.add("proposerEmail");
		}
		
		if(proposerDetailsRepository.existsByProposerEmail(requestDto.getProposerEmail())) {
			missingFields.add("Email Id exists");
		}
		
		if(requestDto.getProposerMobileNo() == null || requestDto.getProposerMobileNo().toString().isEmpty()){
			missingFields.add("proposerMobileNo");
		}
		
		if(proposerDetailsRepository.existsByProposerMobileNo(requestDto.getProposerMobileNo())) {
			missingFields.add("proposer mobile number exists");
		}
		
		if(requestDto.getProposerMobileNo().toString().length()!=10){
			missingFields.add("Invalid proposerMobileNo");
		}
		
		if(requestDto.getAddressLine1() == null || requestDto.getAddressLine1().isEmpty()){
			missingFields.add("addressLine1");
		}
		
		if(requestDto.getPincode() == null || requestDto.getPincode().toString().isEmpty()){
			 missingFields.add("pincode");
		}
		
		if(requestDto.getCity() == null || requestDto.getCity().isEmpty()){
			missingFields.add("city");
		}
		
		if(requestDto.getState() == null || requestDto.getState().isEmpty()){
			missingFields.add("state");
		}
		
		if(requestDto.getAlterMobileNo() == null || requestDto.getAlterMobileNo().toString().isEmpty()){
			missingFields.add("alterMobileNo");
		}
		
		
		if (!missingFields.isEmpty()) {
//	    	 throw new IllegalArgumentException("Missing required fields: " + String.join(", ", missingFields));
		        return "Missing required fields: " + String.join(", ", missingFields);
		    }
		
		return null;
		
	}

}
