package contrato.api.dto.contrato;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosCadastroContrato(
        @NotNull
        LocalDate dataInicio,
        @NotNull
        LocalDate dataFinalizacao,
        @NotNull
        Integer mensalidade,
        @NotNull
        Integer iptu,
        @NotNull
        Long idImovel
) {
}
