package pos.fiap.lanchonete.adapter.in.api.pagamento;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pos.fiap.lanchonete.adapter.in.api.pagamento.dto.PagamentoRequestDto;
import pos.fiap.lanchonete.adapter.in.api.pagamento.dto.PagamentoResponseDto;
import pos.fiap.lanchonete.adapter.in.api.pagamento.mapper.PagamentoDtoMapper;
import pos.fiap.lanchonete.domain.enums.MetodoPagamentoEnum;
import pos.fiap.lanchonete.domain.enums.StatusPagamentoEnum;
import pos.fiap.lanchonete.domain.model.DadosPagamento;
import pos.fiap.lanchonete.port.PagamentoUseCasePort;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PagamentoControllerTest {

    @Mock
    private PagamentoUseCasePort pagamentoUseCasePort;

    @Mock
    private PagamentoDtoMapper pagamentoDtoMapper;

    private PagamentoController pagamentoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pagamentoController = new PagamentoController(pagamentoUseCasePort, pagamentoDtoMapper);
    }

    @Test
    void testRealizarPagamento() {
        PagamentoRequestDto pagamentoRequestDto = PagamentoRequestDto.builder()
                .valorTotal(200.0)
                .numeroPedido("12345")
                .metodoPagamento(MetodoPagamentoEnum.PIX)
                .build();

        DadosPagamento dadosPagamento = DadosPagamento.builder()
                .numeroPedido("12345")
                .valorTotal(200.0)
                .metodoPagamento(MetodoPagamentoEnum.PIX)
                .statusPagamento(StatusPagamentoEnum.AGUARDANDO)
                .build();

        DadosPagamento pagamentoProcessado = DadosPagamento.builder()
                .numeroPedido("12345")
                .valorTotal(200.0)
                .metodoPagamento(MetodoPagamentoEnum.PIX)
                .statusPagamento(StatusPagamentoEnum.AGUARDANDO)
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();

        PagamentoResponseDto pagamentoResponseDto = PagamentoResponseDto.builder()
                .numeroPedido("12345")
                .metodoPagamento(MetodoPagamentoEnum.PIX)
                .statusPagamento(StatusPagamentoEnum.AGUARDANDO)
                .dataCriacao(pagamentoProcessado.getDataCriacao())
                .dataAtualizacao(pagamentoProcessado.getDataAtualizacao())
                .build();

        when(pagamentoDtoMapper.toDadosPagamentoFromPagamentoRequestDto(pagamentoRequestDto)).thenReturn(dadosPagamento);
        when(pagamentoUseCasePort.processarPagamento(dadosPagamento)).thenReturn(pagamentoProcessado);
        when(pagamentoDtoMapper.toPagamentoResponseDtoFromDadosPagamento(pagamentoProcessado)).thenReturn(pagamentoResponseDto);

        var responseEntity = pagamentoController.realizarPagamento(pagamentoRequestDto);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(pagamentoResponseDto, responseEntity.getBody());
        assertEquals("12345", responseEntity.getBody().getNumeroPedido());
        assertEquals(MetodoPagamentoEnum.PIX, responseEntity.getBody().getMetodoPagamento());
        assertEquals(StatusPagamentoEnum.AGUARDANDO, responseEntity.getBody().getStatusPagamento());

        verify(pagamentoDtoMapper, times(1)).toDadosPagamentoFromPagamentoRequestDto(pagamentoRequestDto);
        verify(pagamentoUseCasePort, times(1)).processarPagamento(dadosPagamento);
        verify(pagamentoDtoMapper, times(1)).toPagamentoResponseDtoFromDadosPagamento(pagamentoProcessado);
    }
}