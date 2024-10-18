package contrato.api.dto.contrato;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosAtualizacaoContrato(
        @NotNull
        Long id,
        LocalDate dataInico,
        LocalDate dataFinalizacao,
        Integer mensalidade,
        Integer iptu
) {
}
