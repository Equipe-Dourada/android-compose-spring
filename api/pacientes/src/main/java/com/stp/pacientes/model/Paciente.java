package com.stp.pacientes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {
    @Id
    @Column(length = 11, nullable = false, unique = true)
    private String cpf;
    private String nome;
    private String telefone;
    private String email;
    @Embedded
    private Endereco endereco;
    @OneToOne(cascade = CascadeType.ALL)
    private Ficha ficha;
}
