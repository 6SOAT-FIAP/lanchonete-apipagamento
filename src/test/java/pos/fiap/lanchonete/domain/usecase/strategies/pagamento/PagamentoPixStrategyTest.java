package pos.fiap.lanchonete.domain.usecase.strategies.pagamento;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pos.fiap.lanchonete.adapter.out.mercadopago.dto.pagamentoqrcode.PagamentoMPResponseDto;
import pos.fiap.lanchonete.domain.enums.StatusPagamentoEnum;
import pos.fiap.lanchonete.domain.model.DadosPagamento;
import pos.fiap.lanchonete.port.MercadoPagoPort;
import pos.fiap.lanchonete.port.PagamentoDynamoAdapterPort;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PagamentoPixStrategyTest {

    @Mock
    private MercadoPagoPort mercadoPagoPort;

    @Mock
    private PagamentoDynamoAdapterPort pagamentoDynamoAdapterPort;

    @InjectMocks
    private PagamentoPixStrategy pagamentoPixStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessarPagamento_ComSucesso() {
        DadosPagamento dadosPagamento = DadosPagamento.builder().build();
        dadosPagamento.setStatusPagamento(null);

        when(mercadoPagoPort.gerarPagamentoQRCode(dadosPagamento)).thenReturn(Mockito.mock(PagamentoMPResponseDto.class));

        DadosPagamento dadosPagamentoSalvo = DadosPagamento.builder().build();
        dadosPagamentoSalvo.setStatusPagamento(StatusPagamentoEnum.AGUARDANDO);
        when(pagamentoDynamoAdapterPort.salvarPagamento(dadosPagamento)).thenReturn(dadosPagamentoSalvo);

        DadosPagamento resultado = pagamentoPixStrategy.processarPagamento(dadosPagamento);

        assertEquals(StatusPagamentoEnum.AGUARDANDO, resultado.getStatusPagamento());
        verify(mercadoPagoPort, times(1)).gerarPagamentoQRCode(dadosPagamento);
        verify(pagamentoDynamoAdapterPort, times(1)).salvarPagamento(dadosPagamento);
    }

    @Test
    void testAtualizarPagamento_ComSucesso() {
        DadosPagamento dadosPagamento = DadosPagamento.builder().build();

        pagamentoPixStrategy.atualizarPagamento(dadosPagamento);

        verify(pagamentoDynamoAdapterPort, times(1)).salvarPagamento(dadosPagamento);
    }
}