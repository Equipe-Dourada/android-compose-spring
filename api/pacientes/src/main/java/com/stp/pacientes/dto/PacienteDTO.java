package com.stp.pacientes.dto;

import com.stp.pacientes.model.Endereco;
import com.stp.pacientes.model.Ficha;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDTO {
    private String cpf;
    private String nome;
    private String telefone;
    private String email;
    private Endereco endereco;
    private Ficha ficha;
}
