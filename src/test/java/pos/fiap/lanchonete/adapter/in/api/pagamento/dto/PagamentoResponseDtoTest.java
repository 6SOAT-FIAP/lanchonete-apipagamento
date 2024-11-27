package pos.fiap.lanchonete.adapter.in.api.pagamento.dto;

import org.junit.jupiter.api.Test;
import pos.fiap.lanchonete.domain.enums.MetodoPagamentoEnum;
import pos.fiap.lanchonete.domain.enums.StatusPagamentoEnum;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoResponseDtoTest {

    @Test
    void testPagamentoResponseDtoBuilder() {

        PagamentoResponseDto pagamentoResponse = PagamentoResponseDto.builder()
                .statusPagamento(StatusPagamentoEnum.APROVADO)
                .metodoPagamento(MetodoPagamentoEnum.PIX)
                .numeroPedido("12345")
                .qrCode("QR12345")
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();

        assertNotNull(pagamentoResponse);
        assertEquals(StatusPagamentoEnum.APROVADO, pagamentoResponse.getStatusPagamento());
        assertEquals(MetodoPagamentoEnum.PIX, pagamentoResponse.getMetodoPagamento());
        assertEquals("12345", pagamentoResponse.getNumeroPedido());
        assertEquals("QR12345", pagamentoResponse.getQrCode());
        assertNotNull(pagamentoResponse.getDataCriacao());
        assertNotNull(pagamentoResponse.getDataAtualizacao());
    }
}