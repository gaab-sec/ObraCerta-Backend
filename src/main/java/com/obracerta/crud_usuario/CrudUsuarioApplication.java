package com.obracerta.crud_usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan; 
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.obracerta")

@EnableJpaRepositories(basePackages = "com.obracerta")

@EntityScan(basePackages = "com.obracerta")

public class CrudUsuarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudUsuarioApplication.class, args);
    }

}