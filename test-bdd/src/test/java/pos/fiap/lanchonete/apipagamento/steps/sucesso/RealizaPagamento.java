package pos.fiap.lanchonete.apipagamento.steps.sucesso;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.Assert.*;

public class RealizaPagamento {

    private String payload;
    private HttpResponse<String> response;

    @Given("o seguinte pagamento foi realizado:")
    public void o_seguinte_pagamento_foi_realizado(String body) throws IOException, InterruptedException {
        payload = body;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/pagamento"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Then("o código de resposta deve ser {int} para o pagamento realizado")
    public void o_codigo_de_resposta_deve_ser(int statusCode) {
        assertNotNull(response);
        assertEquals(statusCode, response.statusCode());
    }

    @And("o corpo da resposta deve conter o statusPagamento {string}, metodoPagamento {string} e numeroPedido {string}")
    public void o_corpo_da_resposta_deve_conter_os_campos(String statusPagamento, String metodoPagamento, String numeroPedido) {
        JSONObject responseJson = new JSONObject(response.body());

        assertEquals(statusPagamento, responseJson.getString("statusPagamento"));
        assertEquals(metodoPagamento, responseJson.getString("metodoPagamento"));
        assertEquals(numeroPedido, responseJson.getString("numeroPedido"));
    }

    @And("o corpo da resposta deve conter o qrCode")
    public void o_corpo_da_resposta_deve_conter_o_qrCode() {
        JSONObject responseJson = new JSONObject(response.body());

        assertNotNull(responseJson.getString("qrCode"));
    }

    @And("o corpo da resposta deve conter a dataCriacao")
    public void o_corpo_da_resposta_deve_conter_a_dataCriacao() {
        JSONObject responseJson = new JSONObject(response.body());

        assertNotNull(responseJson.getString("dataCriacao"));
    }
}
