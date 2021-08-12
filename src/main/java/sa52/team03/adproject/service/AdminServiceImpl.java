package sa52.team03.adproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sa52.team03.adproject.domain.Lecturer;
import sa52.team03.adproject.repo.LecturerRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	LecturerRepository lecturerRepo;
	
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
}
