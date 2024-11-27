package pos.fiap.lanchonete.domain.model.entity;

import lombok.Builder;
import lombok.Data;
import pos.fiap.lanchonete.domain.enums.StatusPagamentoEnum;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class Pagamento implements Serializable {

    @Serial
    private static final long serialVersionUID = -5022390632046501317L;

    private StatusPagamentoEnum statusPagamento;
}