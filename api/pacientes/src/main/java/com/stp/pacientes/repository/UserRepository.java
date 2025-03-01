package com.stp.pacientes.repository;

import com.stp.pacientes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
}
