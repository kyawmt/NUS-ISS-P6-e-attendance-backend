package sa52.team03.adproject.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

import sa52.team03.adproject.domain.AcademicPeriod.Semester;

@Entity
public class AcademicPeriod {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int year;

	@Enumerated(EnumType.STRING)
	@Column(length = 9)
	private Semester semester;

	private LocalDate startDate;

	private LocalDate endDate;

	@OneToMany(mappedBy = "academicPeriod", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Collection<Class> classes;

	public enum Semester {
		SEMESTER1, SEMESTER2
	}

	public AcademicPeriod() {
		super();
	}

	public AcademicPeriod(int year, Semester semester) {
		super();
		this.year = year;
		this.semester = semester;

		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		if (semester == Semester.SEMESTER1) {
			this.startDate = LocalDate.parse("01/08/" + year, df);
			this.endDate = LocalDate.parse("05/12/" + year, df);
		} else {
			this.startDate = LocalDate.parse("10/01/" + (year + 1), df);
			this.endDate = LocalDate.parse("08/05/" + (year + 1), df);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
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
