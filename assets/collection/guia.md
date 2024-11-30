# Guia para execução

Segue abaixo instruções para execução.

## - Pagar pedido

Requisição para gerar QrCode do pedido:

[POST] */api/v1/pagamento*

```json
{
  "numeroPedido": "a23bb9c7-c319-4694-9754-9573b9b8b100",
  "metodoPagamento": "PIX",
  "valorTotal": 10.00,
  "itens": [
    {
      "nome": "X-bacon",
      "categoria": "LANCHE",
      "preco": 10.00,
      "descricao": "Pão, carne, queijo e bacon"
    }
  ]
}
```

## - Webhook

Webhook para integração com Mercado Pago e processamento do pagamento:

[POST] */*