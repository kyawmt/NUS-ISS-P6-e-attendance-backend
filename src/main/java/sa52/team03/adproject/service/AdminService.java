package sa52.team03.adproject.service;

import java.util.List;

import sa52.team03.adproject.domain.Lecturer;

public interface AdminService {

	public List<Lecturer> getLecturers();
	
	public Lecturer saveLecturer(Lecturer lecturer);
	
	public Lecturer getLecturerById(int id);
	
	public void deleteLecturer(int id) ;
}
