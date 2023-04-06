Tabela de conteúdos
=================
<!--ts-->
* [Descrição do projeto](#descrição-do-projeto)
* [Pré-requisitos](#-pré-requisitos)
* [Tecnologias](#-tecnologias)
* [Iniciando o uso deste projeto](#-iniciando-o-uso-deste-projeto)
    * [Clone este repositório](#clone-este-repositório)
    * [Acesse a pasta do projeto no terminal/cmd](#acesse-a-pasta-do-projeto-no-terminalcmd)
    * [Instale as dependências](#instale-as-dependências)
    * [Gere o pacote executável](#gere-o-pacote-executável)
    * [Execute a aplicação](#execute-a-aplicação)
* [Arquitetura do projeto](#-arquitetura-do-projeto)
* [Banco de dados](#-banco-de-dados)
  * [Instalando o banco de dados PostgresSQL](#instalando-o-banco-de-dados-postgressql)
  * [Executando o banco de dados com docker](#executando-o-banco-de-dados-com-docker)
  * [Cliente para gerenciamento do banco de dados PostgresSQL](#cliente-para-gerenciamento-do-banco-de-dados-postgressql)
* [Migrations com Flyway](#migrations-com-flyway)
  * [Instalar Flyway](#instalar-flyway)
  * [Padrão de nome de arquivo de migração Flyway](#padrão-de-nome-de-arquivo-de-migração-flyway)
  * [Criando uma migration para o Flyway](#criando-uma-migration-para-o-flyway)
  * [Executando uma migration](#executando-uma-migration)
  * [Revertendo uma migration](#revertendo-uma-migration)
* [Sobre padrões no versionamento do código](#sobre-padrões-no-versionamento-do-código)
<!--te-->

# Descrição do Projeto
Projeto template base para novas solução de  API REST com as seguintes pré-configurações:
- Arquitetura em 3 camadas
- Docker para a aplicação
- Docker para o banco de dados com PostgreSQL
- Consultas com ORM configuradas
- Open API docs com Swagger
- Migrations com Flyway
- Spring Boot

# 👍 Pré-requisitos

Como pré requisitos temos os seguintes itens:
- Java 11 ou superior
- Docker
- Maven
- Flyway

# 🛠 Tecnologias

As seguintes ferramentas foram usadas na construção do projeto:

- [JDK 11 Releases](https://jdk.java.net/11/)
- [Flyway](https://flywaydb.org/)
- [Docker](https://docs.docker.com/)
- [Spring Boot](https://spring.io/)
- [Maven](https://maven.apache.org/what-is-maven.html)

# 🎬 Iniciando o uso deste projeto

### Clone este repositório
```bash
git clone git@github.com:dbserver/dbcamp-template-api.git
```

### Acesse a pasta do projeto no terminal/cmd
```bash
cd dbcamp-template-api
```

### Instale as dependências
```bash
mvn install:install-file -Dfile=./pom.xml -DpomFile=./pom.xml
```

### Gere o pacote executável
```bash
mvn clean package
```

### Execute a aplicação
```bash
java -jar target/template-0.0.1-SNAPSHOT.jar
```

### O servidor inciará na porta:4767 - acesse <http://localhost:4767>

# Acesso ao Open API docs (Swagger)

Para acessar o [Open APi (swagger)](https://swagger.io/specification/) da aplicação acesso o link abaixo;
```
http://localhost:4767/swagger-ui/index.html
```

Para acessar o APi docs (json file) da aplicação acesso o link abaixo;
```
http://localhost:4767/v3/api-docs
```


# 📁 Arquitetura do projeto

A arquitetura do projeto da-se com uma divisão em 3 camadas com responsabilidades distintas:

![img_1.png](docs/images/img_1.png)

## Camada de apresentação (Presentation)
Mantenha essa camada o mais fina possível e limitada à mecânica das operações MVC, por exemplo, recebendo e validando as entradas, manipulando o objeto modelo, retornando o objeto MovedAndView apropriado e assim por diante. Todas as operações relacionadas ao negócio devem ser feitas em classes de serviço. As classes do controlador geralmente são colocadas em um pacote do controlador.

## Camada de lógica de negócios (Business Logic)
Cálculos, transformações de dados, processos de dados e validações entre registros (regras de negócios) geralmente são feitos nessa camada. Eles são chamados pelas classes do controlador e podem chamar repositórios ou outros serviços. As classes de serviço geralmente são colocadas em um pacote de serviço.

## Camada de acesso a dados (Data)
A responsabilidade dessa camada é limitada às operações Criar, Recuperar, Atualizar e Excluir (CRUD) em uma fonte de dados, que geralmente é um banco de dados relacional ou não relacional. As classes de repositório geralmente são colocadas em um pacote de repositório.


# 🏬 Banco de dados

## Instalando o banco de dados PostgresSQL

- Abra uma nova janela de comando e execute o comando abaixo.

 
```bash
# Para sistemas unix
docker pull postgres
```

```bash
# Para sistemas windows
docker pull stellirin/postgres-windows
```

- Verificando se a o download da imagem foi realizado com sucesso.
```bash
docker images
```
- Verifique se a imagem do postgres aparece conforme abaixo

Para **sistemas unix** deverá aparecer algo parecido como o abaixo
```
postgres      latest    80c558ffdc31   7 days ago   379MB
```
Para **sistemas windows** deverá aparecer algo parecido como o abaixo
```
stellirin/postgres-windows      latest    80c558ffdc31   7 days ago   379MB
```

## Executando o banco de dados com docker

OBS: altere o <nome-da-imagem> de acordo com o seu sistema operacional
- Unix: postgres
- Windows: stellirin/postgres-windows

```bash
docker run --name postgresql -e POSTGRES_USER=templateuser -e POSTGRES_PASSWORD=templatepassword -p 5432:5432 -d <nome-da-imagem>
```
**No comando dado acima,**

- PostgreSQL é o nome do Docker Container.
 - -e POSTGRES_USER é o parâmetro que define um nome de usuário exclusivo para o banco de dados Postgres.
 - -e POSTGRES_PASSWORD é o parâmetro que permite definir a senha do banco de dados Postgres.
- -p 5432:5432 é o parâmetro que estabelece uma conexão entre a porta do host e a porta do contêiner do Docker. Nesse caso, ambas as portas são fornecidas como 5432, o que indica que as solicitações enviadas às portas do host serão redirecionadas automaticamente para a porta do contêiner do Docker. Além disso, 5432 também é a mesma porta onde o PostgreSQL estará aceitando requisições do cliente.
- -d  é o parâmetro que executa o Docker Container no modo desanexado, ou seja, em segundo plano. Se você acidentalmente fechar ou encerrar o Prompt de Comando, o Docker Container ainda será executado em segundo plano.
- <nome-da-imagem> é o nome da imagem do Docker que foi baixada anteriormente para executar o Docker Container.

**Para validar se o banco de dados foi executado com sucesso execute o comando abaixo**
- 
```bash
 docker ps --filter "name=postgresql"
```
**Deve ser exibido algo similar o abaixo**
```
CONTAINER ID   IMAGE      COMMAND                  CREATED         STATUS         PORTS                    NAMES
f33be708db53   postgres   "docker-entrypoint.s…"   4 minutes ago   Up 4 minutes   0.0.0.0:5432->5432/tcp   postgresql
```

## Cliente para gerenciamento do banco de dados PostgresSQL
Para gerenciar, executar scripts sugerimos a utilização do [DBeaver](https://dbeaver.io/download/)

## Migrations com Flyway

### Instalar Flyway

- Realize a instalação do flyway de acordo com o seu sistema operacional [nesse link aqui](https://flywaydb.org/download/community)
- Verifique se o comando flyway é reconhecido no terminal executando 
```bash
flyway -v
```
**Observações importantes:**
- O Flyway vem desabilitado para auto migrations por padrão, logo você terá que realizar as migrations manualmente
- As migrações pendentes em uma única transação em vez de uma transação por migrações pendentes. Portanto, se uma migração falhar, todas as migrações executadas antes serão revertidas.

### Padrão de nome de arquivo de migração Flyway
Já existe uma pasta criada onde colocar o arquivo de migração; está localizado em resources/db/migration. No entanto, o nome do arquivo deve seguir um padrão específico:

![img.png](docs/images/img.png)
- Parte 1: É a letra "v" em maiúscula. O nome sempre começa com esta letra.
- Parte 2: É a versão de migração; pode ser 1, 001, 1.2.3, 2021.09.24.12.55.32, ... você entendeu.
- Parte 3: São os dois sublinhados (_)
- Parte 4: A descrição da migração; você pode separar as palavras com um sublinhado ou um espaço.
- Parte 5: É a extensão do arquivo.sql

### Criando uma migration para o Flyway

Para criar arquivos de migrations siga os passos abaixo
- Crie um arquivo de migration com o padrão de nome descrito acima dentro do caminmho resources/db/migration

### Executando uma migration
- Execute o comando abaixo para executar as migrações
```bash
flyway migrate -configFiles=flyway.properties
```
**Observação: você deve estar no diretório do projeto para executar o comando.**

### Revertendo uma migration
Pode acontecer que você queira reverter uma migração; execute o comando abaixo:
```bash
flyway undo -configFiles=flyway.properties
```
# Sobre padrões no versionamento do código

É desejado que seja utilizado o padrão de Commits Semânticos. Pode entender melhor [nesse link](https://github.com/iuricode/padroes-de-commits)
