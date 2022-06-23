package com.sanidhya.ExpenseTrackingApp.Services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sanidhya.ExpenseTrackingApp.Exceptions.ResourceNotFoundException;
import com.sanidhya.ExpenseTrackingApp.Entity.User;
import com.sanidhya.ExpenseTrackingApp.Entity.UserModel;
import com.sanidhya.ExpenseTrackingApp.Exceptions.ItemExistsException;
import com.sanidhya.ExpenseTrackingApp.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User createUser(UserModel user) {
		if(userRepository.existsByEmail(user.getEmail()))
		{
			throw new ItemExistsException("User already registered with email "+user.getEmail());
		}
		User newUser=new User();
		BeanUtils.copyProperties(user, newUser);
		newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
		return userRepository.save(newUser);
	}

	@Override
	public User readUser(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for the id: "+id));
	}

	@Override
	public User updateUser(UserModel user,Long id) {
		// TODO Auto-generated method stub
		User existingUser=readUser(id);
		existingUser.setName(user.getName() != null ? user.getName():existingUser.getName());
		existingUser.setEmail(user.getEmail() != null ? user.getEmail():existingUser.getEmail());
		existingUser.setPassword(user.getPassword() != null ? bcryptEncoder.encode(existingUser.getPassword()):existingUser.getPassword());
		existingUser.setAge(user.getAge() != null ? user.getAge():existingUser.getAge());
		return userRepository.save(existingUser);
	}

	@Override
	public void deleteUser(Long id) {
		User existingUser=readUser(id);
		userRepository.delete(existingUser);
	}

}
