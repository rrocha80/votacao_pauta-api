# votacao_pauta-api

## Tecnologias Utilizadas

- Java 21
- Spring Boot 4
- Maven
- Docker
- MySql 8

## Rodar o projeto

1. Via Docker: execute o docker-compose para iniciar o Projeto;
2. Via IDE: sete as variáveis de referente a URL, usuário e senha do banco de dados MySql e execute a classe `VotacaoPautaApiApplication`.

## Swagger
- http://localhost:8080/swagger-ui/index.html
- http://localhost:8080/v3/api-docs

## Utilização
1. crie uma pauta;
2. crie uma sessão de votação para a pauta criada;
3. vote na pauta usando um dos associados com o status ABLE_TO_VOTE (Habilitado para votar);
4. o resultado final da pauta só estará disponível após o término da sessão de votação, não pode ter sessão ativa.

Obs: Existe um parâmetro no "application.properties" chamado "usar-associado-remoto" que define se vai usar a chamada remota para o serviço de associados, caso seja false vai usar a entidade associado.
Criei a entidade associado com 30 CPF's a maioria ABLE_TO_VOTE, mas tem alguns UNABLE_TO_VOTE (Não habilitado para votar) para testar a API.
