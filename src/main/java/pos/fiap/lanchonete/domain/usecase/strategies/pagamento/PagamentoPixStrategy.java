package pos.fiap.lanchonete.domain.usecase.strategies.pagamento;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pos.fiap.lanchonete.domain.model.DadosPagamento;
import pos.fiap.lanchonete.port.MercadoPagoPort;
import pos.fiap.lanchonete.port.PagamentoDynamoAdapterPort;

import static pos.fiap.lanchonete.domain.enums.StatusPagamentoEnum.AGUARDANDO;

@Component
@RequiredArgsConstructor
public class PagamentoPixStrategy implements PagamentoStrategy {
    private final MercadoPagoPort mercadoPagoPort;
    private final PagamentoDynamoAdapterPort pagamentoMongoAdapterPort;

    @Override
    public DadosPagamento processarPagamento(DadosPagamento dadosPagamento) {
        final var qrCodeData = mercadoPagoPort.gerarPagamentoQRCode(dadosPagamento);
        dadosPagamento.completarPagamentoComQrCode(qrCodeData);
        dadosPagamento.setStatusPagamento(AGUARDANDO);

        return pagamentoMongoAdapterPort.salvarPagamento(dadosPagamento);
    }

    @Override
    public void atualizarPagamento(DadosPagamento dadosPagamento) {
        pagamentoMongoAdapterPort.salvarPagamento(dadosPagamento);
    }
}
