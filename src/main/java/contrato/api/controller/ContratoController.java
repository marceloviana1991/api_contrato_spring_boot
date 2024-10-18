package contrato.api.controller;

import contrato.api.dto.contrato.DadosAtualizacaoContrato;
import contrato.api.dto.contrato.DadosCadastroContrato;
import contrato.api.dto.contrato.DadosDetalhamentoContrato;
import contrato.api.model.Contrato;
import contrato.api.model.Imovel;
import contrato.api.repository.ContratoRepository;
import contrato.api.repository.ImovelRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contratos")
public class ContratoController {

    @Autowired
    private ImovelRepository imovelRepository;

    @Autowired
    private ContratoRepository contratoRepository;

    @PostMapping
    @Transactional
    public DadosDetalhamentoContrato cadastrar(@RequestBody @Valid DadosCadastroContrato dadosCadastroContrato) {
        Imovel imovel = imovelRepository.getReferenceById(dadosCadastroContrato.idImovel());
        Contrato contrato = new Contrato(dadosCadastroContrato, imovel);
        contratoRepository.save(contrato);
        return new DadosDetalhamentoContrato(contrato);
    }

    @GetMapping
    public List<DadosDetalhamentoContrato> listar(@PageableDefault(sort = {"dataInicio", "dataFinalizacao"})
                                                      Pageable pageable) {
        Page<Contrato> contratoList = contratoRepository.contratosAtivos(pageable);
        return contratoList.stream().map(DadosDetalhamentoContrato::new).toList();
    }

    @GetMapping("/{id}")
    public DadosDetalhamentoContrato detalhar(@PathVariable Long id) {
        Contrato contrato = contratoRepository.getReferenceById(id);
        return new DadosDetalhamentoContrato(contrato);
    }

    @PutMapping
    @Transactional
    public DadosDetalhamentoContrato atualizar(@RequestBody @Valid DadosAtualizacaoContrato dadosAtualizacaoContrato) {
        Contrato contrato = contratoRepository.getReferenceById(dadosAtualizacaoContrato.id());
        contrato.atualizarDados(dadosAtualizacaoContrato);
        return new DadosDetalhamentoContrato(contrato);
    }
}
