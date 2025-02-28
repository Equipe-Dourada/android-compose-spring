package com.stp.pacientes.controller;

import com.stp.pacientes.model.Endereco;
import com.stp.pacientes.service.EnderecoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/enderecos")
public class EnderecoController {
	private final EnderecoService enderecoService;

	@GetMapping
	public ResponseEntity<List<Endereco>> findAll() {
		List<Endereco> enderecos = enderecoService.findAll();
		return ResponseEntity.ok(enderecos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Endereco> findById(@PathVariable Long id) {
		Optional<Endereco> endereco = enderecoService.findById(id);
		return endereco.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@PostMapping
	public ResponseEntity<Endereco> create(@RequestBody Endereco endereco) {
		Endereco savedEndereco = enderecoService.save(endereco);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedEndereco);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Endereco> update(@PathVariable Long id, @RequestBody Endereco endereco) {
		if (enderecoService.findById(id).isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		endereco.setId(id);
		Endereco updatedEndereco = enderecoService.save(endereco);
		return ResponseEntity.ok(updatedEndereco);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (enderecoService.findById(id).isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		enderecoService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
