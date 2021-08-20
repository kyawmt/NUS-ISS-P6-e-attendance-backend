package sa52.team03.adproject.domain;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class StudentLeave {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Student student;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String reason;
	
	private String description;
	
	private String documentUrl;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 8)
	private Status status;
	
	public enum Status {
	APPROVED, REJECTED , PENDING
	}

	public StudentLeave() {
		super();
	}

	public StudentLeave(Student student, LocalDate startDate, LocalDate endDate, String reason, String description,
			String documentUrl, Status status) {
		super();
		this.student = student;
		this.startDate = startDate;
		this.endDate = endDate;
		this.reason = reason;
		this.description = description;
		this.documentUrl = documentUrl;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDocumentUrl() {
		return documentUrl;
	}

	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	 
	public String toStringforStatus(Status status) {
		return status.toString();
	}

	@Override
	public String toString() {
		return "StudentLeave [id=" + id + ", student=" + student + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", reason=" + reason + ", description=" + description + ", documentUrl=" + documentUrl + ", status="
				+ status + "]";
	}
	
}
