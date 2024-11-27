package pos.fiap.lanchonete.adapter.in.api.pagamento.dto;

import org.junit.jupiter.api.Test;
import pos.fiap.lanchonete.domain.enums.CategoriaEnum;
import pos.fiap.lanchonete.domain.enums.MetodoPagamentoEnum;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoRequestDtoTest {

    @Test
    void testPagamentoRequestDtoBuilder() {
        PagamentoRequestDto pagamentoRequest = PagamentoRequestDto.builder()
                .valorTotal(100.0)
                .numeroPedido("12345")
                .metodoPagamento(MetodoPagamentoEnum.PIX)
                .itens(List.of(
                        PagamentoRequestDto.ProdutoRequestDto.builder()
                                .nome("Produto A")
                                .categoria(CategoriaEnum.ACOMPANHAMENTO)
                                .preco(50.0)
                                .descricao("Descrição Produto A")
                                .build(),
                        PagamentoRequestDto.ProdutoRequestDto.builder()
                                .nome("Produto B")
                                .categoria(CategoriaEnum.BEBIDA)
                                .preco(50.0)
                                .descricao("Descrição Produto B")
                                .build()
                ))
                .build();

        assertNotNull(pagamentoRequest);
        assertEquals(100.0, pagamentoRequest.getValorTotal());
        assertEquals("12345", pagamentoRequest.getNumeroPedido());
        assertEquals(MetodoPagamentoEnum.PIX, pagamentoRequest.getMetodoPagamento());
        assertEquals(2, pagamentoRequest.getItens().size());
    }

    @Test
    void testProdutoRequestDtoBuilder() {
        PagamentoRequestDto.ProdutoRequestDto produto = PagamentoRequestDto.ProdutoRequestDto.builder()
                .nome("Produto A")
                .categoria(CategoriaEnum.BEBIDA)
                .preco(50.0)
                .descricao("Descrição Produto A")
                .build();

        assertNotNull(produto);
        assertEquals("Produto A", produto.getNome());
        assertEquals(CategoriaEnum.BEBIDA, produto.getCategoria());
        assertEquals(50.0, produto.getPreco());
        assertEquals("Descrição Produto A", produto.getDescricao());
    }
}