Feature: Validação de pagamento realizado

  Scenario: Realizar pagamento com sucesso
    Given o seguinte pagamento foi realizado:
      """
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
      """
    Then o código de resposta deve ser 200 para o pagamento realizado
    And o corpo da resposta deve conter o statusPagamento "AGUARDANDO", metodoPagamento "PIX" e numeroPedido "a23bb9c7-c319-4694-9754-9573b9b8b100"
    And o corpo da resposta deve conter o qrCode
    And o corpo da resposta deve conter a dataCriacao
