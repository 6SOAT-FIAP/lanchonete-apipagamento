package pos.fiap.lanchonete.port;

import pos.fiap.lanchonete.adapter.out.mercadopago.dto.pagamentoqrcode.PagamentoMPResponseDto;
import pos.fiap.lanchonete.domain.model.DadosPagamento;

public interface MercadoPagoPort {

    PagamentoMPResponseDto gerarPagamentoQRCode(DadosPagamento dadosPagamento);

    String obterNumeroPedido(String merchantOrderId);
}