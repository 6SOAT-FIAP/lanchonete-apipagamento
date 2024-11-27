package pos.fiap.lanchonete.adapter.in.api.webhook;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import pos.fiap.lanchonete.adapter.in.api.webhook.dto.WebHookRequestDto;
import pos.fiap.lanchonete.port.PagamentoWebHookPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PagamentoWebHookControllerTest {

    private final PagamentoWebHookPort pagamentoWebHookPort = mock(PagamentoWebHookPort.class);
    private final PagamentoWebHookController controller = new PagamentoWebHookController(pagamentoWebHookPort);

    @Test
    void testProcessarPagamentoComMerchantOrderId() {
        WebHookRequestDto webHookRequestDto = WebHookRequestDto.builder()
                .resource("https://example.com/merchant_orders/12345")
                .topic("payment")
                .build();

        ResponseEntity<Void> response = controller.processarPagamento(webHookRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        verify(pagamentoWebHookPort, times(1)).processarPagamento("12345");
    }

    @Test
    void testProcessarPagamentoSemMerchantOrderId() {
        WebHookRequestDto webHookRequestDto = WebHookRequestDto.builder()
                .resource("https://example.com/merchant_orders/")
                .topic("payment")
                .build();

        ResponseEntity<Void> response = controller.processarPagamento(webHookRequestDto);

        assertEquals(404, response.getStatusCodeValue());
        verify(pagamentoWebHookPort, never()).processarPagamento(Mockito.anyString());
    }
}
