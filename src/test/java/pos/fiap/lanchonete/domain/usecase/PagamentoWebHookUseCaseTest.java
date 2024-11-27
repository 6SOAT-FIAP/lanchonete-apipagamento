package pos.fiap.lanchonete.domain.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pos.fiap.lanchonete.domain.enums.StatusPagamentoEnum;
import pos.fiap.lanchonete.domain.model.DadosPagamento;
import pos.fiap.lanchonete.port.MercadoPagoPort;
import pos.fiap.lanchonete.port.PagamentoUseCasePort;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PagamentoWebHookUseCaseTest {

    @Mock
    private MercadoPagoPort mercadoPagoPort;

    @Mock
    private PagamentoUseCasePort pagamentoUseCasePort;

    @InjectMocks
    private PagamentoWebHookUseCase pagamentoWebHookUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessarPagamento() {
        String merchantOrderId = "12345";
        String numeroPedido = "54321";
        DadosPagamento dadosPagamento = DadosPagamento.builder().build();

        when(mercadoPagoPort.obterNumeroPedido(merchantOrderId)).thenReturn(numeroPedido);
        when(pagamentoUseCasePort.obterDadosPagamento(numeroPedido)).thenReturn(dadosPagamento);

        pagamentoWebHookUseCase.processarPagamento(merchantOrderId);

        verify(mercadoPagoPort, times(1)).obterNumeroPedido(merchantOrderId);
        verify(pagamentoUseCasePort, times(1)).obterDadosPagamento(numeroPedido);
        verify(pagamentoUseCasePort, times(1)).atualizarPagamento(dadosPagamento);

        assertEquals(StatusPagamentoEnum.APROVADO, dadosPagamento.getStatusPagamento());
    }
}