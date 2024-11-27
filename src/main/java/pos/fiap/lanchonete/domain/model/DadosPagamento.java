package pos.fiap.lanchonete.domain.model;

import lombok.Builder;
import lombok.Data;
import pos.fiap.lanchonete.domain.enums.StatusPagamentoEnum;
import pos.fiap.lanchonete.adapter.out.mercadopago.dto.pagamentoqrcode.PagamentoMPResponseDto;
import pos.fiap.lanchonete.domain.enums.MetodoPagamentoEnum;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class DadosPagamento implements Serializable {
    @Serial
    private static final long serialVersionUID = -8888154278106197658L;
    private String id;
    private StatusPagamentoEnum statusPagamento;
    private MetodoPagamentoEnum metodoPagamento;
    private String qrCode;
    private String qrCodeId;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    private List<DadosProduto> itens;
    private String numeroPedido;
    private Double valorTotal;

    public void completarPagamentoComQrCode(PagamentoMPResponseDto pagamentoMPResponseDto) {
        this.qrCode = pagamentoMPResponseDto.getQrData();
        this.qrCodeId = pagamentoMPResponseDto.getInStoreOrderId();
    }
}