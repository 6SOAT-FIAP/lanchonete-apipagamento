# API de Pagamentos

## O que é o projeto

Há uma lanchonete de bairro que está se expandindo devido ao seu grande sucesso.

Nesse contexto, um sistema de controle de pedidos é essencial para garantir que a lanchonete possa atender os clientes
de maneira eficiente, gerenciando seus pedidos e estoques de forma adequada.

Para solucionar o problema, o projeto visa oferecer à lanchonete um sistema de autoatendimento de fast food que permite
aos clientes selecionar e fazer pedidos sem precisar interagir com um atendente.

## Objetivos

Este serviço inclui as seguintes funcionalidades:

- Realizar pagamento PIX via Mercado Pago
- Webhook e confirmação pagamento

## 💻 Pré-requisitos

Antes de começar, verifique se você atendeu aos seguintes requisitos:

* Você tem uma máquina `<Windows / Linux / Mac>`.
* Docker
* Postman
* Jdk 17 ou superior

## Como iniciar o projeto localmente

Execute o docker compose para subir o banco de dados:

```bash
docker-compose up -d
```

Crie a tabela no dynamo:

```bash
aws dynamodb create-table \
    --table-name tb_pagamento \
    --attribute-definitions AttributeName=id,AttributeType=S \
    --key-schema AttributeName=id,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
    --endpoint-url http://localhost:8000
```

Comando para listar as tabelas:

```bash
aws dynamodb list-tables --endpoint-url http://localhost:8000
```

Comando para scan na tabela criada:

```bash
aws dynamodb scan --table-name tb_pagamento --endpoint-url http://localhost:8000
```

Variáveis de ambiente:

| Variável                 | Descrição                                       |
|--------------------------|-------------------------------------------------|
| `SPRING_PROFILES_ACTIVE` | Perfil ativo do Spring para definir o ambiente. |
| `WEBHOOK_PATH`           | Caminho da URL do webhook.                      |

## Como executar o Cucumber localmente

1. Execute o docker compose e crie a tabela no dynamo;
2. Suba o serviço em um console;
3. Em outro execute o CucumberRunnerTest.

## Collection

Acesse a [**collection**](assets/collection/API_PAGAMENTO.postman_collection.json) do Postman com todas as APIs desenvolvidas.

## Guia instrutivo

Acesse o [**guia**](assets/collection/guia.md) para execução das APIs.