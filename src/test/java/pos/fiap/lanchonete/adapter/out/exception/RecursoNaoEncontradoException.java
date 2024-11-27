package pos.fiap.lanchonete.adapter.out.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecursoNaoEncontradoExceptionTest {

    @Test
    void testRecursoNaoEncontradoExceptionComMensagem() {
        String mensagem = "Recurso não encontrado";

        RecursoNaoEncontradoException exception = new RecursoNaoEncontradoException(mensagem);

        assertEquals(mensagem, exception.getMessage());
    }

    @Test
    void testRecursoNaoEncontradoExceptionComMensagemNull() {
        String mensagem = null;

        RecursoNaoEncontradoException exception = new RecursoNaoEncontradoException(mensagem);

        assertNull(exception.getMessage());
    }
}
