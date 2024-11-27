package pos.fiap.lanchonete.adapter.out.dynamo.entities.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pos.fiap.lanchonete.adapter.out.dynamo.entities.PagamentoEntity;
import pos.fiap.lanchonete.domain.enums.MetodoPagamentoEnum;
import pos.fiap.lanchonete.domain.enums.StatusPagamentoEnum;
import pos.fiap.lanchonete.domain.model.DadosPagamento;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoEntityMapperTest {

    private PagamentoEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(PagamentoEntityMapper.class);
    }

    @Test
    void testToEntity() {
        DadosPagamento dadosPagamento = DadosPagamento.builder()
                .id("123")
                .statusPagamento(StatusPagamentoEnum.APROVADO)
                .metodoPagamento(MetodoPagamentoEnum.PIX)
                .qrCode("qrcode123")
                .qrCodeId("qrcodeId123")
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .numeroPedido("456")
                .valorTotal(100.50)
                .build();

        PagamentoEntity entity = mapper.toEntity(dadosPagamento);

        assertEquals(dadosPagamento.getNumeroPedido(), entity.getIdPedido());
        assertEquals(dadosPagamento.getQrCode(), entity.getQrCode());
        assertEquals(dadosPagamento.getQrCodeId(), entity.getQrCodeId());
        assertEquals(dadosPagamento.getStatusPagamento().name(), entity.getStatusPagamento());
        assertEquals(dadosPagamento.getMetodoPagamento().name(), entity.getMetodoPagamento());
        assertNotNull(entity.getId());
    }

    @Test
    void testToDadosPagamento() {
        PagamentoEntity pagamentoEntity = PagamentoEntity.builder()
                .statusPagamento(StatusPagamentoEnum.RECUSADO)
                .idPedido("789")
                .qrCode("qrcode789")
                .qrCodeId("qrcodeId789")
                .metodoPagamento(MetodoPagamentoEnum.PIX)
                .build();

        DadosPagamento dadosPagamento = mapper.toDadosPagamento(pagamentoEntity);

        assertEquals(pagamentoEntity.getIdPedido(), dadosPagamento.getNumeroPedido());
        assertEquals(pagamentoEntity.getQrCode(), dadosPagamento.getQrCode());
        assertEquals(pagamentoEntity.getQrCodeId(), dadosPagamento.getQrCodeId());
        assertEquals(StatusPagamentoEnum.RECUSADO, dadosPagamento.getStatusPagamento());
        assertEquals(MetodoPagamentoEnum.PIX, dadosPagamento.getMetodoPagamento());
        assertNotNull(dadosPagamento.getId());
    }
}