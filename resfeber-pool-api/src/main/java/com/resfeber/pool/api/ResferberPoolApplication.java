package com.resfeber.pool.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.resfeber.pool"})
public class ResferberPoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResferberPoolApplication.class, args);
    }
}
