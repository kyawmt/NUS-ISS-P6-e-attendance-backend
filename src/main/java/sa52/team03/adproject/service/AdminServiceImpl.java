package sa52.team03.adproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sa52.team03.adproject.domain.Lecturer;
import sa52.team03.adproject.domain.Module;
import sa52.team03.adproject.repo.LecturerRepository;
import sa52.team03.adproject.repo.ModuleRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	LecturerRepository lecturerRepo;
	
	@Autowired
	ModuleRepository moduleRepo;
	
	@Override
	public List<Lecturer> getLecturers(){
		return lecturerRepo.findAll();
	}
	
	@Override
	public Lecturer saveLecturer(Lecturer lecturer) {
		return lecturerRepo.save(lecturer);
	}
	
	@Override
	public Lecturer getLecturerById(int id) {
		return lecturerRepo.getById(id);
	}
	
	@Override
	public void deleteLecturer(int id) {
		lecturerRepo.deleteById(id);
	}

	@Override
	public List<Module> getModules() {
		return moduleRepo.findAll();
	}

	@Override
	public Module getModuleById(int id) {
		return moduleRepo.findById(id).get();
	}

	@Override
	public Module saveModule(Module module) {
		return moduleRepo.save(module);
	}

	@Override
	public void deleteModule(int id) {
		moduleRepo.deleteById(id);
	}
}
