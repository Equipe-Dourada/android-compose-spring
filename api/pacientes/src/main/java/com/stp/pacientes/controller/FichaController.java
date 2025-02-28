package com.stp.pacientes.controller;

import com.stp.pacientes.model.Ficha;
import com.stp.pacientes.service.FichaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/fichas")
public class FichaController {

    private final FichaService service;

    @GetMapping
    public java.util.List<Ficha> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Ficha obter(@PathVariable Long id) {
        return service.obter(id);
    }

    @PostMapping
    public Ficha adicionar(@RequestBody Ficha ficha) {
        return service.salvar(ficha);
    }

    @PutMapping("/{id}")
    public Ficha atualizar(@PathVariable Long id, @RequestBody Ficha ficha) {
        return service.salvar(id, ficha);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }
}
