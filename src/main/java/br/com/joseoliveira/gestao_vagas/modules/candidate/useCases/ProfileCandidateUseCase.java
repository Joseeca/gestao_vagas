package br.com.joseoliveira.gestao_vagas.modules.candidate.useCases;

import br.com.joseoliveira.gestao_vagas.exceptions.UserNotFoundException;
import br.com.joseoliveira.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.joseoliveira.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute (UUID idCandidate) {
        var candidate = this.candidateRepository.findById(idCandidate)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });
        var candidateDTO = ProfileCandidateResponseDTO.builder()
                .description(candidate.getDescription())
                .id(candidate.getId())
                .username(candidate.getUsername())
                .email(candidate.getEmail())
                .name(candidate.getName())
                .build(); //cria a instância dessa classe
        return candidateDTO;
    }
}