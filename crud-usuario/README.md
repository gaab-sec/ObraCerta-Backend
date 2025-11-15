CRUD de Usuários com Spring Boot e Spring Security
Este é um projeto de API RESTful completo para gerenciamento de usuários, incluindo cadastro, login e operações CRUD (Create, Read, Update, Delete). A aplicação é construída em Java com Spring Boot e segue as melhores práticas de arquitetura MVC e segurança de API.

🚀 Funcionalidades
Cadastro de Usuários: Criação de novos usuários com nome, e-mail e senha.

Autenticação de Usuários: Endpoint de login (/login) que utiliza Spring Security para autenticar e criar uma sessão de usuário.

Hashing de Senhas: As senhas são armazenadas de forma segura no banco de dados usando BCrypt.

CRUD Completo:

Create: POST /api/usuarios/cadastro

Read: GET /api/usuarios (Listar todos) e GET /api/usuarios/{id} (Buscar por ID)

Update: PUT /api/usuarios/{id}

Delete: DELETE /api/usuarios/{id}

Autorização: Todos os endpoints CRUD (exceto Cadastro e Login) são protegidos e só podem ser acessados por usuários autenticados.

DTO (Data Transfer Object): Otimização das requisições e respostas, garantindo que a senha do usuário nunca seja exposta nos retornos da API.

🛠️ Tecnologias Utilizadas
Este projeto foi construído com as seguintes tecnologias:

Java 17+

Spring Boot 3+

Spring Security: Para autenticação baseada em sessão e hashing de senhas.

Spring Data JPA: Para persistência de dados e abstração de queries.

H2 Database: Um banco de dados em memória, ideal para desenvolvimento e testes.

Maven: Gerenciador de dependências e build do projeto.

📂 Estrutura do Projeto
O projeto segue o padrão de arquitetura MVC (Model-View-Controller), adaptado para APIs REST, com uma clara separação de responsabilidades:

model: Contém as entidades JPA (ex: Usuario).

repository: Contém as interfaces do Spring Data JPA (ex: UsuarioRepository).

service: Contém a lógica de negócios da aplicação (ex: UsuarioService, AutenticacaoService).

controller: Recebe as requisições HTTP e direciona para os serviços (ex: UsuarioController).

dto: Contém os Data Transfer Objects para validar entradas (UsuarioCadastroDTO) e formatar saídas (UsuarioResponseDTO).

config: Contém as configurações de segurança (ex: SecurityConfig).

⚙️ Como Executar o Projeto
Siga os passos abaixo para executar a aplicação localmente.

Pré-requisitos
JDK 17 ou superior (Java Development Kit)

Apache Maven

Um cliente de API, como o Postman

Configuração
Clone o repositório:

Bash

git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
Crie o arquivo de propriedades: Na pasta src/main/resources/, renomeie o arquivo application.properties.example para application.properties.

Configure o application.properties: Preencha as informações do banco H2 (embora as padrões do arquivo example já devam funcionar).

Properties

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password 

spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
Executando
Navegue até a raiz do projeto (onde está o pom.xml).

Execute o build com o Maven:

Bash

mvn clean install
Execute a aplicação:

Bash

java -jar target/crud-usuario-0.0.1-SNAPSHOT.jar 
A API estará rodando em http://localhost:8080.

🧪 Testando com o Postman
Para testar os endpoints protegidos (GET, PUT, DELETE), você deve primeiro Cadastrar um usuário e depois fazer Login para obter um cookie de sessão.

Fluxo de Teste (Obrigatório)
Passo 1: Cadastrar um Usuário

Método: POST

URL: http://localhost:8080/api/usuarios/cadastro

Body (raw/JSON):

JSON

{
    "nome": "Seu Nome",
    "email": "teste@email.com",
    "senha": "senha123"
}
Resposta: 201 Created

Passo 2: Fazer Login

Método: POST

URL: http://localhost:8080/api/usuarios/login

Body (raw/JSON):

JSON

{
    "email": "teste@email.com",
    "senha": "senha123"
}
Resposta: 200 OK. O Postman irá salvar automaticamente o cookie de sessão (JSESSIONID).

Passo 3: Testar Endpoints Protegidos Agora você pode acessar os outros endpoints:

Listar Todos (GET): http://localhost:8080/api/usuarios

Buscar por ID (GET): http://localhost:8080/api/usuarios/1

Atualizar (PUT): http://localhost:8080/api/usuarios/1

Body (raw/JSON):

JSON

{
    "nome": "Novo Nome",
    "email": "novo.email@email.com"
}
Deletar (DELETE): http://localhost:8080/api/usuarios/1 (Body: none)