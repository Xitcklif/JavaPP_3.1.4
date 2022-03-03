package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

	private final UserServiceImpl userService;
	private final UserDetailsService userDetailsService;

	public RestController(UserServiceImpl userService,
						  UserDetailsService userDetailsService) {
		this.userService = userService;
		this.userDetailsService = userDetailsService;
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
		return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
	}

	@GetMapping("/user")
	public ResponseEntity<User> getUser(Principal principal) {
		return new ResponseEntity<>(userService.getUserByUsername(
				userDetailsService.loadUserByUsername(principal.getName()).getUsername()),
				HttpStatus.OK);
	}

	@PostMapping("/users")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		userService.save(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
		userService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/users")
	public ResponseEntity<User> editUser(@RequestBody User user) {
		userService.update(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
