package contrato.api.dto.despesa;

import contrato.api.model.Despesa;

import java.time.LocalDate;

public record DadosDetalhamentoDespesa(
        Long id,
        LocalDate data,
        Integer valor,
        String descricao,
        Long idImovel
) {
    public DadosDetalhamentoDespesa(Despesa despesa) {
        this(
                despesa.getId(),
                despesa.getData(),
                despesa.getValor(),
                despesa.getDescricao(),
                despesa.getImovel().getId()
        );
    }
}
