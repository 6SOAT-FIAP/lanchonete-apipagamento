package pos.fiap.lanchonete.adapter.out.pedido;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import pos.fiap.lanchonete.adapter.out.exception.HttpRequestException;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PedidoAdapterTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private PedidoAdapter pedidoAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(webClientBuilder.build()).thenReturn(webClient);
        pedidoAdapter = new PedidoAdapter(webClientBuilder, "http://mock-pedido/");
    }

    @Test
    void deveAtualizarStatusPedidoComSucesso() {
        String numeroPedido = "12345";
        String expectedUrl = "http://mock-pedido/12345";

        when(webClient.patch()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(expectedUrl)).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.header(eq("Content-Type"), eq("application/json"))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        pedidoAdapter.atualizarStatusPedido(numeroPedido);

        verify(webClient).patch();
        verify(requestBodyUriSpec).uri(expectedUrl);
        verify(requestBodyUriSpec).header(eq("Content-Type"), eq("application/json"));
        verify(requestBodyUriSpec).bodyValue(Map.of("statusPedido", "EM_PREPARACAO"));
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToMono(Void.class);
    }

    @Test
    void deveLancarHttpRequestExceptionQuandoOcorreErro() {
        String numeroPedido = "12345";
        String expectedUrl = "http://mock-pedido/12345";

        when(webClient.patch()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(expectedUrl)).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.header(anyString(), anyString())).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenThrow(new RuntimeException("Erro de conexão"));

        assertThrows(HttpRequestException.class, () -> pedidoAdapter.atualizarStatusPedido(numeroPedido));

        verify(webClient).patch();
        verify(requestBodyUriSpec).uri(expectedUrl);
        verify(requestBodyUriSpec).header(eq("Content-Type"), eq("application/json"));
        verify(requestBodyUriSpec).bodyValue(Map.of("statusPedido", "EM_PREPARACAO"));
        verify(requestHeadersSpec).retrieve();
    }
}