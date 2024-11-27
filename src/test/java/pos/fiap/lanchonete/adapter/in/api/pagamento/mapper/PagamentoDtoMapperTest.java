package pos.fiap.lanchonete.adapter.in.api.pagamento.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pos.fiap.lanchonete.adapter.in.api.pagamento.dto.PagamentoRequestDto;
import pos.fiap.lanchonete.adapter.in.api.pagamento.dto.PagamentoResponseDto;
import pos.fiap.lanchonete.domain.enums.CategoriaEnum;
import pos.fiap.lanchonete.domain.model.DadosPagamento;
import pos.fiap.lanchonete.domain.enums.MetodoPagamentoEnum;
import pos.fiap.lanchonete.domain.enums.StatusPagamentoEnum;
import pos.fiap.lanchonete.adapter.out.mercadopago.dto.pagamentoqrcode.PagamentoMPResponseDto;
import pos.fiap.lanchonete.domain.model.DadosProduto;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoDtoMapperTest {

    private PagamentoDtoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(PagamentoDtoMapper.class);
    }

    @Test
    void testToDadosPagamentoFromPagamentoRequestDto() {
        PagamentoRequestDto produtoRequestDto = PagamentoRequestDto.builder()
                .valorTotal(100.0)
                .numeroPedido("123")
                .metodoPagamento(MetodoPagamentoEnum.PIX)
                .build();

        DadosPagamento dadosPagamento = mapper.toDadosPagamentoFromPagamentoRequestDto(produtoRequestDto);

        assertNotNull(dadosPagamento);
        assertEquals("123", dadosPagamento.getNumeroPedido());
        assertEquals(100.0, dadosPagamento.getValorTotal());
        assertEquals(MetodoPagamentoEnum.PIX, dadosPagamento.getMetodoPagamento());
        assertNull(dadosPagamento.getStatusPagamento());
        assertNull(dadosPagamento.getQrCode());
    }

    @Test
    void testToPagamentoResponseDtoFromDadosPagamento() {
        DadosPagamento dadosPagamento = DadosPagamento.builder()
                .id("1")
                .statusPagamento(StatusPagamentoEnum.RECUSADO)
                .metodoPagamento(MetodoPagamentoEnum.PIX)
                .numeroPedido("456")
                .qrCode("QR123")
                .qrCodeId("QR123ID")
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .itens(Collections.emptyList())
                .valorTotal(150.0)
                .build();

        PagamentoResponseDto pagamentoResponseDto = mapper.toPagamentoResponseDtoFromDadosPagamento(dadosPagamento);

        assertNotNull(pagamentoResponseDto);
        assertEquals(StatusPagamentoEnum.RECUSADO, pagamentoResponseDto.getStatusPagamento());
        assertEquals(MetodoPagamentoEnum.PIX, pagamentoResponseDto.getMetodoPagamento());
        assertEquals("456", pagamentoResponseDto.getNumeroPedido());
        assertEquals("QR123", pagamentoResponseDto.getQrCode());
        assertNotNull(pagamentoResponseDto.getDataCriacao());
        assertNotNull(pagamentoResponseDto.getDataAtualizacao());
    }

    @Test
    void testCompletarPagamentoComQrCode() {
        DadosPagamento dadosPagamento = DadosPagamento.builder()
                .numeroPedido("789")
                .valorTotal(200.0)
                .statusPagamento(StatusPagamentoEnum.AGUARDANDO)
                .metodoPagamento(MetodoPagamentoEnum.PIX)
                .build();

        PagamentoMPResponseDto pagamentoMPResponseDto = new PagamentoMPResponseDto();
        pagamentoMPResponseDto.setQrData("QR123456");
        pagamentoMPResponseDto.setInStoreOrderId("QR123456ID");

        dadosPagamento.completarPagamentoComQrCode(pagamentoMPResponseDto);

        assertEquals("QR123456", dadosPagamento.getQrCode());
        assertEquals("QR123456ID", dadosPagamento.getQrCodeId());
    }

    @Test
    void testToDadosPagamentoFromPagamentoRequestDto_WithProdutos() {
        PagamentoRequestDto pagamentoRequestDto = PagamentoRequestDto.builder()
                .valorTotal(100.0)
                .numeroPedido("123")
                .metodoPagamento(MetodoPagamentoEnum.PIX)
                .itens(Collections.singletonList(
                        PagamentoRequestDto.ProdutoRequestDto.builder()
                                .nome("Produto 1")
                                .categoria(CategoriaEnum.SOBREMESA)
                                .preco(10.0)
                                .descricao("Descrição do Produto 1")
                                .build()
                ))
                .build();

        DadosPagamento dadosPagamento = mapper.toDadosPagamentoFromPagamentoRequestDto(pagamentoRequestDto);

        assertNotNull(dadosPagamento);
        assertEquals("123", dadosPagamento.getNumeroPedido());
        assertEquals(100.0, dadosPagamento.getValorTotal());
        assertEquals(MetodoPagamentoEnum.PIX, dadosPagamento.getMetodoPagamento());
        assertNotNull(dadosPagamento.getItens());
        assertEquals(1, dadosPagamento.getItens().size());

        DadosProduto dadosProduto = dadosPagamento.getItens().get(0);
        assertEquals("Produto 1", dadosProduto.getNome());
        assertEquals(CategoriaEnum.SOBREMESA, dadosProduto.getCategoria());
        assertEquals(10.0, dadosProduto.getPreco());
        assertEquals("Descrição do Produto 1", dadosProduto.getDescricao());
    }
}