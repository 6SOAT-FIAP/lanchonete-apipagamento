package pos.fiap.lanchonete.adapter.out.mercadopago.dto.pagamentoqrcode;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import pos.fiap.lanchonete.domain.model.DadosPagamento;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import static pos.fiap.lanchonete.adapter.out.mercadopago.mapper.PagamentoMapper.buildPaymentRequest;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PagamentoMPRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -5177723034855181589L;
    private String title;
    private String description;
    private String externalReference;
    private List<ItemPagamentoMPRequestDto> items;
    private String notificationUrl;
    private final BigDecimal totalAmount;

    @Data
    @Builder
    @AllArgsConstructor
    @RequiredArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ItemPagamentoMPRequestDto implements Serializable {
        @Serial
        private static final long serialVersionUID = 8295014281922837357L;

        private String title;
        private String description;
        private String category;
        private BigDecimal unitPrice;
        private Integer quantity;
        private String unitMeasure;
        private BigDecimal totalAmount;
    }

    public static HttpEntity<PagamentoMPRequestDto> buildRequestEntity(DadosPagamento dadosPagamento, HttpHeaders headers, String notificationUrl) {
        var requestEntity = buildPaymentRequest(dadosPagamento);
        requestEntity.setNotificationUrl(notificationUrl);

        return new HttpEntity<>(requestEntity, headers);
    }
}
