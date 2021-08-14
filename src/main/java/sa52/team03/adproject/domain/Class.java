package sa52.team03.adproject.domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Class {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private Integer size;
	
	@JsonBackReference
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private AcademicPeriod academicPeriod;
	
	@JsonBackReference
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Module module;
	
	@JsonBackReference
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "lecturer_id", nullable = true)
	private Lecturer lecturer;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "_class", cascade = { CascadeType.PERSIST, CascadeType.MERGE })	
	private Collection<Schedule> schedules;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "_class", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Collection<Enrolment> enrolments;

	public Class() {
		super();
	}

	public Class(Integer size) {
		super();
		this.size = size;
	}

	public Class(Integer size, AcademicPeriod academicPeriod, Module module, Lecturer lecturer) {
		super();
		this.size = size;
		this.academicPeriod = academicPeriod;
		this.module = module;
		this.lecturer = lecturer;
	}

	public Class(Integer size, AcademicPeriod academicPeriod, Module module, Lecturer lecturer,
			Collection<Schedule> schedules, Collection<Enrolment> enrolments) {
		super();
		this.size = size;
		this.academicPeriod = academicPeriod;
		this.module = module;
		this.lecturer = lecturer;
		this.schedules = schedules;
		this.enrolments = enrolments;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public AcademicPeriod getAcademicPeriod() {
		return academicPeriod;
	}

	public void setAcademicPeriod(AcademicPeriod academicPeriod) {
		this.academicPeriod = academicPeriod;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Lecturer getLecturer() {
		return lecturer;
	}

	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}

	public Collection<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(Collection<Schedule> schedules) {
		this.schedules = schedules;
	}

	public Collection<Enrolment> getEnrolments() {
		return enrolments;
	}

	public void setEnrolments(Collection<Enrolment> enrolments) {
		this.enrolments = enrolments;
	}

	@Override
	public String toString() {
		return "Class [id=" + id + ", size=" + size + ", academicPeriod=" + academicPeriod + ", module=" + module
				+ ", lecturer=" + lecturer + ", schedules=" + schedules + ", enrolments=" + enrolments + "]";
	}
	
}
