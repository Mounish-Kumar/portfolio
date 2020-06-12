package dev.mounish.portfolio.common;

import java.util.List;

public class SearchRequest {
	
	private Integer pageNumber;
	
	private Integer pageSize;
	
	private String sortColumn;
	
	private String sortOrder;
	
	List<QueryParam> filters;

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public List<QueryParam> getFilters() {
		return filters;
	}

	public void setFilters(List<QueryParam> filters) {
		this.filters = filters;
	}

	@Override
	public String toString() {
		return "SearchRequest [pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", sortColumn=" + sortColumn
				+ ", sortOrder=" + sortOrder + ", filters=" + filters + "]";
	}
	
	
}
