package com.resfeber.pool.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.resfeber.pool"})
public class ResferberPoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResferberPoolApplication.class, args);
    }
}
