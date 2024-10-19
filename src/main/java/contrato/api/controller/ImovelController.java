package contrato.api.controller;

import contrato.api.dto.imovel.DadosAtualizacaoImovel;
import contrato.api.dto.imovel.DadosCadastroEndereco;
import contrato.api.dto.imovel.DadosCadastroImovel;

import contrato.api.dto.imovel.DadosDetalhamentoImovel;
import contrato.api.model.Imovel;
import contrato.api.repository.ImovelRepository;
import contrato.api.service.viacep.ConsumoViaCepApi;
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
@RequestMapping("/imoveis")
public class ImovelController {

    @Autowired
    private ImovelRepository imovelRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoImovel> cadastrar(
            @RequestBody @Valid DadosCadastroImovel dadosCadastroImovel, UriComponentsBuilder uriComponentsBuilder) {
        DadosCadastroEndereco dadosCadastroEndereco = ConsumoViaCepApi.obterDados(dadosCadastroImovel.cep());
        Imovel imovel = new Imovel(dadosCadastroImovel, dadosCadastroEndereco);
        imovelRepository.save(imovel);
        var uri = uriComponentsBuilder.path("/imoveis/{id}").buildAndExpand(imovel.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoImovel(imovel));
    }

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoImovel>> listar(
            @PageableDefault(sort = {"bairro", "rua", "numero"}) Pageable pageable) {
        Page<Imovel> imovelList = imovelRepository.findAllByAtivoTrue(pageable);
        return ResponseEntity.ok(imovelList.stream().map(DadosDetalhamentoImovel::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoImovel> detalhar(@PathVariable Long id) {
        Imovel imovel = imovelRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoImovel(imovel));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoImovel> atualizar(@RequestBody @Valid DadosAtualizacaoImovel dadosAtualizacaoImovel) {
        Imovel imovel = imovelRepository.getReferenceById(dadosAtualizacaoImovel.id());
        imovel.atualizarDados(dadosAtualizacaoImovel);
        return ResponseEntity.ok(new DadosDetalhamentoImovel(imovel));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluir (@PathVariable Long id) {
        Imovel imovel = imovelRepository.getReferenceById(id);
        imovel.desativar();
        return ResponseEntity.noContent().build();
    }
}
