package pos.fiap.lanchonete.port;

import pos.fiap.lanchonete.domain.model.DadosPagamento;

public interface PagamentoUseCasePort {

    DadosPagamento obterDadosPagamento(String idPedido);

    DadosPagamento processarPagamento(DadosPagamento dadosPagamento);

    void atualizarPagamento(DadosPagamento dadosPagamento);
}