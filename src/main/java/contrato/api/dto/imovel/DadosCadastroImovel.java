package contrato.api.dto.imovel;

import contrato.api.model.TipoImovel;

public record DadosCadastroImovel(
        TipoImovel tipoImovel,
        String cep,
        String numero
) {
}
