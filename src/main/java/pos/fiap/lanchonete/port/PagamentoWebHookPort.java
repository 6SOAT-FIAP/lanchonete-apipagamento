package pos.fiap.lanchonete.port;

public interface PagamentoWebHookPort {

    void processarPagamento(String merchantOrderId);
}
