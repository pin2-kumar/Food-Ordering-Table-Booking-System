package com.delivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.dto.UserDto;
import com.delivery.entity.User;
import com.delivery.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

   
    public User registerUser(UserDto dto) {

        // Check email uniqueness
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // encrypt later
        user.setAddress(dto.getAddress());

        return userRepository.save(user);
    }

    //  LOGIN USER
    public User loginUser(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }
    

	
		public User findByMobile(String mobile) {
		    return userRepository.findByMobile(mobile)
		            .orElseThrow(() -> new RuntimeException("User not found"));
		}

		
		public User findOrCreateByMobile(String mobile) {

		    return userRepository.findByMobile(mobile)
		            .orElseGet(() -> {
		                User newUser = new User();
		                newUser.setMobile(mobile);
		                newUser.setName("Guest");
		                return userRepository.save(newUser);
		            });
		}

		
		
		public User setAddress(Long userId,String city,String address){

		    User user = userRepository.findById(userId).orElseThrow();

		    user.setCity(city);

		    user.setAddress(address);

		    return userRepository.save(user);
		}

		public User getUser(Long userId){

		    return userRepository.findById(userId)
		            .orElseThrow(() -> new RuntimeException("User not found"));

		}

		
		public void saveUser(User user){
		    userRepository.save(user);
		}
		
		
		
}
