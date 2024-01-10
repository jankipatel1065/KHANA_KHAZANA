package com.humber.khana_khazana;

import com.humber.khana_khazana.models.Role;
import com.humber.khana_khazana.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan
public class KhanaKhazanaApplication implements CommandLineRunner {


    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(KhanaKhazanaApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
    }
    private void initializeRoles() {
        // Check if roles are already present
        if (roleRepository.findByRole("USER") == null) {
            roleRepository.save(new Role("USER"));
        }
        if (roleRepository.findByRole("ADMIN") == null) {
            roleRepository.save(new Role("ADMIN"));
        }
        // Add more roles if needed
    }

}
