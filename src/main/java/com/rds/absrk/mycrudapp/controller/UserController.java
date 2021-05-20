package com.rds.absrk.mycrudapp.controller;
/**
 * @author ABSRK Manikanta
 */
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rds.absrk.mycrudapp.model.User;
import com.rds.absrk.mycrudapp.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/welcome")
	public String welcome() {
		return "welcome Table is created";
	}

	@GetMapping("/users")
	public List<User> getUsers() {
		return userRepository.getUsers();
	}

	@PostMapping("/users")
	public void registerUser(@RequestBody User user) {
		userRepository.createUser(user);
	}

	@GetMapping("/userscount")
	public int countUsers() {
		return userRepository.countUsers();
	}

	@GetMapping("/users/{userId}")
	public User findUser(@PathVariable Long userId) {
		return userRepository.findById(userId);

	}

}
