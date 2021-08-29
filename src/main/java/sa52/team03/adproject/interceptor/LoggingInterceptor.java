package sa52.team03.adproject.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import sa52.team03.adproject.domain.Admin;
import sa52.team03.adproject.domain.Lecturer;
import sa52.team03.adproject.domain.Student;
import sa52.team03.adproject.repo.AdminRepository;
import sa52.team03.adproject.repo.LecturerRepository;
import sa52.team03.adproject.repo.StudentRepository;
import sa52.team03.adproject.utils.TokenUtil;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

	@Autowired
	TokenUtil tokenUtil;

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	LecturerRepository lecturerRepository;

	@Autowired
	StudentRepository studentRepository;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String uri = request.getRequestURI();
		String token = request.getHeader("JwtToken");

		if (token != null && !tokenUtil.isTokenExpired(token)) {

			String username = tokenUtil.getUsernameFromToken(token);
			Admin admin = adminRepository.findByUserName(username);
			Lecturer lecturer = lecturerRepository.findByUserName(username);
			Student student = studentRepository.findByUserName(username);

			if ((uri.startsWith("/api/lecturer") && lecturer != null) 
					|| (uri.startsWith("/api/admin") && admin != null)
					|| (uri.startsWith("/api/student") && student != null)) {
				return true;
			}
		} else if (uri.startsWith("/token")) {
			return true;
		}
		return false;
	}
}
