package com.stp.pacientes.controller;

import com.stp.pacientes.model.Paciente;
import com.stp.pacientes.service.PacienteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService service;

    @GetMapping
    public List<Paciente> listar() {
        return service.listar();
    }

    @GetMapping("/{cpf}")
    public Paciente obter(@PathVariable String cpf) {
        return service.obter(cpf);
    }

    @PostMapping
    public Paciente adicionar(@RequestBody Paciente paciente) {
        return service.salvar(paciente);
    }

    @PutMapping("/{cpf}")
    public Paciente atualizar(@PathVariable String cpf, @RequestBody Paciente paciente) {
        return service.salvar(cpf, paciente);
    }

    @DeleteMapping("/{cpf}")
    public void remover(@PathVariable String cpf) {
        service.remover(cpf);
    }
}
