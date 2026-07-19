package com.vidstream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VidstreamApplication {
    public static void main(String[] args) {
        SpringApplication.run(VidstreamApplication.class, args);
    }
}
