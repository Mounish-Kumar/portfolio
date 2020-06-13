package dev.mounish.portfolio.common;

public class PageResponse {
	
	private int number;
	
	private int size;
	
	private long totalElements;
	
	private int totalPages;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	@Override
	public String toString() {
		return "PageResponse [number=" + number + ", size=" + size + ", totalElements=" + totalElements
				+ ", totalPages=" + totalPages + "]";
	}

}
