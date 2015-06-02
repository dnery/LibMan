<h1>SCC0103 - Trabaho 3 - Aplicativo de Biblioteca</h1>

<h2>Sumario</h2>

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
<h5> ATENCAO </h5>
**Os comandos sao case insensitive**.
**Em todo comando e preciso inserir o ; (ponto e virgula) no final**.

+ Execucao:

   - Compilacao:

   - Executar:


+ Cadastros:
   - Adicionar Usuario:

            user add  "nomedousuario"  tipo;
    *"nomedousuario" pode ter caracteres alfanumericos (exceto \\, / e ,(virgula)).*

    Onde o tipo pode ser:
     + tutor(professor)
     + student(estudante)
     + community(comunidade)
     + omitir o tipo: assume-se community como default.

  - Adicionar Livro:

            catalog add "nomedolivro" tipo;
    *"nomedolivro" pode ter caracteres alfanumericos (exceto \\, / e ,(virgula)).*
    Onde o tipo pode ser:
    + general (comunitario)
    + text(texto)
    + Omitir o tipo: assume-se comunitario

+ Emprestimos

   - Cadastrar Emprestimo:

            lend "nomedolivro" to "nomeusuario";
    *Onde "nomedolivro" e "nomeusuario" pode ter caracteres alfanumericos(exceto \, / e , (virgula)).*

   - Cadastrar Devolucao:

            return "nomedolivro";
    *Onde "nomedolivro" pode ter caracteres alfanumericos(exceto \, | e , (virgula)).*

+ Listas:

    - Listagem de Usuarios:

            list user;
        *Ira mostrar uma lista de usuarios, classificados pelo tipo.*
        *Caso o usuario possua emprestismos ou esteja em atraso, mostrara mensagem.*
    - Listagem de Livros:

            list books;
        *Ira mostrar uma lista de livros, classificados pelo tipo.*
        *Os livros com emprestimos estarao em vermelho*
    - Listagem de Emprestimos:

            list loans;
        *Ira mostrar uma lista de emprestismos, classificados por status.*
+ Viagem do Tempo:

* * *

<h2 id="contribuidores">Contribuidores</h2>

* * *

<h2 id="copyright">Copyright</h2>

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
                    Version 2, December 2004

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION

  0. You just DO WHAT THE FUCK YOU WANT TO.


* * *

**Actual README file with command grammar instructions!**






