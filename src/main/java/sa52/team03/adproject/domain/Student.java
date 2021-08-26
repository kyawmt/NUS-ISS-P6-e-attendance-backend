package sa52.team03.adproject.domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String studentId = "STU" + RandomStringUtils.randomNumeric(5);

	private String firstName;

	private String lastName;

	private String userName;

	private String password = "123456";

	private boolean photoRegistered = false;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Collection<Enrolment> enrolments;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Collection<Attendance> attendances;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Collection<StudentLeave> studentLeaves;

	public Student() {
		super();

	}

	public Student(String firstName, String lastName, String userName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
	}

	public Student(String studentId, String firstName, String lastName, String userName, String password) {
		super();
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
	}

	public Student(String studentId, String firstName, String lastName, String userName, String password,
			Collection<Enrolment> enrolments, Collection<Attendance> attendances,
			Collection<StudentLeave> studentLeaves) {
		super();
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.enrolments = enrolments;
		this.attendances = attendances;
		this.studentLeaves = studentLeaves;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStudentId() {
		return studentId;
	}

	public boolean isPhotoRegistered() {
		return photoRegistered;
	}

	public void setPhotoRegistered(boolean isPhotoRegistered) {
		this.photoRegistered = isPhotoRegistered;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Enrolment> getEnrolments() {
		return enrolments;
	}

	public void setEnrolments(Collection<Enrolment> enrolments) {
		this.enrolments = enrolments;
	}

	public Collection<Attendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(Collection<Attendance> attendances) {
		this.attendances = attendances;
	}

	public Collection<StudentLeave> getStudentLeaves() {
		return studentLeaves;
	}

	public void setStudentLeaves(Collection<StudentLeave> studentLeaves) {
		this.studentLeaves = studentLeaves;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", studentId=" + studentId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", userName=" + userName + ", password=" + password + ", enrolments=" + enrolments + ", attendances="
				+ attendances + ", studentLeaves=" + studentLeaves + "]";
	}

}
