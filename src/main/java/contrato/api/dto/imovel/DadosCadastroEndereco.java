package contrato.api.dto.imovel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosCadastroEndereco(
        String bairro,
        String localidade,
        String estado,
        String logradouro
) {
}
