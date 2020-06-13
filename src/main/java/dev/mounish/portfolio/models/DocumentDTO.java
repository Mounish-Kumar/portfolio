package dev.mounish.portfolio.models;

import java.util.Date;

import javax.validation.constraints.NotBlank;

public class DocumentDTO {

	private Long id;

	@NotBlank(message = "Mandatory")
	private String key;

	@NotBlank(message = "Mandatory")
	private String fileName;
	
	@NotBlank(message = "Mandatory")
	private String fileContent;

	private Date creationDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "DocumentDTO [id=" + id + ", key=" + key + ", fileName=" + fileName + ", fileContent=" + fileContent
				+ ", creationDate=" + creationDate + "]";
	}
	
}
