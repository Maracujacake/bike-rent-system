# bike-rent-system

Sistema de locação de bicicletas.

http://localhost:8080/bikes-rent/
execute com mvn clean package tomcat7:run-war

# to-dos:

- ## organizar o código
- - criar um arquivo .env para o bd porque o tanso do chris não usa root root como login, mds

<hr>

- ## fixes
- - Mover algumas funções de locação para admin. Obrigar login para acessar locacoes
- - Revisar arquivos apos merges
<hr>

- ## implementação
- - filtro para locadora (locadora pode ver quais locações estão em seu cnpj)
- - página de login para locadora (pode ser o mesmo estilo para página de login de cliente, porém, deve redirecionar para o filtro/controller locadora)
- - deve haver um botão para locadora fazer login
- - página de erros ( horário não é cheio, tentativa de criar uma locação sem cliente existente ou locadora existente (precisa de ambos), etc )
- - ao criar locação (ao inserir no banco de dados com sucesso), enviar arquivo pdf ao email do cliente e locadora
