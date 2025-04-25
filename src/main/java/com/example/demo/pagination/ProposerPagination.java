package com.example.demo.pagination;

public class ProposerPagination {
	
	private Integer page;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	
	private Searching searching;
	
	public ProposerPagination() {
		// TODO Auto-generated constructor stub
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	

	public Searching getSearching() {
		return searching;
	}

	public void setSearching(Searching searching) {
		this.searching = searching;
	}

	public ProposerPagination(Integer page, Integer size, String sortBy, String sortOrder) {
		super();
		this.page = page;
		this.size = size;
		this.sortBy = sortBy;
		this.sortOrder = sortOrder;
	}
	
	

}
