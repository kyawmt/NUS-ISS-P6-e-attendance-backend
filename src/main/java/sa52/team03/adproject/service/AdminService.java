package sa52.team03.adproject.service;

import java.util.List;
import sa52.team03.adproject.domain.Module;
import sa52.team03.adproject.domain.Lecturer;


public interface AdminService {

	public List<Lecturer> getLecturers();
	
	public Lecturer saveLecturer(Lecturer lecturer);
	
	public Lecturer getLecturerById(int id);
	
	public void deleteLecturer(int id);
	
	public List<Module> getModules();
	
	public Module getModuleById(int id);
	
	public Module saveModule(Module module);
	
	public void deleteModule(int id);
}
