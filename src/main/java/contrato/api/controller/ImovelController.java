package contrato.api.controller;

import contrato.api.dto.imovel.DadosCadastroEndereco;
import contrato.api.dto.imovel.DadosCadastroImovel;

import contrato.api.dto.imovel.DadosDetalhamentoImovel;
import contrato.api.model.Imovel;
import contrato.api.repository.ImovelRepository;
import contrato.api.service.consumo.ConsumoViaCepApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/imoveis")
public class ImovelController {

    @Autowired
    private ImovelRepository imovelRepository;

    @PostMapping
    @Transactional
    public DadosDetalhamentoImovel cadastrar(@RequestBody DadosCadastroImovel dadosCadastroImovel) {
        DadosCadastroEndereco dadosCadastroEndereco = ConsumoViaCepApi.obterDados(dadosCadastroImovel.cep());
        Imovel imovel = new Imovel(dadosCadastroImovel, dadosCadastroEndereco);
        imovelRepository.save(imovel);
        return new DadosDetalhamentoImovel(imovel);
    }
}
