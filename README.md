# bike-rent-system

Sistema de locação de bicicletas.

http://localhost:8080/bikes-rent/
execute com mvn clean package tomcat7:run-war

# Configurar .env
    - Criar arquivo .env dentro da pasta bikes-rent
    - Adicionar campos *DB_USER*, *DB_PASSWORD*

# to-dos:

- ## organizar o código

<hr>

- ## fixes
- - Mover algumas funções de locação para admin. Obrigar login para acessar locacoes
- - Revisar arquivos apos merges
- - Ao adicionar uma locação como cliente existem dois problemas: 
    - Nao é adicionado. V
    - É redirecionado para outra pagina, deveria continuar na parte de cliente/ V
- - Como usuario eu consigo adicionar uma nova locadora


<hr>

- ## implementação
- - filtro para locadora (locadora pode ver quais locações estão em seu cnpj) V
- - página de login para locadora (pode ser o mesmo estilo para página de login de cliente, porém, deve redirecionar para o filtro/controller locadora) V
- - deve haver um botão para locadora fazer login V
- - página de erros ( horário não é cheio, tentativa de criar uma locação sem cliente existente ou locadora existente (precisa de ambos), etc )
- - Melhorar tratamento de erros, como senha, usuario invalidos ou usuario sem permissão etc.
- - ao criar locação (ao inserir no banco de dados com sucesso), enviar arquivo pdf ao email do cliente e locadora
- - Crud de clientes

- ## Duvidas
- - Locadoras tem apenas uma bicicleta ?
- - Usuário comum nao poderia criar locadoras? Ex: eu quero incluir minha locadora

