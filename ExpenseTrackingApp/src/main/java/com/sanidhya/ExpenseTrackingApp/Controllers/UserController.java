package com.sanidhya.ExpenseTrackingApp.Controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.sanidhya.ExpenseTrackingApp.Entity.User;
import com.sanidhya.ExpenseTrackingApp.Entity.UserModel;
import com.sanidhya.ExpenseTrackingApp.Services.UserService;

@RestController
@ResponseBody
public class UserController {
	
	@Autowired
	private UserService userService;
	
	

	@GetMapping("/user/{id}")
	public ResponseEntity<User> readUser(@PathVariable Long id) {
		return new ResponseEntity<User>(userService.readUser(id), HttpStatus.OK);
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@RequestBody UserModel user,@PathVariable Long id)
	{
		return new ResponseEntity<User>(userService.updateUser(user, id), HttpStatus.OK);	
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
}
