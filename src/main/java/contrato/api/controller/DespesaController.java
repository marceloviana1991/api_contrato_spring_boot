package contrato.api.controller;

import contrato.api.dto.despesa.DadosAtualizarDespesa;
import contrato.api.dto.despesa.DadosCadastroDespesa;
import contrato.api.dto.despesa.DadosDetalhamentoDespesa;
import contrato.api.model.Despesa;
import contrato.api.model.Imovel;
import contrato.api.repository.DespesaRepository;
import contrato.api.repository.ImovelRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    private ImovelRepository imovelRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoDespesa> cadastrar(
            @RequestBody @Valid DadosCadastroDespesa dadosCadastroDespesa, UriComponentsBuilder uriComponentsBuilder) {
        Imovel imovel = imovelRepository.getReferenceById(dadosCadastroDespesa.idImovel());
        Despesa despesa = new Despesa(dadosCadastroDespesa, imovel);
        despesaRepository.save(despesa);
        var uri = uriComponentsBuilder.path("/despesas/{id}").buildAndExpand(despesa.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoDespesa(despesa));
    }

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoDespesa>> listar(Pageable pageable) {
        Page<Despesa> despesaList = despesaRepository.despesasAtivas(pageable);
        return ResponseEntity.ok(despesaList.stream().map(DadosDetalhamentoDespesa::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoDespesa> detalhar(@PathVariable Long id) {
        Despesa despesa = despesaRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoDespesa(despesa));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoDespesa> atualizar(@RequestBody @Valid DadosAtualizarDespesa dadosAtualizarDespesa) {
        Despesa despesa = despesaRepository.getReferenceById(dadosAtualizarDespesa.id());
        despesa.atualizarDados(dadosAtualizarDespesa);
        return ResponseEntity.ok(new DadosDetalhamentoDespesa(despesa));
    }
}


