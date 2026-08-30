package br.com.joseoliveira.gestao_vagas.modules.candidate.useCases;

import br.com.joseoliveira.gestao_vagas.exceptions.JobNotFoundException;
import br.com.joseoliveira.gestao_vagas.exceptions.UserNotFoundException;
import br.com.joseoliveira.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.joseoliveira.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.joseoliveira.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.joseoliveira.gestao_vagas.modules.company.entities.JobEntity;
import br.com.joseoliveira.gestao_vagas.modules.company.repositories.JobRepository;
import br.com.joseoliveira.gestao_vagas.modules.repository.ApplyJobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should not be able to apply job with candidate not found")
    public void should_not_be_able_to_apply_job_with_candidate_not_found() {
        try {
            applyJobCandidateUseCase.execute(null, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    public void should_be_not_able_to_apply_job_with_job_not_found() {
        var idCandidate = UUID.randomUUID();
        var candidate = new CandidateEntity();
        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

        try {
            applyJobCandidateUseCase.execute(idCandidate, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    public void should_be_able_to_create_anew_apply_job() {
        var idCandidate = UUID.randomUUID();
        var idJob = UUID.randomUUID();

        var applyJob = ApplyJobEntity.builder().candidateId(idCandidate).jobId(idJob)
                .id(UUID.randomUUID())
                .build();

        var applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID()).build();

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));

// Passamos o 'applyJob' no return, pois ele já tem o ID preenchido pela linha 65
        when(applyJobRepository.save(any(ApplyJobEntity.class))).thenReturn(applyJob);

        var result = applyJobCandidateUseCase.execute(idCandidate, idJob);

        assertThat(result).hasFieldOrProperty("id");
        assertThat(result.getId()).isNotNull();
    }
}