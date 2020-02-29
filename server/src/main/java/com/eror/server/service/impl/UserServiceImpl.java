package com.eror.server.service.impl;


import com.eror.server.model.User;
import com.eror.server.repository.UserRepository;
import com.eror.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User save(User entity) {
		return userRepository.save(entity);
	}

	@Override
	public User update(User entity) {
		return userRepository.save(entity);
	}

	@Override
	public void delete(User entity) {
		userRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User find(Long id) {
		return userRepository.findUserById(id);
	}

	@Override
	public List<User> findAll() {

		return userRepository.findAll();
	}

	@Override
	public boolean authenticate(String username, String password) {
		User user = this.findByEmail(username);
		if (user == null) {
			return false;
		} else {
			if (password.equals(user.getPassword())) return true;
			else return false;
		}
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void deleteInBatch(List<User> users) {
		userRepository.deleteInBatch(users);
	}

}
