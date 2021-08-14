package sa52.team03.adproject.domain;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class AcademicPeriod {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String year;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 9)
	private Semester semester;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "academicPeriod", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<Class> classes;
	
	public enum Semester {
	SEMESTER1, SEMESTER2
	}

	public AcademicPeriod() {
		super();
	}

	public AcademicPeriod(String year, Semester semester, LocalDate startDate, LocalDate endDate) {
		super();
		this.year = year;
		this.semester = semester;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public AcademicPeriod(String year, Semester semester, LocalDate startDate, LocalDate endDate,
			Collection<Class> classes) {
		super();
		this.year = year;
		this.semester = semester;
		this.startDate = startDate;
		this.endDate = endDate;
		this.classes = classes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
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

	public Collection<Class> getClasses() {
		return classes;
	}

	public void setClasses(Collection<Class> classes) {
		this.classes = classes;
	}

	@Override
	public String toString() {
		return "AcademicPeriod [id=" + id + ", year=" + year + ", semester=" + semester + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", classes=" + classes + "]";
	}
		
}
