package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "batchQueue")
public class BatchQueue {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "queue_id")
	private Integer queueId;
	@Column(name = "file_path")
	private String filePath;
	@Column(name = "is_process")
	private String isProcess;
	@Column(name = "row_count")
	private Integer rowCount;
	@Column(name = "row_read")
	private Integer rowRead;
	@Column(name = "status")
	private String status;
	@Column(name = "last_process_count")
	private Integer lastProcessCount;
	
	public BatchQueue() {
		// TODO Auto-generated constructor stub
	}

	public Integer getQueueId() {
		return queueId;
	}

	public void setQueueId(Integer queueId) {
		this.queueId = queueId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getIsProcess() {
		return isProcess;
	}

	public void setIsProcess(String isProcess) {
		this.isProcess = isProcess;
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	public Integer getRowRead() {
		return rowRead;
	}

	public void setRowRead(Integer rowRead) {
		this.rowRead = rowRead;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getLastProcessCount() {
		return lastProcessCount;
	}

	public void setLastProcessCount(Integer lastProcessCount) {
		this.lastProcessCount = lastProcessCount;
	}

	public BatchQueue(Integer queueId, String filePath, String isProcess, Integer rowCount, Integer rowRead,
			String status, Integer lastProcessCount) {
		super();
		this.queueId = queueId;
		this.filePath = filePath;
		this.isProcess = isProcess;
		this.rowCount = rowCount;
		this.rowRead = rowRead;
		this.status = status;
		this.lastProcessCount = lastProcessCount;
	}

	@Override
	public String toString() {
		return "BatchQueue [queueId=" + queueId + ", filePath=" + filePath + ", isProcess=" + isProcess + ", rowCount="
				+ rowCount + ", rowRead=" + rowRead + ", status=" + status + ", lastProcessCount=" + lastProcessCount
				+ "]";
	}
	
	
	

}
