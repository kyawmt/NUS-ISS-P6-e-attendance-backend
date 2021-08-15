package sa52.team03.adproject.domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Lecturer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String staffId;	
	
	private String firstName;
	
	private String lastName;
	
	private String userName;
	
	private String password;
	
	@OneToMany(mappedBy = "lecturer", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JsonIgnore
	private Collection<Class> classes;

	public Lecturer() {
		super();
	}

	public Lecturer(String staffId, String firstName, String lastName, String userName, String password) {
		super();
		this.staffId = staffId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
	}

	public Lecturer(String staffId, String firstName, String lastName, String userName, String password,
			Collection<Class> classes) {
		super();
		this.staffId = staffId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.classes = classes;
	}
	
	public Lecturer(String firstName, String lastName, String userName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
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

	public Collection<Class> getClasses() {
		return classes;
	}

	public void setClasses(Collection<Class> classes) {
		this.classes = classes;
	}

	@Override
	public String toString() {
		return "Lecturer [id=" + id + ", staffId=" + staffId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", userName=" + userName + ", password=" + password + ", classes=" + classes + "]";
	}


	
}
