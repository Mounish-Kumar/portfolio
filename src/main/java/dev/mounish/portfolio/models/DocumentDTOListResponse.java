package dev.mounish.portfolio.models;

import java.util.List;

import dev.mounish.portfolio.common.PageResponse;

public class DocumentDTOListResponse extends PageResponse {
	
	List<DocumentDTO> content;

	public List<DocumentDTO> getContent() {
		return content;
	}

	public void setContent(List<DocumentDTO> content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "DocumentDTOListResponse [content=" + content + "]";
	}

}
