package contrato.api.dto.imovel;

import contrato.api.model.Imovel;
import contrato.api.model.TipoImovel;

public record DadosDetalhamentoImovel(
        Long id,
        TipoImovel tipoImovel,
        String bairro,
        String rua,
        String numero,
        String cidade,
        String estado,
        String cep
) {

    public DadosDetalhamentoImovel(Imovel imovel) {
        this(
                imovel.getId(),
                imovel.getTipoImovel(),
                imovel.getBairro(),
                imovel.getRua(),
                imovel.getNumero(),
                imovel.getCidade(),
                imovel.getEstado(),
                imovel.getCep()
        );
    }
}
