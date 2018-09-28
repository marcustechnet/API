# API Address

## Technologies
```
Java 8
Spring Framework 4
Spring Boot 2.0
PostgreSQL
```

## API Documentation
http://localhost:8083/swagger-ui.html

## Postgres scripts

**creating base**
```
CREATE TABLE endereco
(
  id serial NOT NULL,
  rua text NOT NULL,
  numero integer NOT NULL,
  cep character varying(9) NOT NULL,
  cidade character varying(50) NOT NULL,
  estado character varying(50) NOT NULL,
  bairro character varying(50),
  complemento character varying(50),
  CONSTRAINT endereco_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE endereco
  OWNER TO postgres;
```

## Command line tips

**using a specific timezone**
```
-Duser.timezone=UTC -Duser.country=BR -Duser.language=pt 
```

**compile and run**
```
mvn clean package -Dmaven.test.skip=true && java -Duser.timezone=UTC -Duser.country=BR -Duser.language=pt -jar target/api-address-0.1.0.jar
```

## Estratégia

Foi desenvolvida uma aplicação RESTFul (API), que por sua vez tem como base a arquitetura REST. Para tal foi utilizado Spring MVC, e Spring Data JPA com Hibernate para persistência de dados. Os dados são armazenados no banco relacional PostgreSQL.
```  
Como estratégia para uma configuração inicial eficiente e ágil, foi utilizado o Spring Boot com as dependências necessárias para o melhor desenvolvimento da API.
```
Foram desenvolvidos testes tanto dos serviços da interface quanto da camada Controller. Além disso é possível consumir/testar a API através do Swagger (que também é uma documentação).

