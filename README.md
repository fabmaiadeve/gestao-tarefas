# Projeto: gestao-tarefas
![Java](https://img.shields.io/badge/Java-17-blue?style=plastic&logo=java)  ![Spring](https://img.shields.io/badge/Spring-3.4.0-green?style=plastic&logo=spring)  ![](https://img.shields.io/badge/Maven-orange)  ![Developer](https://img.shields.io/static/v1?label=Dev&message=Fab&color=456a8f&style=plastic&logo=librewolf)  ![Msg](https://img.shields.io/badge/Nada%20é%20permanente,%20exceto%20a%20mudança.-456a8f)

## Índice

1. [Descrição](#descrição) 

2. [Execução Local](#execução-local)

3. [Acessando Aplicação](#acessando-aplicação)

## Descrição
- Este é um projeto desafio proposto pela Solutis que realiza uma implementação de uma API de gestão de tarefas.


## Execução Local
- É possível subir a aplicação utilizando o próprio Maven. É útil para testes de desenvolvimento onde é necessário uma agilidade maior

- No diretório root do projeto executar:

```bash

mvn spring-boot:run

```
- Ou então, executar o arquivo 'GestaoTarefasApplication.java' na sua IDE preferida.   

## Acessando Aplicação
- A aplicação irá subir na porta 8080 nos seguintes end-points: 

(tipo GET) Retorna todas as tarefa ==> http://localhost:8080/api/tarefas

(tipo GET) Retorna uma tarefa por id ==> http://localhost:8080/api/tarefas/{id}

(tipo POST) Cria uma nova tarefa ==> http://localhost:8080/api/tarefas

(tipo PUT) Atualiza o status de uma tarefa existente ==> http://localhost:8080/api/tarefas/{id}

(tipo DELETE) Exclui uma tarefa existente ==> http://localhost:8080/api/tarefas/{id}
