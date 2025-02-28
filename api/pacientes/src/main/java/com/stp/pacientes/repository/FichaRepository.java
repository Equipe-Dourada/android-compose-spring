package com.stp.pacientes.repository;

import com.stp.pacientes.model.Ficha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FichaRepository extends JpaRepository<Ficha, Long> {}
