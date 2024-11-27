package pos.fiap.lanchonete.adapter.out.mercadopago;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pos.fiap.lanchonete.adapter.out.exception.HttpRequestException;
import pos.fiap.lanchonete.adapter.out.exception.RecursoNaoEncontradoException;
import pos.fiap.lanchonete.adapter.out.mercadopago.dto.merchantorder.MerchantOrderResponseDto;
import pos.fiap.lanchonete.adapter.out.mercadopago.dto.pagamentoqrcode.PagamentoMPResponseDto;
import pos.fiap.lanchonete.domain.model.DadosPagamento;
import pos.fiap.lanchonete.port.MercadoPagoPort;

import static java.util.Objects.isNull;
import static pos.fiap.lanchonete.adapter.out.mercadopago.dto.pagamentoqrcode.PagamentoMPRequestDto.buildRequestEntity;

@Slf4j
@Component
@RequiredArgsConstructor
public class MercadoPagoAdapter implements MercadoPagoPort {

    @Value("${mercado.pago.endpoint.payment}")
    private String urlPayment;

    @Value("${mercado.pago.endpoint.qr.code}")
    private String urlQrCode;

    @Value("${mercado.livre.endpoint.merchant.orders}")
    private String urlMerchantOrders;

    @Value("${mercado.pago.access_token}")
    private String accessToken;

    @Value("${mercado.pago.notification.url}")
    private String notificationUrl;

    private final RestTemplate restTemplate;

    @Override
    public PagamentoMPResponseDto gerarPagamentoQRCode(DadosPagamento dadosPagamento) {
        try {
            var headers = buildHeaders();
            var requestEntity = buildRequestEntity(dadosPagamento, headers, notificationUrl);
            log.info("Request generate QR Code mercado pago: {}", requestEntity);

            var response = restTemplate.exchange(urlQrCode, HttpMethod.POST, requestEntity, PagamentoMPResponseDto.class);

            if (isNull(response.getBody())) {
                throw new RecursoNaoEncontradoException("Erro ao tentar gerar o QR Code");
            }

            log.info("Response generate QR Code mercado pago: {}", response.getBody());
            return response.getBody();
        } catch (Exception e) {

            if (e instanceof RecursoNaoEncontradoException) {
                throw e;
            }

            log.error("Ocorreu algum erro na geração do QR code. ", e);
            throw new HttpRequestException("Ocorreu algum erro na geração do QR code. ", e);
        }
    }

    @Override
    public String obterNumeroPedido(String merchantOrderId) {
        log.info("Request obter numero pedido mercado livre: {}", merchantOrderId);

        var headers = buildHeaders();
        var url = urlMerchantOrders.replace("{merchant_order_id}", merchantOrderId);
        var response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), MerchantOrderResponseDto.class);

        log.info("Response numero pedido mercado livre: {}", response.getBody());

        if (isNull(response.getBody()) || isNull(response.getBody().getExternalReference())) {
            throw new RecursoNaoEncontradoException("Erro ao tentar obter o numero do pedido");
        }

        return response.getBody().getExternalReference();
    }

    private HttpHeaders buildHeaders() {
        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");

        return headers;
    }
}