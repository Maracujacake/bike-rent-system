# bike-rent-system
Sistema de locação de bicicletas.

http://localhost:8080/bikes-rent/
execute com mvn clean package tomcat7:run-war

# to-dos:

- ## organizar o código
- - organizar em clienteView somente as páginas que cliente pode acessar
- - criar um adminController com as funções antigas de cliente, afinal, cliente não pode ver informações de outros clientes, apenas admin pode
- - esse adminController deve possuir as funções de locadora também que não devem ser acessíveis a locadora, ex: ver as locações de outras locadoras
- - retirar o código antigo de clienteController e deixar somente no adminController

<hr>

- ## fixes
- - cliente não deve passar cpf para busca de locações, ele pode acabar passando o cpf de outro cliente e ver as locações de outra pessoa
- - ajustar as traduções nas paginas .jsp para poder fazer o merge com a main // faz um pull da branch main, pega arquivo de traduções e substitui as páginas

<hr>

- ## implementação
- - adminController
- - filtro para admin e locadora
- - página de login para admin e locadora (pode ser o mesmo estilo para página de login de cliente, porém, deve redirecionar para o filtro/controller de admin e/ou locadora)
- - na página inicial, opção para ver locadoras e locadoras por cidade é pública
- - deve haver um botão para locadora fazer login, além dos outros dois acima ^
- - página de erros ( horário não é cheio, tentativa de criar uma locação sem cliente existente ou locadora existente (precisa de ambos), etc )
- - ao criar locação (ao inserir no banco de dados com sucesso), enviar arquivo pdf ao email do cliente e locadora
