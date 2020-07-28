package dev.mounish.portfolio.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "Message")
@Table(name = "MESSAGE")
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "MESSAGE_ID_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;
	
	@NotBlank(message = "Mandatory")
	@Column(name = "NAME")
	private String name;

	@NotBlank(message = "Mandatory")
	@Email(message = "Invalid")
	@Column(name = "EMAIL")
	private String email;
	
	@NotBlank(message = "Mandatory")
	@Column(name = "MOBILE")
	private String mobile;
	
	@NotBlank(message = "Mandatory")
	@Size(min = 10, max = 2000, message = "Must be between 10 and 2000 characters")
	@Column(name = "MESSAGE")
	private String message;
	
	@Column(name = "IP_ADDRESS")
	private String ipAddress;
	
	@CreationTimestamp
	@Column(name = "CREATION_DATE", updatable = false)
	private Date creationDate;
	
	@Column(name = "STATUS")
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", name=" + name + ", email=" + email + ", mobile=" + mobile + ", message="
				+ message + ", ipAddress=" + ipAddress + ", creationDate=" + creationDate + ", status=" + status + "]";
	}

}
