package br.com.joseoliveira.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobDTO {

    @Schema(example = "Vaga para pessoa desenvolvedora júnior", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
    @Schema(example = "GYMPass, Plano de saúde, plano odontológico", requiredMode = Schema.RequiredMode.REQUIRED)
    private String benefits;
    @Schema(example = "JÚNIOR", requiredMode = Schema.RequiredMode.REQUIRED)
    private String level;
}