package com.stp.pacientes.service;

import com.stp.pacientes.model.Ficha;
import com.stp.pacientes.repository.FichaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FichaService {

    private final FichaRepository repository;

    public java.util.List<Ficha> listar() {
        return repository.findAll();
    }

    public Ficha obter(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Ficha salvar(Ficha ficha) {
        return repository.save(ficha);
    }

    public Ficha salvar(Long id, Ficha ficha) {
        ficha.setId(id);
        return repository.save(ficha);
    }

    public void remover(Long id) {
        repository.deleteById(id);
    }
}
