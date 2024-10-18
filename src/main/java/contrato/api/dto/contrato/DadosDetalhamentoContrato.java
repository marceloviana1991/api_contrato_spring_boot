package contrato.api.dto.contrato;

import contrato.api.model.Contrato;

import java.time.LocalDate;

public record DadosDetalhamentoContrato(
        Long id,
        LocalDate dataInico,
        LocalDate dataFinalizacao,
        Integer mensalidade,
        Integer iptu,
        Long idImovel
) {
    public DadosDetalhamentoContrato(Contrato contrato){
        this(
                contrato.getId(),
                contrato.getDataInicio(),
                contrato.getDataFinalizacao(),
                contrato.getMensalidade(),
                contrato.getIptu(),
                contrato.getImovel().getId()
        );
    }
}
