package pos.fiap.lanchonete.domain.usecase.strategies.pagamento;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pos.fiap.lanchonete.domain.enums.MetodoPagamentoEnum;
import pos.fiap.lanchonete.domain.model.DadosPagamento;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PagamentoContextTest {

    @Mock
    private PagamentoPixStrategy pagamentoPixStrategy;

    @InjectMocks
    private PagamentoContext pagamentoContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessarPagamento_ComMetodoPix() {
        DadosPagamento dadosPagamento = DadosPagamento.builder().build();
        dadosPagamento.setMetodoPagamento(MetodoPagamentoEnum.PIX);

        when(pagamentoPixStrategy.processarPagamento(dadosPagamento)).thenReturn(dadosPagamento);

        var resultado = pagamentoContext.processarPagamento(dadosPagamento);

        assertEquals(dadosPagamento, resultado);
        verify(pagamentoPixStrategy, times(1)).processarPagamento(dadosPagamento);
    }

    @Test
    void testProcessarPagamento_MetodoNaoSuportado() {
        DadosPagamento dadosPagamento = DadosPagamento.builder().build();
        dadosPagamento.setMetodoPagamento(MetodoPagamentoEnum.CARTAO);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pagamentoContext.processarPagamento(dadosPagamento)
        );

        assertEquals("O metodo de pagamento nao é suportado: CARTAO", exception.getMessage());
    }

    @Test
    void testAtualizarPagamento_ComMetodoPix() {
        DadosPagamento dadosPagamento = DadosPagamento.builder().build();
        dadosPagamento.setMetodoPagamento(MetodoPagamentoEnum.PIX);

        pagamentoContext.atualizarPagamento(dadosPagamento);

        verify(pagamentoPixStrategy, times(1)).atualizarPagamento(dadosPagamento);
    }

    @Test
    void testAtualizarPagamento_MetodoNaoSuportado() {
        DadosPagamento dadosPagamento = DadosPagamento.builder().build();
        dadosPagamento.setMetodoPagamento(MetodoPagamentoEnum.CARTAO);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pagamentoContext.atualizarPagamento(dadosPagamento)
        );

        assertEquals("O metodo de pagamento nao é suportado: CARTAO", exception.getMessage());
    }
}