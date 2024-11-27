package pos.fiap.lanchonete.adapter.in.api.pagamento.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pos.fiap.lanchonete.domain.enums.MetodoPagamentoEnum;
import pos.fiap.lanchonete.domain.enums.StatusPagamentoEnum;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagamentoResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -164114208446131289L;

    private StatusPagamentoEnum statusPagamento;
    private MetodoPagamentoEnum metodoPagamento;
    private String numeroPedido;
    private String qrCode;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}