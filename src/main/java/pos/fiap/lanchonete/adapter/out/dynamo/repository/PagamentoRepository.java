package pos.fiap.lanchonete.adapter.out.dynamo.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import pos.fiap.lanchonete.adapter.out.dynamo.entities.PagamentoEntity;

@EnableScan
@Repository
public interface PagamentoRepository extends CrudRepository<PagamentoEntity, String> {

    PagamentoEntity findByIdPedido(@Param("idPedido") String idPedido);
}
