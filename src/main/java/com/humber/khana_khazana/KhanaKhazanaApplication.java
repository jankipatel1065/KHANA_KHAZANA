package com.humber.khana_khazana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan
public class KhanaKhazanaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KhanaKhazanaApplication.class, args);
    }

}
