package pos.fiap.lanchonete.adapter.out.mercadopago.dto.merchantorder;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MerchantOrderResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -2834648702959989860L;

    private String externalReference;
}
