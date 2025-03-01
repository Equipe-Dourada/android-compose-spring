package com.stp.pacientes.service;

import com.stp.pacientes.model.Paciente;
import com.stp.pacientes.repository.PacienteRepository;
import com.stp.pacientes.dto.PacienteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteService {
    private final PacienteRepository pacienteRepository;

    public List<PacienteDTO> listarTodos() {
        return pacienteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PacienteDTO> buscarPorId(String id) {
        return pacienteRepository.findById(id).map(this::toDTO);
    }

    public PacienteDTO salvar(PacienteDTO pacienteDTO) {
        Paciente paciente = toEntity(pacienteDTO);
        return toDTO(pacienteRepository.save(paciente));
    }

    public PacienteDTO atualizar(String id, PacienteDTO pacienteDTO) {
        Paciente paciente = toEntity(pacienteDTO);
        paciente.setCpf(id);
        return toDTO(pacienteRepository.save(paciente));
    }

    public void deletar(String id) { pacienteRepository.deleteById(id); }

    private PacienteDTO toDTO(Paciente paciente) {
        return new PacienteDTO(
                paciente.getCpf(),
                paciente.getNome(),
                paciente.getTelefone(),
                paciente.getEmail(),
                paciente.getEndereco(),
                paciente.getFicha()
        );
    }

    private Paciente toEntity(PacienteDTO pacienteDTO) {
        return new Paciente(pacienteDTO.getCpf(), pacienteDTO.getNome(), pacienteDTO.getTelefone(),
                pacienteDTO.getEmail(), pacienteDTO.getEndereco(), pacienteDTO.getFicha());
    }
}
