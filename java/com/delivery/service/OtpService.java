package com.delivery.service;

import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.delivery.entity.Otp;
import com.delivery.repository.OtpRepository;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    public String generateOtp(String mobile) {

        Random random = new Random();
        String otpValue = String.valueOf(100000 + random.nextInt(900000));

        Otp otp = otpRepository.findByMobile(mobile)
                .orElse(new Otp());

        otp.setMobile(mobile);
        otp.setOtp(otpValue);

        otpRepository.save(otp);

        System.out.println("Generated OTP: " + otpValue); // console me milega

        return otpValue;
    }

    public boolean verifyOtp(String mobile, String otpValue) {

        Otp otp = otpRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        return otp.getOtp().equals(otpValue);
    }


}