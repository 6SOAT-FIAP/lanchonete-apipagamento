Feature: Validação de pagamento inconsistente com método CHEQUE

  Scenario: Tentar realizar pagamento com cheque
    Given o seguinte pagamento com metodo CHEQUE foi realizado:
      """
      {
        "numeroPedido": "a23bb9c7-c319-4694-9754-9573b9b8b100",
        "metodoPagamento": "CHEQUE",
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
    Then o código de resposta deve ser 400 para pagamento com cheque
