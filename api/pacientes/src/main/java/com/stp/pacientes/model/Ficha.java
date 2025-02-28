package com.stp.pacientes.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Ficha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String medicamentos;
    private String historico;
    private String alergias;
}
