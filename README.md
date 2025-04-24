Endpoints

POST /users
Recebe e armazena os usuários na memória. Pode simular um banco de dados em memória.

GET /superusers
Filtro: score >= 900 e active = true
Retorna os dados e o tempo de processamento da requisição.
GET /top-countries
Agrupa os superusuários por país.
Retorna os 5 países com maior número de superusuários.
GET /team-insights
Agrupa por team.name.
Retorna: total de membros, líderes, projetos concluídos e % de membros ativos.
GET /active-users-per-day
Conta quantos logins aconteceram por data.
Query param opcional: ?min=3000 para filtrar dias com pelo menos 3.000 logins.
GET /evaluation
Ele deve executar uma autoavaliação dos principais endpoints da API e retornar um relatório de pontuação.

A avaliação deve testar:

Se o status retornado é 200
O tempo em milisegundos de resposta
Se o retorno é um JSON válido
Esse endpoint pode rodar scripts de teste embutidos no próprio projeto e retornar um JSON com os resultados. Ele será utilizado para validar a entrega de forma automática e rápida.

Requisitos Técnicos
Tempo de resposta < 1s por endpoint.
Todos os endpoints precisam retornar o tempo de processamento (em milissegundos) e a timestamp da requisição
Código limpo, modular, com funções bem definidas.
Pode usar qualquer linguagem/framework.
Documentação ou explicação final vale pontos bônus.
Não pode usar IA.
