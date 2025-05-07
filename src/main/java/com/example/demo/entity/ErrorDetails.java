package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "errorDetails")
public class ErrorDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "error_id")
	private Integer errorId;
	@Column(name = "status")
	private String status;
	@Column(name = "error")
	private String error;
	@Column(name = "error_field")
	private String errorField;
	@Column(name = "row_index")
	private Integer rowIndex;
	@Column(name = "queue_id")
	private Integer queueId;

	public ErrorDetails() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Integer getQueueId() {
		return queueId;
	}



	public void setQueueId(Integer queueId) {
		this.queueId = queueId;
	}



	public Integer getErrorId() {
		return errorId;
	}

	public void setErrorId(Integer errorId) {
		this.errorId = errorId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorField() {
		return errorField;
	}

	public void setErrorField(String errorField) {
		this.errorField = errorField;
	}

	public Integer getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}



	public ErrorDetails(Integer errorId, String status, String error, String errorField, Integer rowIndex,
			Integer queueId) {
		super();
		this.errorId = errorId;
		this.status = status;
		this.error = error;
		this.errorField = errorField;
		this.rowIndex = rowIndex;
		this.queueId = queueId;
	}

	

	
	
	

}
