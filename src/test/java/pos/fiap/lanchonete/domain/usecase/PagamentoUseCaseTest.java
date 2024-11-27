package pos.fiap.lanchonete.domain.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pos.fiap.lanchonete.domain.model.DadosPagamento;
import pos.fiap.lanchonete.domain.usecase.strategies.pagamento.PagamentoContext;
import pos.fiap.lanchonete.port.PagamentoDynamoAdapterPort;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PagamentoUseCaseTest {

    @Mock
    private PagamentoDynamoAdapterPort pagamentoMongoAdapterPort;

    @Mock
    private PagamentoContext pagamentoContext;

    @InjectMocks
    private PagamentoUseCase pagamentoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObterDadosPagamento() {
        String idPedido = "12345";
        DadosPagamento expectedDadosPagamento = mock(DadosPagamento.class);
        when(pagamentoMongoAdapterPort.obterDadosPagamento(idPedido)).thenReturn(expectedDadosPagamento);

        DadosPagamento result = pagamentoUseCase.obterDadosPagamento(idPedido);

        assertEquals(expectedDadosPagamento, result);
        verify(pagamentoMongoAdapterPort, times(1)).obterDadosPagamento(idPedido);
    }

    @Test
    void testProcessarPagamento() {
        DadosPagamento inputDadosPagamento = mock(DadosPagamento.class);
        DadosPagamento expectedDadosPagamento = mock(DadosPagamento.class);
        when(pagamentoContext.processarPagamento(inputDadosPagamento)).thenReturn(expectedDadosPagamento);

        DadosPagamento result = pagamentoUseCase.processarPagamento(inputDadosPagamento);

        assertEquals(expectedDadosPagamento, result);
        verify(pagamentoContext, times(1)).processarPagamento(inputDadosPagamento);
    }

    @Test
    void testAtualizarPagamento() {
        DadosPagamento dadosPagamento = mock(DadosPagamento.class);

        pagamentoUseCase.atualizarPagamento(dadosPagamento);

        verify(pagamentoContext, times(1)).atualizarPagamento(dadosPagamento);
    }
}
