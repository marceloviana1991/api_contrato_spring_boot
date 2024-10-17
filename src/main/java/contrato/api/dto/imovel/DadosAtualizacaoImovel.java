package contrato.api.dto.imovel;

import contrato.api.model.TipoImovel;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoImovel(
        @NotNull
        Long id,
        TipoImovel tipoImovel,
        String bairro,
        String rua,
        String numero,
        String cidade,
        String estado,
        String cep
) {
}
