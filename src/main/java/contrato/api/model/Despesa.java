package contrato.api.model;

import contrato.api.dto.despesa.DadosAtualizarDespesa;
import contrato.api.dto.despesa.DadosCadastroDespesa;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "despesas")
@Entity(name = "Despesa")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Despesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data;
    private Integer valor;
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "id_imovel")
    private Imovel imovel;

    public Despesa(DadosCadastroDespesa dadosCadastroDespesa, Imovel imovel) {
        this.data = dadosCadastroDespesa.data();
        this.valor = dadosCadastroDespesa.valor();
        this.descricao = dadosCadastroDespesa.descricao();
        this.imovel = imovel;
    }

    public void atualizarDados(DadosAtualizarDespesa dadosAtualizarDespesa) {
        this.data = (dadosAtualizarDespesa.data() != null) ? dadosAtualizarDespesa.data(): this.data;
        this.valor = (dadosAtualizarDespesa.valor() != null) ? dadosAtualizarDespesa.valor(): this.valor;
        this.descricao = (dadosAtualizarDespesa.descricao() != null) ? dadosAtualizarDespesa.descricao(): this.descricao;
    }
}
