package sa52.team03.adproject.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Enrolment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JsonBackReference
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Class _class;
	
	@JsonBackReference
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Student student;
	
	private String predictedPerformance;

	public Enrolment() {
		super();
	}
	
	public Enrolment(Class _class, Student student) {
		super();
		this._class = _class;
		this.student = student;
	}

	public Enrolment(Class _class, Student student, String predictedPerformance) {
		super();
		this._class = _class;
		this.student = student;
		this.predictedPerformance = predictedPerformance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Class get_class() {
		return _class;
	}

	public void set_class(Class _class) {
		this._class = _class;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getPredictedPerformance() {
		return predictedPerformance;
	}

	public void setPredictedPerformance(String predictedPerformance) {
		this.predictedPerformance = predictedPerformance;
	}

	@Override
	public String toString() {
		return "Enrolment [id=" + id + ", _class=" + _class + ", student=" + student + ", predictedPerformance="
				+ predictedPerformance + "]";
	}
	
}
