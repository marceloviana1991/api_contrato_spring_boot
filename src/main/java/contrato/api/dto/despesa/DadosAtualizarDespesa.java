package contrato.api.dto.despesa;

import lombok.NonNull;

import java.time.LocalDate;

public record DadosAtualizarDespesa(
        @NonNull
        Long id,
        LocalDate data,
        Integer valor,
        String descricao,
        Long idImovel
) {
}
