package pos.fiap.lanchonete.adapter.out.mercadopago.dto.pagamentoqrcode;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PagamentoMPResponseDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("external_reference")
    private String externalReference;

    @JsonProperty("date_approved")
    private String dateApproved;

    @JsonProperty("status")
    private String status;

    @JsonProperty("status_detail")
    private String statusDetail;

    @JsonProperty("in_store_order_id")
    private String inStoreOrderId;

    @JsonProperty("qr_data")
    private String qrData;
}