# SCC0103 - Trabaho 3 - Aplicativo de Biblioteca

# Sumario

* [Introducao](#introducao)
* [Comandos e Execucao](#comando)
* [Contribuidores](#contribuidores)
* [Copyright](#copyright)

* * *

<h2 id="introducao">Introducao</h2>

 Aplicativo de Biblioteca implementado no formato de uma Shell interativa.

 Funcionalidades:

    + Cadastro de Usuarios e Livros: pelo nome e titulo, respectivamente.
    + Cadastro de Emprestimo: associa um titulo ao nome de um usuario e faz o emprestimo.
    + Validacao de Emprestimo: verifica se o usuario e livro sao validos para emprestimo
    + Verifica os atrasos: analisa os emprestimos e procura os emprestimos que estao em atraso.

* * *

<h2 id="comando">Comandos</h2>

<h3>Lista de comando</h3>

 **Os comandos sao case insensitive**

**Em todo comando e preciso inserir o ; (ponto e virgual) no final**

+ Execucao:


+ Cadastros:
   - Adicionar Usuario:

            user add  "nomedousuario"  tipo;
    Onde o tipo pode ser:
     + tutor(professor)
     + student(estudante)
     + community(comunidade)
     + omitir o tipo: assume-se comnunity como default.

  - Adicionar Livro:

            catalog add "nomedolivro" tipo;
    Onde o tipo pode ser:
    + general (comunitario)
    + text(texto)
    + Omitir o tipo: assume-se general

+ Emprestimos
   - Cadastrar Emprestimo:

   - Cadastrar Devolucao:


+ Listas:

    - Listagem de Usuarios:
            list books;
    - Listagem de Emprestimos:

    - Listagem de Livros:

+ Viagem do Tempo:

* * *

<h2 id="contribuidores">Contribuidores</h2>

* * *

<h2 id="copyright">Copyright</h2>

* * *

## Still to be done:

### *Formatter*
* Loan listings

### *Shell*
* Change "loan" and "return" commands grammar to something more practical

### *Shell*
* Show help message, which shows how to use the commands

### *Shell*
* Show message for wrong commands.

### *Shell*
* User can change the actual date of the system (time-travel feature)

### *Shell*
* Show verbose messages (including date)

### *Database* and *Tables*
* Propagate naming convention from *Shell* commands to attributes and methods

**Actual README file with command grammar instructions!**

