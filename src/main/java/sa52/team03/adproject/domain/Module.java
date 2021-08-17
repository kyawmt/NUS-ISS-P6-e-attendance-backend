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
public class Module {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String code;
	
	private String name;
	
	private Integer minAttendance;
	
	@OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Collection<Class> classes;

	public Module() {
		super();
	}

	public Module(String code, String name, Integer minAttendance) {
		super();
		this.code = code;
		this.name = name;
		this.minAttendance = minAttendance;
	}

	public Module(String code, String name, Integer minAttendance, Collection<Class> classes) {
		super();
		this.code = code;
		this.name = name;
		this.minAttendance = minAttendance;
		this.classes = classes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMinAttendance() {
		return minAttendance;
	}

	public void setMinAttendance(Integer minAttendance) {
		this.minAttendance = minAttendance;
	}

	public Collection<Class> getClasses() {
		return classes;
	}

	public void setClasses(Collection<Class> classes) {
		this.classes = classes;
	}

	@Override
	public String toString() {
		return "Module [id=" + id + ", code=" + code + ", name=" + name + ", minAttendance=" + minAttendance
				+ ", classes=" + classes + "]";
	}
}
