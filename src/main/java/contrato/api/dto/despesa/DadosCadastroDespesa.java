package contrato.api.dto.despesa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosCadastroDespesa(
        @NotNull
        LocalDate data,
        @NotNull
        Integer valor,
        @NotBlank
        String descricao,
        @NotNull
        Long idImovel
) {
}
