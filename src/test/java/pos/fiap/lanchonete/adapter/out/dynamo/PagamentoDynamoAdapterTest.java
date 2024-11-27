package pos.fiap.lanchonete.adapter.out.dynamo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pos.fiap.lanchonete.adapter.out.dynamo.entities.PagamentoEntity;
import pos.fiap.lanchonete.adapter.out.dynamo.entities.mapper.PagamentoEntityMapper;
import pos.fiap.lanchonete.adapter.out.dynamo.repository.PagamentoRepository;
import pos.fiap.lanchonete.domain.model.DadosPagamento;
import pos.fiap.lanchonete.domain.enums.StatusPagamentoEnum;
import pos.fiap.lanchonete.domain.enums.MetodoPagamentoEnum;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PagamentoDynamoAdapterTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private PagamentoEntityMapper pagamentoEntityMapper;

    @InjectMocks
    private PagamentoDynamoAdapter pagamentoDynamoAdapter;

    private DadosPagamento dadosPagamento;
    private PagamentoEntity pagamentoEntity;

    @BeforeEach
    void setUp() {
        dadosPagamento = DadosPagamento.builder()
                .numeroPedido("12345")
                .statusPagamento(StatusPagamentoEnum.AGUARDANDO)
                .metodoPagamento(MetodoPagamentoEnum.PIX)
                .qrCode("QRCode123")
                .qrCodeId("QRCodeId123")
                .build();

        pagamentoEntity = new PagamentoEntity();
        pagamentoEntity.setId("1");
        pagamentoEntity.setIdPedido("12345");
        pagamentoEntity.setStatusPagamento(StatusPagamentoEnum.AGUARDANDO.name());
        pagamentoEntity.setMetodoPagamento(MetodoPagamentoEnum.PIX.name());
        pagamentoEntity.setQrCode("QRCode123");
        pagamentoEntity.setQrCodeId("QRCodeId123");
    }

    @Test
    void testObterDadosPagamento() {
        when(pagamentoRepository.findByIdPedido("12345")).thenReturn(pagamentoEntity);
        when(pagamentoEntityMapper.toDadosPagamento(any(PagamentoEntity.class))).thenReturn(dadosPagamento);

        DadosPagamento resultado = pagamentoDynamoAdapter.obterDadosPagamento("12345");

        assertNotNull(resultado);
        assertEquals("12345", resultado.getNumeroPedido());
        assertEquals(StatusPagamentoEnum.AGUARDANDO, resultado.getStatusPagamento());
        verify(pagamentoRepository, times(1)).findByIdPedido("12345");
        verify(pagamentoEntityMapper, times(1)).toDadosPagamento(any(PagamentoEntity.class));
    }

    @Test
    void testSalvarPagamentoNovo() {
        when(pagamentoRepository.findByIdPedido("12345")).thenReturn(null);
        when(pagamentoEntityMapper.toEntity(any(DadosPagamento.class))).thenReturn(pagamentoEntity);
        when(pagamentoRepository.save(any(PagamentoEntity.class))).thenReturn(pagamentoEntity);
        when(pagamentoEntityMapper.toDadosPagamento(any(PagamentoEntity.class))).thenReturn(dadosPagamento);

        DadosPagamento resultado = pagamentoDynamoAdapter.salvarPagamento(dadosPagamento);

        assertNotNull(resultado);
        assertEquals("12345", resultado.getNumeroPedido());
        assertEquals(StatusPagamentoEnum.AGUARDANDO, resultado.getStatusPagamento());
        verify(pagamentoRepository, times(1)).findByIdPedido("12345");
        verify(pagamentoEntityMapper, times(1)).toEntity(any(DadosPagamento.class));
        verify(pagamentoRepository, times(1)).save(any(PagamentoEntity.class));
        verify(pagamentoEntityMapper, times(1)).toDadosPagamento(any(PagamentoEntity.class));
    }

    @Test
    void testSalvarPagamentoExistente() {
        PagamentoEntity pagamentoEntityExistente = new PagamentoEntity();
        pagamentoEntityExistente.setId("1");
        pagamentoEntityExistente.setIdPedido("12345");
        pagamentoEntityExistente.setStatusPagamento(StatusPagamentoEnum.AGUARDANDO.name());

        when(pagamentoRepository.findByIdPedido("12345")).thenReturn(pagamentoEntityExistente);
        when(pagamentoRepository.save(any(PagamentoEntity.class))).thenReturn(pagamentoEntityExistente);
        when(pagamentoEntityMapper.toDadosPagamento(any(PagamentoEntity.class))).thenReturn(dadosPagamento);

        DadosPagamento resultado = pagamentoDynamoAdapter.salvarPagamento(dadosPagamento);

        assertNotNull(resultado);
        assertEquals("12345", resultado.getNumeroPedido());
        assertEquals(StatusPagamentoEnum.AGUARDANDO, resultado.getStatusPagamento());

        verify(pagamentoRepository, times(1)).findByIdPedido("12345");
        verify(pagamentoRepository, times(1)).deleteById("1");
        verify(pagamentoRepository, times(1)).save(any(PagamentoEntity.class));

        verify(pagamentoEntityMapper, times(1)).toDadosPagamento(any(PagamentoEntity.class));
    }
}