package pos.fiap.lanchonete.adapter.out.dynamo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pos.fiap.lanchonete.adapter.out.dynamo.entities.mapper.PagamentoEntityMapper;
import pos.fiap.lanchonete.adapter.out.dynamo.repository.PagamentoRepository;
import pos.fiap.lanchonete.domain.model.DadosPagamento;
import pos.fiap.lanchonete.port.PagamentoDynamoAdapterPort;

import static java.util.Objects.nonNull;
import static pos.fiap.lanchonete.utils.Constantes.FIM;
import static pos.fiap.lanchonete.utils.Constantes.INICIO;

@Slf4j
@Component
@RequiredArgsConstructor
public class PagamentoDynamoAdapter implements PagamentoDynamoAdapterPort {

    private static final String SERVICE_NAME = "PagamentoDynamoAdapter";
    private static final String BUSCAR_STATUS_PAGAMENTO_METHOD_NAME = "buscarStatusPagamento";
    private static final String SALVAR_PAGAMENTO_METHOD_NAME = "salvarPagamento";
    private static final String STRING_LOG_FORMAT = "%s_%s_%s {}";

    private final PagamentoRepository pagamentoRepository;
    private final PagamentoEntityMapper pagamentoEntityMapper;

    @Override
    public DadosPagamento obterDadosPagamento(String idPedido) {
        log.info(String.format(STRING_LOG_FORMAT, SERVICE_NAME, BUSCAR_STATUS_PAGAMENTO_METHOD_NAME, INICIO), idPedido);

        var pagamentoEntity = pagamentoRepository.findByIdPedido(idPedido);

        log.info(String.format(STRING_LOG_FORMAT, SERVICE_NAME, BUSCAR_STATUS_PAGAMENTO_METHOD_NAME, FIM), idPedido);
        return pagamentoEntityMapper.toDadosPagamento(pagamentoEntity);
    }

    @Override
    public DadosPagamento salvarPagamento(DadosPagamento dadosPagamento) {
        log.info(String.format(STRING_LOG_FORMAT, SERVICE_NAME, SALVAR_PAGAMENTO_METHOD_NAME, INICIO), dadosPagamento);

        var pagamentoEntity = pagamentoRepository.findByIdPedido(dadosPagamento.getNumeroPedido());

        if (nonNull(pagamentoEntity)) {
            pagamentoRepository.deleteById(pagamentoEntity.getId());
            pagamentoEntity.atualizarDadosEntity(dadosPagamento);
        } else {
            pagamentoEntity = pagamentoEntityMapper.toEntity(dadosPagamento);
        }

        pagamentoEntity = pagamentoRepository.save(pagamentoEntity);

        var pagamento = pagamentoEntityMapper.toDadosPagamento(pagamentoEntity);
        log.info(String.format(STRING_LOG_FORMAT, SERVICE_NAME, SALVAR_PAGAMENTO_METHOD_NAME, FIM), pagamento);
        return pagamento;
    }
}