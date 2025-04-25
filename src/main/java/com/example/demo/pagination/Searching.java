package com.example.demo.pagination;

public class Searching {
	
	private String proposerFirstName;
	private String proposerLastName;
	private Long proposerMobileNo;
	private String city;
	
	public Searching() {
		// TODO Auto-generated constructor stub
	}

	public String getProposerFirstName() {
		return proposerFirstName;
	}

	public void setProposerFirstName(String proposerFirstName) {
		this.proposerFirstName = proposerFirstName;
	}

	public String getProposerLastName() {
		return proposerLastName;
	}

	public void setProposerLastName(String proposerLastName) {
		this.proposerLastName = proposerLastName;
	}

	public Long getProposerMobileNo() {
		return proposerMobileNo;
	}

	public void setProposerMobileNo(Long proposerMobileNo) {
		this.proposerMobileNo = proposerMobileNo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Searching(String proposerFirstName, String proposerLastName, Long proposerMobileNo, String city) {
		super();
		this.proposerFirstName = proposerFirstName;
		this.proposerLastName = proposerLastName;
		this.proposerMobileNo = proposerMobileNo;
		this.city = city;
	}



	
	
	

}
