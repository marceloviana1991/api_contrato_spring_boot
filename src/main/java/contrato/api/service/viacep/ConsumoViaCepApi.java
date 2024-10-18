package contrato.api.service.viacep;

import com.fasterxml.jackson.databind.ObjectMapper;
import contrato.api.dto.imovel.DadosCadastroEndereco;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoViaCepApi {

    public static DadosCadastroEndereco obterDados(String cep) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://viacep.com.br/ws/" + cep + "/json/"))
                .build();
        ObjectMapper mapper = new ObjectMapper();
        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return mapper.readValue(response.body(), DadosCadastroEndereco.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
