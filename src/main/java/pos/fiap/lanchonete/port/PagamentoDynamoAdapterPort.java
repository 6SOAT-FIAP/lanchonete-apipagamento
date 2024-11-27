package pos.fiap.lanchonete.port;

import pos.fiap.lanchonete.domain.model.DadosPagamento;

public interface PagamentoDynamoAdapterPort {

    DadosPagamento obterDadosPagamento(String idPedido);

    DadosPagamento salvarPagamento(DadosPagamento dadosPagamento);
}