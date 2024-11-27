package pos.fiap.lanchonete.adapter.out.mercadopago.dto.pagamentoqrcode;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import pos.fiap.lanchonete.domain.enums.CategoriaEnum;
import pos.fiap.lanchonete.domain.enums.MetodoPagamentoEnum;
import pos.fiap.lanchonete.domain.enums.StatusPagamentoEnum;
import pos.fiap.lanchonete.domain.model.DadosPagamento;
import pos.fiap.lanchonete.domain.model.DadosProduto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

class PagamentoMPRequestDtoTest {

    @Test
    void testPagamentoMPRequestDto() {
        String title = "Pagamento de pedido";
        String description = "Pagamento referente ao pedido #12345";
        String externalReference = "ref-12345";
        BigDecimal totalAmount = new BigDecimal("100.00");
        PagamentoMPRequestDto.ItemPagamentoMPRequestDto item = PagamentoMPRequestDto.ItemPagamentoMPRequestDto.builder()
                .title("Item 1")
                .description("Descrição do item 1")
                .category("SOBREMESA")
                .unitPrice(new BigDecimal("50.00"))
                .quantity(2)
                .unitMeasure("UNIT")
                .totalAmount(new BigDecimal("100.00"))
                .build();
        PagamentoMPRequestDto dto = PagamentoMPRequestDto.builder()
                .title(title)
                .description(description)
                .externalReference(externalReference)
                .items(Arrays.asList(item))
                .notificationUrl("https://notificacao.url")
                .totalAmount(totalAmount)
                .build();

        assertNotNull(dto);
        assertEquals(title, dto.getTitle());
        assertEquals(description, dto.getDescription());
        assertEquals(externalReference, dto.getExternalReference());
        assertEquals(totalAmount, dto.getTotalAmount());
        assertNotNull(dto.getItems());
        assertEquals(1, dto.getItems().size());
        assertEquals(item, dto.getItems().get(0));
    }

    @Test
    void testBuildRequestEntity() {
        DadosProduto produto = DadosProduto.builder()
                .nome("Produto Exemplo")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(10.0)
                .descricao("Descrição do produto")
                .build();

        DadosPagamento dadosPagamento = DadosPagamento.builder()
                .numeroPedido("12345")
                .statusPagamento(StatusPagamentoEnum.AGUARDANDO)
                .qrCode("QRCode123")
                .qrCodeId("QRCodeId123")
                .metodoPagamento(MetodoPagamentoEnum.PIX)
                .itens(List.of(produto))
                .valorTotal(1.00)
                .build();
        HttpHeaders headers = new HttpHeaders();
        String notificationUrl = "https://notificacao.url";

        HttpEntity<PagamentoMPRequestDto> requestEntity = PagamentoMPRequestDto.buildRequestEntity(dadosPagamento, headers, notificationUrl);

        assertNotNull(requestEntity);
        assertNotNull(requestEntity.getBody());
        assertEquals(notificationUrl, requestEntity.getBody().getNotificationUrl());
        assertEquals(headers, requestEntity.getHeaders());
    }
}