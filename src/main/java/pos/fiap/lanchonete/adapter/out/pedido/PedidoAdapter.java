package pos.fiap.lanchonete.adapter.out.pedido;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pos.fiap.lanchonete.adapter.out.exception.HttpRequestException;
import pos.fiap.lanchonete.port.PedidoPort;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PedidoAdapter implements PedidoPort {

    private final WebClient.Builder webClientBuilder;

    @Value("${pedido.atualizacao.url}")
    private String atualizacaoPedidoUrl;

    @Override
    public void atualizarStatusPedido(String numeroPedido) {
        try {
            var url = atualizacaoPedidoUrl + numeroPedido;

            var body = Map.of("statusPedido", "EM_PREPARACAO");

            webClientBuilder.build()
                    .patch()
                    .uri(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .doOnTerminate(() -> log.info("Pedido atualizado com sucesso. Pedido: {}, Status: {}", numeroPedido, "EM_PREPARACAO"))
                    .subscribe();
        } catch (Exception e) {
            log.error("Erro ao atualizar o status do pedido. Pedido: {}, Status: {}", numeroPedido, "EM_PREPARACAO", e);
            throw new HttpRequestException("Erro ao atualizar o status do pedido", e);
        }
    }
}