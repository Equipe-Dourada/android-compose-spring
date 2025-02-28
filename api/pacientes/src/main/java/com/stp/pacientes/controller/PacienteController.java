package com.stp.pacientes.controller;

import com.stp.pacientes.dto.PacienteDTO;
import com.stp.pacientes.service.PacienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService service;

    @GetMapping
    public List<PacienteDTO> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<PacienteDTO> obter(@PathVariable String cpf) {
        return service.buscarPorId(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PacienteDTO adicionar(@RequestBody PacienteDTO pacienteDTO) {
        return service.salvar(pacienteDTO);
    }

    @PutMapping("/{cpf}")
    public PacienteDTO atualizar(@PathVariable String cpf, @RequestBody PacienteDTO pacienteDTO) {
        return service.atualizar(cpf, pacienteDTO);
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> remover(@PathVariable String cpf) {
        service.deletar(cpf);
        return ResponseEntity.noContent().build();
    }
}
