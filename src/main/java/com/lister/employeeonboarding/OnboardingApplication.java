package com.lister.employeeonboarding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnboardingApplication {

    Logger log = LoggerFactory.getLogger(OnboardingApplication.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(OnboardingApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
