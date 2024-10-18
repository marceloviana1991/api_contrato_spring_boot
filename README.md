# Projeto de API de Sistema de Gerenciamento de Contrato de Aluguel de Imóveis

## Dados do projeto

- **Linguagem:** Java versão 17
- **Framework:** Spring Boot 3.3.4
- **Gerenciador de Dependências:** Maven
- **SGBD:** MySQL
- **API externa:** viacep

## Levantamento de requisitos

 Um escritório solicita um aplicação que auxilie no gerenciamento de contratos de alugueis de imóveis. O objetivo é que a aplicação seja capaz de cadastrar os imóveis que são gerenciados pelo escritório, administrar os contratos de aluguel e construir um registro de despesas provenientes de manutenção.
 
 Cada registro deve conter:
 
(A) Imóvel
- Tipo (informa se o imóvel é casa, apartamento, galpão, sala comercial ou terreno);
- Localização (informa bairro, rua, número, cidade e CEP).

(B) Aluguel
- Data de início;
- Data de finalização;
- Valor de mensalidade;
- Valor de IPTU.

(C) Despesas
- Data;
- Valor;
- Descrição.

 O escritório relata que o maior desafio que tem enfrentado ultimamente é a grande quantidade de imóveis registrados com erros nos dados de localização.
 
## Solução escolhida

- A solução escolhida será a utilização de uma API Rest que se conecta a um banco de dados para implementar a persistência de dados. 
- Para garantir a gestão de segurança da informação será implementado um sistema simplificado de autenticação e permissão de acesso utilizando token JWT.
- Notou-se que para solucionar o problema de dados registrados com erro somente a utilização de um sistema de validação seria insuficiente e foi decidido utilizar uma API externa que retorna dados de localização de um imóvel a partir de um CEP.

![Consome API via cep](imagens/consome_api_viacep.png)

## Modelagem de Dados

A estrutura da aplicação gira em torno de 3 entidades:
- Imóvel: entidade forte que possuí atributo composto localização e atributo simples tipo;
- Contrato de Aluguel: entidade fraca que possui atributos data de início, date de finalização, valor de IPTU e valor de mensalidade;
- Despesas com Manutenção: entidade fraca que possui atributos data, descrição e valor.

A dinâmica das entidades gira em tordo de dois relacionamentos:
- Imóvel possui contrato de aluguel
- Imóvel faz despesa com manutenção

Cardinalidade mínima dos relacionamentos:
- A existência do imóvel não depende da existência de um contrato
- A existência do imóvel não depende da existência de uma despesa
- A existência do contrato depende da existência de um imóvel
- A existência da despesa depende da existência de um imóvel

Cardinalidade máxima dos relacionamentos:
- Um imóvel possui n contratos
- Um imóvel faz n despesas
- Um contrato só pode ser de um único imóvel
- Uma despesa só pode ser de um único imóvel

![Modelo Conceitual](imagens/modelo_conceitual_contratos.jpg)

A construção do modelo lógico definiu que:
- Todas as entidades recebem uma chave primaria id;
- As entidades contrato de aluguel e despesas com manutenção recebem chave estrangeira id_imovel;
- O atributo composto localização da entidade imóvel foi dividido em 5 atributos bairro, rua, número, cidade e cep;

![Modelo Lógico](imagens/modelo_logico_contratos.jpg)

Apesar da regra de negócio impor que um imóvel não pode ter dois contratos diferentes em um mesmo intervalo de tempo, isso não impede a construção de um relacionamento de muitos para muitos entre as entidades imóvel e contrato de aluguel. Para atender esse demanda utiliza-se um serviço que verifica as datas de início e finalização do contrato no momento do cadastro do contrato de aluguel.

A modelagem de dados opta por não representar a entidade usuário. Essa decisão foi tomada porque essa entidade não se relaciona com nenhuma das três entidades do sistema de gerenciamento. Apesar da entidade não ser representada nos modelos conceitual e lógico, no modelo físico cria-se uma tabela que persiste os dados utilizados na autenticação no sistema de autenticação.

## Projeto de criação da aplicação

### 1. Criação do projeto Spring Boot

Nessa etapa cria-se o projeto Spring Boot, adiciona-se as dependências principais e configura-se a conexão com o banco de dados.

Ponteiros utilizados no arquivo src/main/resources/application.properties:
- ${DB_HOST}: Deve apontar para endereço IP do host e a porta onde o SGBD está rodando;
- ${DB_NAME}: Deve apontar para nome do banco de dados que foi criado no SGBD;
- ${DB_USER}: Deve apontar para login do usuário do SGBD;
- ${DB_PASSWORD}: Deve apontar para a senha do usuário do SGBD;
- ${JWT_SECRET}: Deve apontar para a secret key que foi criada para o token service.

Antes de inicializar a API, deve ser feita criação do banco de dados, usuário do SGBD e variáveis de ambiente.

Para testar os configurações do projeto, nessa etapa foi feita uma inserção no banco de dados. Apesar de no projeto já existir classes controller, model, DTO, service e repository (DAO), referentes a entidade imóveis, o sistema de cadastro de imóveis só será criado na próxima etapa do projeto. O foco dessa etapa é a criação do projeto Spring Boot, configuração de dependências principais e a conexão com o banco de dados. 

### 2. Criação do sistema de cadastro de imóveis

Nessa etapa cria-se o CRUD da entidade imóveis com regras de validação. Para a obtenção dos endereços utiliza-se uma API externa chamada viacep. A API externa viacep atua, de modo que, recebe um cep e devolve os dados do endereço. Por tanto, definiu-se que no método post do cadastro de imóveis seria passado somente o tipo do imóvel, o cep e o número. A partir daí, obtém-se os dados de bairro, rua, cidade e estado por meio da API externa. Vale ressaltar que para viabilizar a manipulação dos dados obtidos por meio da API externa necessita-se da utilização de biblioteca de serialização. A biblioteca de serialização tem a função de converter os dados obtidos por meio da API externa para um DTO java.

**(A) Método POST imóveis**

Parâmetros BodyRequest
- tipoImovel (EnumType.STRING)
- cep (String)
- numero (String)

O cep é utilizado como PathVariable na requisição enviada para a API externa.

Parâmetros BodyResponse
- id (Long)
- tipoImovel (EnumType.STRING)
- bairro (String)
- rua (String)
- numero (String)
- cidade (String)
- estado (String)
- cep (String)

Regras de validação
- **NotBlank:** cep e numero
- **NotNull:** tipoImovel
- **Regex:** cep (8 dígitos de número decimal)

**(B) Método GET imóveis**

PathVariable
- id (opcional)

Utilizando PathVariable chama o método detalhar (retorna objeto) e sem utilizar PathVariable chama método listar (retorna lista).

No método listar utiliza-se componente de paginação.

QueryParameters
- size (quantidade de itens por página)
- page (número da página)
- sort (ordenação)

Para facilitar as pesquisas, o parâmetro sort (ordenação) é definido por padrão com ordenação respectivamente por nome de bairro, rua e número.

Parâmetros BodyResponse
- id (Long)
- tipoImovel (EnumType.STRING)
- bairro (String)
- rua (String)
- numero (String)
- cidade (String)
- estado (String)
- cep (String)

**(C) Método PUT imóveis**

Parâmetros BodyRequest
- id (Long)
- tipoImovel (EnumType.STRING)
- bairro (String)
- rua (String)
- numero (String)
- cidade (String)
- estado (String)
- cep (String)

Regras de validação
- **NotNull:** id

Caso de parâmetros não passados no BodyRequest o atributo permanece inalterado. 

Parâmetros BodyResponse
- id (Long)
- tipoImovel (EnumType.STRING)
- bairro (String)
- rua (String)
- numero (String)
- cidade (String)
- estado (String)
- cep (String)

**(D) Método DELETE imóveis**

PathVariable
- id

O método delete implementa conceito de exclusão lógica por meio de atributo denominado ativo que recebe variável do tipo boolean. Além de ter a função de definir a exclusão da própria entidade imóvel, por meio de uma reação em cadeia, o atributo também tem a função de excluir as entidades dependentes da entidade imóvel, funcionando como algo semelhante a uma exclusão em cascata.

### 3. Criação do sistema de cadastro contratos

Nessa etapa cria-se o CRUD da entidade contratos de aluguel com regras de validação.

**(A) Método POST contratos**

Parâmetros BodyRequest
- dataInicio (LocalDate "yyyy-MM-dd")
- dataFinalizacao (LocalDate "yyyy-MM-dd")
- mensalidade (Integer)
- iptu (Integer)
- idImovel (Long)

Parâmetros BodyResponse
- id (Long)
- dataInicio (LocalDate "yyyy-MM-dd")
- dataFinalizacao (LocalDate "yyyy-MM-dd")
- mensalidade (Integer)
- iptu (Integer)
- idImovel (Long)

Regras de validação
- **NotNull:** Todos atributos

**(B) Método GET contratos**

PathVariable
- id (opcional)

Utilizando PathVariable chama o método detalhar (retorna objeto) e sem utilizar PathVariable chama método listar (retorna lista).

No método listar utiliza-se componente de paginação.

QueryParameters
- size (quantidade de itens por página)
- page (número da página)
- sort (ordenação)

Para facilitar as pesquisas, o parâmetro sort (ordenação) é definido por padrão com ordenação respectivamente por data de inicio e data de finalização.

Parâmetros BodyResponse
- id (Long)
- dataInico (LocalDate "yyyy-MM-dd")
- dataFinalizacao (LocalDate "yyyy-MM-dd")
- mensalidade (Integer)
- iptu (Integer)
- idImovel (Long)

**(C) Método PUT contratos**

Parâmetros BodyRequest
- id (Long)
- dataInicio (LocalDate "yyyy-MM-dd")
- dataFinalizacao (LocalDate "yyyy-MM-dd")
- mensalidade (Integer)
- iptu (Integer)

Regras de validação
- **NotNull:** id

Caso de parâmetros não passados no BodyRequest o atributo permanece inalterado.

Parâmetros BodyResponse
- id (Long)
- dataInicio (LocalDate "yyyy-MM-dd")
- dataFinalizacao (LocalDate "yyyy-MM-dd")
- mensalidade (Integer)
- iptu (Integer)
- idImovel (Long)




