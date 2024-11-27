package pos.fiap.lanchonete.adapter.in.api.pagamento.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pos.fiap.lanchonete.domain.enums.CategoriaEnum;
import pos.fiap.lanchonete.domain.enums.MetodoPagamentoEnum;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagamentoRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 142698616682300465L;

    private Double valorTotal;
    private String numeroPedido;
    private MetodoPagamentoEnum metodoPagamento;
    private List<ProdutoRequestDto> itens;

    @Getter
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProdutoRequestDto implements Serializable {
        @Serial
        private static final long serialVersionUID = -7187216715845710615L;

        private String nome;
        private CategoriaEnum categoria;
        private Double preco;
        private String descricao;
    }
}
