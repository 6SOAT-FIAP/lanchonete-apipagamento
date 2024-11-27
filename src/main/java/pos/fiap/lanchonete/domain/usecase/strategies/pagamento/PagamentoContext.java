package pos.fiap.lanchonete.domain.usecase.strategies.pagamento;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pos.fiap.lanchonete.domain.enums.MetodoPagamentoEnum;
import pos.fiap.lanchonete.domain.model.DadosPagamento;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static pos.fiap.lanchonete.domain.enums.MetodoPagamentoEnum.*;

@Service
@RequiredArgsConstructor
public class PagamentoContext {

    private Map<MetodoPagamentoEnum, PagamentoStrategy> pagamentoStrategy;
    private final PagamentoPixStrategy pagamentoPixStrategy;

    public DadosPagamento processarPagamento(DadosPagamento dadosPagamento) {
        buildStrategies();

        var metodoPagamentoEnum = valueOf(dadosPagamento.getMetodoPagamento().name().toUpperCase());
        var strategy = pagamentoStrategy.get(metodoPagamentoEnum);

        if (isNull(strategy)) {
            throw new IllegalArgumentException(format("O metodo de pagamento nao é suportado: %s", metodoPagamentoEnum));
        }

        return strategy.processarPagamento(dadosPagamento);
    }

    public void atualizarPagamento(DadosPagamento dadosPagamento) {
        buildStrategies();

        var metodoPagamentoEnum = valueOf(dadosPagamento.getMetodoPagamento().name().toUpperCase());
        var strategy = pagamentoStrategy.get(metodoPagamentoEnum);

        if (isNull(strategy)) {
            throw new IllegalArgumentException(format("O metodo de pagamento nao é suportado: %s", metodoPagamentoEnum));
        }
        strategy.atualizarPagamento(dadosPagamento);
    }

    private void buildStrategies() {
        pagamentoStrategy = new HashMap<>();
        pagamentoStrategy.put(PIX, pagamentoPixStrategy);
    }
}