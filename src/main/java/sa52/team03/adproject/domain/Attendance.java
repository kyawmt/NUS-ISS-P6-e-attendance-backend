package sa52.team03.adproject.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Attendance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Schedule schedule;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Student student;
	
	private Boolean signIn;
	
	private Boolean signOut;

	public Attendance() {
		super();
	}

	public Attendance(Schedule schedule, Student student, Boolean signIn, Boolean signOut) {
		super();
		this.schedule = schedule;
		this.student = student;
		this.signIn = signIn;
		this.signOut = signOut;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Boolean getSignIn() {
		return signIn;
	}

	public void setSignIn(Boolean signIn) {
		this.signIn = signIn;
	}

	public Boolean getSignOut() {
		return signOut;
	}

	public void setSignOut(Boolean signOut) {
		this.signOut = signOut;
	}

	@Override
	public String toString() {
		return "Attendance [id=" + id + ", schedule=" + schedule + ", student=" + student + ", signIn=" + signIn
				+ ", signOut=" + signOut + "]";
	}
	
}
