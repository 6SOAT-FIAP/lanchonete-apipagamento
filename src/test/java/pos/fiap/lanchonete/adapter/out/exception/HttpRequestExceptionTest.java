package pos.fiap.lanchonete.adapter.out.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestExceptionTest {

    @Test
    void testHttpRequestExceptionComMensagemECausa() {
        String mensagem = "Erro na requisição HTTP";
        Throwable causa = new Throwable("Erro causado por falha na rede");

        HttpRequestException exception = new HttpRequestException(mensagem, causa);

        assertEquals(mensagem, exception.getMessage());
        assertEquals(causa, exception.getCause());
    }

    @Test
    void testHttpRequestExceptionSomenteComMensagem() {
        String mensagem = "Erro na requisição HTTP";

        HttpRequestException exception = new HttpRequestException(mensagem, null);

        assertEquals(mensagem, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testHttpRequestExceptionSomenteComCausa() {
        Throwable causa = new Throwable("Erro causado por falha na rede");

        HttpRequestException exception = new HttpRequestException(null, causa);

        assertNull(exception.getMessage());
        assertEquals(causa, exception.getCause());
    }
}
