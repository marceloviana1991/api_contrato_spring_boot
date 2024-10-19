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
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity<DadosDetalhamentoContrato> cadastrar(
            @RequestBody @Valid DadosCadastroContrato dadosCadastroContrato, UriComponentsBuilder uriComponentsBuilder) {
        Imovel imovel = imovelRepository.getReferenceById(dadosCadastroContrato.idImovel());
        Contrato contrato = new Contrato(dadosCadastroContrato, imovel);
        contratoRepository.save(contrato);
        var uri = uriComponentsBuilder.path("/contratos/{id}").buildAndExpand(contrato.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoContrato(contrato));
    }

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoContrato>> listar(@PageableDefault(sort = {"dataInicio", "dataFinalizacao"})
                                                      Pageable pageable) {
        Page<Contrato> contratoList = contratoRepository.contratosAtivos(pageable);
        return ResponseEntity.ok(contratoList.stream().map(DadosDetalhamentoContrato::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoContrato> detalhar(@PathVariable Long id) {
        Contrato contrato = contratoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoContrato(contrato));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoContrato> atualizar(@RequestBody @Valid DadosAtualizacaoContrato dadosAtualizacaoContrato) {
        Contrato contrato = contratoRepository.getReferenceById(dadosAtualizacaoContrato.id());
        contrato.atualizarDados(dadosAtualizacaoContrato);
        return ResponseEntity.ok(new DadosDetalhamentoContrato(contrato));
    }
}
