![GitHub last commit](https://img.shields.io/github/last-commit/cironeto/to-do-spring?style=flat-square)
![GitHub top language](https://img.shields.io/github/languages/top/cironeto/to-do-spring?style=flat-square)
# Desafio Backend – Java / Spring Boot

## Sobre
Esse projeto foi desenvolvido para o Desafio Backend – Java / Spring Boot.
O objetivo é implementar uma solução de gerenciamento de terefas (TO-DO).
Para isso foi desenvolvido uma API REST com Spring Boot.


O sistema contém as seguintes funcionalidades:

    • CRUD de Usuários
    • CRUD de Tarefas / Completar uma tarefa / Verificar tarefas pendentes
    • Autenticação do usuário via JWT
    • Documentação via Swagger

## Ferramentas utilizadas
- Java 17
- SQL Server
- Spring Boot 3.1.0
- Spring Data JPA
- Spring Security / JWT
- Spring Validation
- Spring Doc / OpenAPI
- JUnit


## Requisitos
Para execução deste projeto é necessário ter instalado:
- JDK 17
- SQL Server ou Docker
- Maven

## Execução
### Bando de dados:
**SQL Server instalado localmente:** execute os scripts iniciais da criação do bando de dados existentes no arquivo **init.sql**. A aplicação irá se conectar ao banco pela porta 1433.

**Docker:** abra um terminal em **'.../to-do-spring'** e execute para a criação de uma instância do SQL SERVER:
```sh
docker-compose up
```
```sh
SQL SERVER LOGIN
porta: 1433
username: sa
password: DB@password123
```
Em seguida execute os scripts iniciais da criação do bando de dados existentes no arquivo init.sql.

### Aplicação:
Execute via IDE ou abra o terminal em '.../to-do-spring' e execute o comando:
```sh
mvn clean install
```
Em seguida, execute a aplicação:

```sh
mvn spring-boot:run
```


Ao executar, a aplicação estará disponível em **http://localhost:8080**

## REST API Endpoints
Com a aplicação rodando, acesse a documentação via Swagger para testar as requisições de autenticação e dos recursos:
**http://localhost:8080/swagger-ui/index.html**


<br><br>
Desenvolvido por Ciro Neto
<div> 
<a href="https://api.whatsapp.com/send?phone=5519992582741" target="_blank"><img src="https://img.shields.io/badge/WhatsApp-25D366?style=for-the-badge&logo=whatsapp&logoColor=white" target="_blank"></a> 
<a href="https://www.linkedin.com/in/cironeto/" target="_blank"><img src="https://img.shields.io/badge/-LinkedIn-%230077B5?style=for-the-badge&logo=linkedin&logoColor=white" target="_blank"></a> 
<a href = "mailto:ciro.neto16@gmail.com"><img src="https://img.shields.io/badge/-Gmail-%23333?style=for-the-badge&logo=gmail&logoColor=white" target="_blank"></a>
</div>
