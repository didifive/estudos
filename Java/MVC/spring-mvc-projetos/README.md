# Spring MVC - Projetos

Sistema criado com criado com SpringBoot 2.7.0, Java 8, Maven 3.8.4 para gerenciar as dependências e banco de dados MySQL.

Cadastros de Linguagens, Desenvolvedor e Projetos, relacionando desenvonvedor com linguagem e projeto com linguagem, podendo incluir e remover desenvolvedores em projetos.

## Instalação
O projeto foi feito com IDE Eclipse, para abrir o ideal é utilizar a mesma IDE.
_Deve possuir servidor do MySQL e fazer as configurações em aplications.properties._


## Testes
Para rodar os testes basta executar na pasta do projeto com terminal PowerShell:
```powershell
.\mvnw clean test
```
Após o teste finalizado, é possível verificar relatório de coverage em: target/site/jacoco/index.html
