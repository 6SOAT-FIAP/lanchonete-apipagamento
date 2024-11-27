package pos.fiap.lanchonete.domain.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pos.fiap.lanchonete.domain.model.DadosPagamento;
import pos.fiap.lanchonete.domain.usecase.strategies.pagamento.PagamentoContext;
import pos.fiap.lanchonete.port.PagamentoDynamoAdapterPort;
import pos.fiap.lanchonete.port.PagamentoUseCasePort;

@Slf4j
@Service
@RequiredArgsConstructor
public class PagamentoUseCase implements PagamentoUseCasePort {
    private final PagamentoDynamoAdapterPort pagamentoMongoAdapterPort;
    private final PagamentoContext pagamentoContext;

    @Override
    public DadosPagamento obterDadosPagamento(String idPedido) {
        return pagamentoMongoAdapterPort.obterDadosPagamento(idPedido);
    }

    @Override
    public DadosPagamento processarPagamento(DadosPagamento dadosPagamento) {
        return pagamentoContext.processarPagamento(dadosPagamento);
    }

    @Override
    public void atualizarPagamento(DadosPagamento dadosPagamento) {
        pagamentoContext.atualizarPagamento(dadosPagamento);
    }
}