package contrato.api.model;

import contrato.api.dto.imovel.DadosCadastroEndereco;
import contrato.api.dto.imovel.DadosCadastroImovel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "imoveis")
@Entity(name = "Imovel")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Imovel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TipoImovel tipoImovel;
    private String bairro;
    private String rua;
    private String numero;
    private String cidade;
    private String estado;
    private String cep;

    public Imovel(DadosCadastroImovel dadosCadastroImovel, DadosCadastroEndereco dadosCadastroEndereco) {
        this.tipoImovel = dadosCadastroImovel.tipoImovel();
        this.bairro = dadosCadastroEndereco.bairro();
        this.rua = dadosCadastroEndereco.logradouro();
        this.numero = dadosCadastroImovel.numero();
        this.cidade = dadosCadastroEndereco.localidade();
        this.estado = dadosCadastroEndereco.estado();
        this.cep = dadosCadastroImovel.cep();
    }

}
