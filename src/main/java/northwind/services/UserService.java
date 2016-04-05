package northwind.services;

import java.util.Map;

import org.springframework.data.neo4j.repository.GraphRepository;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Service;

import northwind.entities.User;
import northwind.controllers.forms.UserForm;
import northwind.repositories.UserRepository;
import northwind.services.utils.GenericCRUDService;

@Service
public class UserService extends GenericCRUDService<User, UserForm> {
	final static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserRepository userRepository;
	
	@Override
	public GraphRepository<User> getRepository() {
		return userRepository;
	}

	@Override
	public void convertToForm(User user, UserForm form) {
		form.setId(user.getId().toString());
		form.setEmail(user.getEmail());
		form.setPassword(user.getPassword());
		form.setBirthdate(user.getBirthdate());
		form.setBirthplace(user.getBirthplace());
		form.setProfileImage(user.getProfileImage());
	}

	@Override
	public User convertToEntity(UserForm form) {
		User user = new User();
		if (StringUtils.isNotEmpty(form.getId())) {
			user.setId(Long.parseLong(form.getId()));
		}
		user.setEmail(form.getEmail());
		user.setPassword(form.getPassword());
		user.setBirthdate(form.getBirthdate());
		user.setBirthplace(form.getBirthplace());
		user.setProfileImage(form.getProfileImage());
		
		return user;
	}

	@Override
	public Iterable<Map<String, Object>> entityIDs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer maxEntityID() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Iterable<User> getAll() {
		return userRepository.getAll();
	}

}
