package pos.fiap.lanchonete.apipagamento.steps.inconsistencia;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.Assert.*;

public class RealizaPagamentoInconsistente {

    private String payload;
    private HttpResponse<String> response;

    @Given("o seguinte pagamento com metodo CHEQUE foi realizado:")
    public void o_seguinte_pagamento_com_metodo_cheque_foi_realizado(String body) throws IOException, InterruptedException {
        payload = body;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/pagamento"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Then("o código de resposta deve ser {int} para pagamento com cheque")
    public void o_codigo_de_resposta_deve_ser(int statusCode) {
        assertNotNull(response);
        assertEquals(statusCode, response.statusCode());
    }
}
