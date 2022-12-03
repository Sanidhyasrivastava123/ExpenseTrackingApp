package com.sanidhya.ExpenseTrackingApp.Services;

import com.sanidhya.ExpenseTrackingApp.Entity.User;
import com.sanidhya.ExpenseTrackingApp.Entity.UserModel;
import com.sanidhya.ExpenseTrackingApp.Exceptions.ItemExistsException;
import com.sanidhya.ExpenseTrackingApp.Exceptions.ResourceNotFoundException;
import com.sanidhya.ExpenseTrackingApp.Repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImlp implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

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
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with the id :"+id));


    }

    @Override
    public User updateUser(UserModel user, Long id) {
        User existingUser=readUser(id);
        existingUser.setFirstname(user.getFirstname() != null ? user.getFirstname():existingUser.getFirstname());
        existingUser.setEmail(user.getEmail() != null ? user.getEmail():existingUser.getEmail());
        existingUser.setLastname(user.getLastname() != null ? user.getLastname():existingUser.getLastname());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        // TODO Auto-generated method stub
        User existingUser=readUser(id);
        userRepository.delete(existingUser);

    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found for the email"+email));
    }

}
