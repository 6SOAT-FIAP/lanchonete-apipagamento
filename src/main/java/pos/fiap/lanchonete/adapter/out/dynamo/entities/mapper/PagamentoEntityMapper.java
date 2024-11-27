package pos.fiap.lanchonete.adapter.out.dynamo.entities.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pos.fiap.lanchonete.adapter.out.dynamo.entities.PagamentoEntity;
import pos.fiap.lanchonete.domain.model.DadosPagamento;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PagamentoEntityMapper {

    @Mapping(target = "idPedido", source = "dadosPagamento.numeroPedido")
    @Mapping(target = "qrCode", source = "dadosPagamento.qrCode")
    @Mapping(target = "qrCodeId", source = "dadosPagamento.qrCodeId")
    PagamentoEntity toEntity(DadosPagamento dadosPagamento);

    @Mapping(target = "numeroPedido", source = "pagamentoEntity.idPedido")
    DadosPagamento toDadosPagamento(PagamentoEntity pagamentoEntity);

}
