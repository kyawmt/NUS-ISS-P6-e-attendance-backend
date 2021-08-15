package sa52.team03.adproject.domain;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Class _class;
	
	private LocalDate date;
	
	private String signInId;
	
	private String signOutId;
	
	private Integer predictedAttendance;
	
	@OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Collection<Attendance> attendances;

	public Schedule() {
		super();
	}

	public Schedule(Class _class, LocalDate date, String signInId, String signOutId) {
		super();
		this._class = _class;
		this.date = date;
		this.signInId = signInId;
		this.signOutId = signOutId;
	}

	public Schedule(Class _class, LocalDate date, String signInId, String signOutId, Integer predictedAttendance,
			Collection<Attendance> attendances) {
		super();
		this._class = _class;
		this.date = date;
		this.signInId = signInId;
		this.signOutId = signOutId;
		this.predictedAttendance = predictedAttendance;
		this.attendances = attendances;
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getSignInId() {
		return signInId;
	}

	public void setSignInId(String signInId) {
		this.signInId = signInId;
	}

	public String getSignOutId() {
		return signOutId;
	}

	public void setSignOutId(String signOutId) {
		this.signOutId = signOutId;
	}

	public Integer getPredictedAttendance() {
		return predictedAttendance;
	}

	public void setPredictedAttendance(Integer predictedAttendance) {
		this.predictedAttendance = predictedAttendance;
	}

	public Collection<Attendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(Collection<Attendance> attendances) {
		this.attendances = attendances;
	}

	@Override
	public String toString() {
		return "Schedule [id=" + id + ", _class=" + _class + ", date=" + date + ", signInId=" + signInId
				+ ", signOutId=" + signOutId + ", predictedAttendance=" + predictedAttendance + ", attendances="
				+ attendances + "]";
	}
	
}
