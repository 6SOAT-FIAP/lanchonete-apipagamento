package pos.fiap.lanchonete.adapter.in.api.pagamento;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.fiap.lanchonete.adapter.in.api.pagamento.dto.PagamentoRequestDto;
import pos.fiap.lanchonete.adapter.in.api.pagamento.dto.PagamentoResponseDto;
import pos.fiap.lanchonete.adapter.in.api.pagamento.mapper.PagamentoDtoMapper;
import pos.fiap.lanchonete.port.PagamentoUseCasePort;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/pagamento")
@RestController
public class PagamentoController {

    private final PagamentoUseCasePort pagamentoUseCasePort;
    private final PagamentoDtoMapper pagamentoDtoMapper;

    @ResponseStatus(OK)
    @PostMapping
    public ResponseEntity<PagamentoResponseDto> realizarPagamento(@RequestBody @Valid PagamentoRequestDto pagamentoRequestDto) {

        log.info("Realizando pagamento {}", pagamentoRequestDto);

        var dadosPagamento = pagamentoDtoMapper.toDadosPagamentoFromPagamentoRequestDto(pagamentoRequestDto);
        var pagamentoProcessado = pagamentoUseCasePort.processarPagamento(dadosPagamento);
        var pagamentoResponse = pagamentoDtoMapper.toPagamentoResponseDtoFromDadosPagamento(pagamentoProcessado);

        log.info("Pagamento realizado {}", pagamentoResponse);

        return ResponseEntity.ok().body(pagamentoResponse);
    }
}