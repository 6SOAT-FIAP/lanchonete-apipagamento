package pos.fiap.lanchonete.adapter.in.api.webhook;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.fiap.lanchonete.adapter.in.api.webhook.dto.WebHookRequestDto;
import pos.fiap.lanchonete.port.PagamentoWebHookPort;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class PagamentoWebHookController {

    private final PagamentoWebHookPort pagamentoWebHookPort;

    @ResponseStatus(OK)
    @PostMapping
    public ResponseEntity<Void> processarPagamento(@RequestBody WebHookRequestDto webHookRequestDto) {

        log.info("Processando WebHook: {}", webHookRequestDto);

        var merchantOrderId = StringUtils.substringAfter(webHookRequestDto.getResource(), "merchant_orders/");

        if (merchantOrderId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        pagamentoWebHookPort.processarPagamento(merchantOrderId);

        log.info("WebHook processado");

        return ResponseEntity.ok().build();
    }
}