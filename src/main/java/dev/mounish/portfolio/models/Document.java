package dev.mounish.portfolio.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "Document")
@Table(name = "DOCUMENT")
public class Document {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@NotBlank(message = "Mandatory")
	@Column(name = "KEY")
	private String key;
	
	@NotBlank(message = "Mandatory")
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@NotBlank(message = "Mandatory")
	@Column(name = "FILE_CONTENT")
	private String fileContent;
	
	@CreationTimestamp
	@Column(name = "CREATION_DATE", updatable = false)
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
		return "Document [id=" + id + ", key=" + key + ", fileName=" + fileName + ", fileContent="
				+ fileContent + ", creationDate=" + creationDate + "]";
	}
}
