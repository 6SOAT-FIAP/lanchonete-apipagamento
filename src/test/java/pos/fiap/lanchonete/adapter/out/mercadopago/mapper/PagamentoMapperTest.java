package pos.fiap.lanchonete.adapter.out.mercadopago.mapper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import pos.fiap.lanchonete.domain.model.DadosPagamento;
import pos.fiap.lanchonete.domain.model.DadosProduto;
import pos.fiap.lanchonete.domain.enums.CategoriaEnum;
import pos.fiap.lanchonete.adapter.out.mercadopago.dto.pagamentoqrcode.PagamentoMPRequestDto;

public class PagamentoMapperTest {

    @Test
    public void testBuildPaymentRequest() {
        DadosProduto produto1 = DadosProduto.builder()
                .nome("Burger")
                .categoria(CategoriaEnum.LANCHE)
                .preco(10.0)
                .descricao("Delicious beef burger")
                .build();

        DadosProduto produto2 = DadosProduto.builder()
                .nome("Soda")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(5.0)
                .descricao("Refreshing soda")
                .build();

        DadosPagamento dadosPagamento = DadosPagamento.builder()
                .numeroPedido("12345")
                .dataCriacao(LocalDateTime.of(2024,11,26,22,59,52))
                .itens(List.of(produto1, produto2))
                .valorTotal(15.0)
                .build();

        PagamentoMPRequestDto requestDto = PagamentoMapper.buildPaymentRequest(dadosPagamento);

        assertEquals("Payment for order 12345", requestDto.getTitle());
        assertEquals("Payment for order 12345 placed 2024-11-26T22:59:52", requestDto.getDescription());
        assertEquals("12345", requestDto.getExternalReference());

        assertNotNull(requestDto.getItems());
        assertEquals(2, requestDto.getItems().size());

        PagamentoMPRequestDto.ItemPagamentoMPRequestDto item1 = requestDto.getItems().get(0);
        assertEquals("Burger", item1.getTitle());
        assertEquals("Delicious beef burger", item1.getDescription());
        assertEquals("LANCHE", item1.getCategory());
        assertEquals(BigDecimal.valueOf(10.0), item1.getUnitPrice());
        assertEquals(BigDecimal.valueOf(10.0), item1.getTotalAmount());

        PagamentoMPRequestDto.ItemPagamentoMPRequestDto item2 = requestDto.getItems().get(1);
        assertEquals("Soda", item2.getTitle());
        assertEquals("Refreshing soda", item2.getDescription());
        assertEquals("BEBIDA", item2.getCategory());
        assertEquals(BigDecimal.valueOf(5.0), item2.getUnitPrice());
        assertEquals(BigDecimal.valueOf(5.0), item2.getTotalAmount());
    }
}