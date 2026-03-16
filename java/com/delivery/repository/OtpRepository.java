package com.delivery.repository;



import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.delivery.entity.Otp;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByMobile(String mobile);
}