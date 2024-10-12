package org.josafamelo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan
public class ConsultaCEPApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsultaCEPApplication.class, args);
    }
}