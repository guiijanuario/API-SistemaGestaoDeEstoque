package br.com.catalisa.GestaoDeEstoque.service;

import br.com.catalisa.GestaoDeEstoque.model.CepModel;
import br.com.catalisa.GestaoDeEstoque.repository.CepRepository;
import br.com.catalisa.GestaoDeEstoque.validations.CepValidations;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class CepService {
    private static final String viaCepUrl = "https://viacep.com.br/ws/";
    private static final Gson gson = new Gson();

    @Autowired
    private CepRepository cepRepository;

    @Autowired
    private CepValidations cepValidations;

    public CepModel findCep(String cepString) {
       cepValidations.validaCep(cepString);
        try {
            HttpClient httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.of(1, MINUTES))
                    .build();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(viaCepUrl+cepString+"/json"))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            CepModel cepModel = gson.fromJson(httpResponse.body(), CepModel.class);

            return cepRepository.save(cepModel);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
