package contrato.api.model;

import contrato.api.dto.contrato.DadosAtualizacaoContrato;
import contrato.api.dto.contrato.DadosCadastroContrato;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "contratos")
@Entity(name = "Contrato")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dataInicio;
    private LocalDate dataFinalizacao;
    private Integer mensalidade;
    private Integer iptu;
    @ManyToOne
    @JoinColumn(name = "id_imovel")
    private Imovel imovel;

    public Contrato(DadosCadastroContrato dadosCadastroContrato, Imovel imovel) {
        this.dataInicio = dadosCadastroContrato.dataInicio();
        this.dataFinalizacao = dadosCadastroContrato.dataFinalizacao();
        this.mensalidade = dadosCadastroContrato.mensalidade();
        this.iptu = dadosCadastroContrato.iptu();
        this.imovel = imovel;
    }

    public void atualizarDados(DadosAtualizacaoContrato dadosAtualizacaoContrato) {
        this.dataInicio = (dadosAtualizacaoContrato.dataInico() != null) ? dadosAtualizacaoContrato.dataInico():
                this.dataInicio;
        this.dataFinalizacao = (dadosAtualizacaoContrato.dataFinalizacao() != null) ?
                dadosAtualizacaoContrato.dataFinalizacao(): this.getDataFinalizacao();
        this.mensalidade = (dadosAtualizacaoContrato.mensalidade() != null) ? dadosAtualizacaoContrato.mensalidade():
                this.getMensalidade();
        this.iptu = (dadosAtualizacaoContrato.iptu() != null) ? dadosAtualizacaoContrato.iptu(): this.getIptu();
    }
}
