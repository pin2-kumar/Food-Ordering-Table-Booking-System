package com.delivery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.delivery.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

	Optional<User> findByMobile(String mobile); 
    
}
