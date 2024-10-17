package contrato.api.dto.imovel;

import contrato.api.model.TipoImovel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroImovel(
        @NotNull
        TipoImovel tipoImovel,
        @NotBlank @Pattern(regexp = "\\d{8}")
        String cep,
        @NotBlank
        String numero
) {
}
