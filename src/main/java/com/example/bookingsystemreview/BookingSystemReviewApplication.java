package com.example.bookingsystemreview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookingSystemReviewApplication {

    public static void main(String[] args) {
        System.out.println("Test for docker upload and server pull");
        SpringApplication.run(BookingSystemReviewApplication.class, args);
    }

}
