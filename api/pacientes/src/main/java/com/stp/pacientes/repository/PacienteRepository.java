package com.stp.pacientes.repository;

import com.stp.pacientes.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, String> {}
