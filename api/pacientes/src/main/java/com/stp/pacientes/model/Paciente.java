package com.stp.pacientes.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Paciente {
    @Id
    @Column(length = 11, nullable = false, unique = true)
    private String cpf;
    private String nome;
    private String telefone;
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ficha_id")
    private Ficha ficha;
}
