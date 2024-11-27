package pos.fiap.lanchonete.adapter.out.dynamo.entities;

import org.junit.jupiter.api.Test;
import pos.fiap.lanchonete.domain.enums.MetodoPagamentoEnum;
import pos.fiap.lanchonete.domain.enums.StatusPagamentoEnum;
import pos.fiap.lanchonete.domain.model.DadosPagamento;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoEntityTest {

    @Test
    void testConstructorDefault() {
        PagamentoEntity pagamentoEntity = new PagamentoEntity();

        assertNotNull(pagamentoEntity.getId());
        assertEquals(StatusPagamentoEnum.AGUARDANDO.name(), pagamentoEntity.getStatusPagamento());
        assertNotNull(pagamentoEntity.getDataCriacao());
        assertNull(pagamentoEntity.getDataAtualizacao());
    }

    @Test
    void testConstructorBuilder() {
        PagamentoEntity pagamentoEntity = PagamentoEntity.builder()
                .statusPagamento(StatusPagamentoEnum.AGUARDANDO)
                .idPedido("12345")
                .qrCode("QRCode123")
                .qrCodeId("QRCodeId123")
                .metodoPagamento(MetodoPagamentoEnum.PIX)
                .build();

        assertNotNull(pagamentoEntity.getId());
        assertEquals("12345", pagamentoEntity.getIdPedido());
        assertEquals("QRCode123", pagamentoEntity.getQrCode());
        assertEquals("QRCodeId123", pagamentoEntity.getQrCodeId());
        assertEquals(StatusPagamentoEnum.AGUARDANDO.name(), pagamentoEntity.getStatusPagamento());
        assertEquals(MetodoPagamentoEnum.PIX.name(), pagamentoEntity.getMetodoPagamento());
        assertNotNull(pagamentoEntity.getDataCriacao());
    }

    @Test
    void testAtualizarDadosEntity() {
        PagamentoEntity pagamentoEntity = new PagamentoEntity();
        DadosPagamento dadosPagamento = DadosPagamento.builder()
                .statusPagamento(StatusPagamentoEnum.AGUARDANDO)
                .numeroPedido("98765")
                .qrCode("QRCodeUpdated")
                .qrCodeId("QRCodeIdUpdated")
                .metodoPagamento(MetodoPagamentoEnum.PIX)
                .build();

        pagamentoEntity.atualizarDadosEntity(dadosPagamento);

        assertEquals(StatusPagamentoEnum.AGUARDANDO.name(), pagamentoEntity.getStatusPagamento());
        assertEquals("98765", pagamentoEntity.getIdPedido());
        assertEquals("QRCodeUpdated", pagamentoEntity.getQrCode());
        assertEquals("QRCodeIdUpdated", pagamentoEntity.getQrCodeId());
        assertEquals(MetodoPagamentoEnum.PIX.name(), pagamentoEntity.getMetodoPagamento());
        assertNotNull(pagamentoEntity.getDataAtualizacao());
    }
}