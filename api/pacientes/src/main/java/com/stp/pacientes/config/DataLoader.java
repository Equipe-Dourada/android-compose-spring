package com.stp.pacientes.config;

import com.stp.pacientes.model.Usuario;
import com.stp.pacientes.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner loadData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                Usuario user = new Usuario();
                user.setUsername("usuario");
                user.setPassword(passwordEncoder.encode("senha123"));
                user.setEnabled(true);
                userRepository.save(user);
            }
        };
    }
}
