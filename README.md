# SCC0103 - Trabaho 3 - Aplicativo de Biblioteca

## Sumario

* [Introducao](#introducao)
* [Comandos e Execucao](#comando)
* [Contribuidores](#contribuidores)
* [Copyright](#copyright)

* * *

<h2 id="introducao">Introducao</h2>

Aplicativo de Biblioteca implementado no formato de uma Shell interativa.

Suporte para as seguintes funcionalidades diretas e oferecido:

* Cadastramento de usuarios e livros
* Cadastramento de emprestimo
* Atualizacao de emprestimo (retorno)
* Listagem de usuarios, livros e emprestimos
* Finalizacao da *Shell*

Adicionalmente, todas operacoes sao realizadas sob uma determinada data referencial, **que pode ser configurada**:
A data e estabelecida com base no argumento de entrada unico que o programa recebe, no formato `MM/dd/yyyy`. **Se nenhum argumento e oferecido, o programa toma a data atual como a data referencia.**

* * *

<h2 id="comando">Comandos</h2>

<h3>Lista de comandos</h3>
##### ATENCAO
**A validacao de entrada e _case insensitive_.**       
**Ao final de todo comando e preciso inserir o `;` (ponto e virgula).**        
**Insira `"` (aspas) nos comandos, onde indicado. por ex: a ocorrencia `"nomedolivro"` inclui as aspas literalmente.**
**Sempre utilize o comando `exit;` para finalizar o programa, pois o mesmo garante a atualizacao das tabelas `.csv`.**

* Compilacao e execucao manual:
	Do diretorio raiz do projeto, execute para compilar:
```
javac -d out src/br/usp/icmc/ssc0103/*.java 
```
	Supondo que o caminho para o `javac` esteja incluso na variavel `PATH` do seu sistema. Caso contrario, o caminho completo para o JDK tera que ser especificado no lugar.
	Para rodar o programa, execute:
```
java -cp <caminho absoluto para o diretorio raiz do projeto>/out br.usp.icmc.ssc0103.Main <argumento>
```
	Tambem supondo caminho definido para `java`, onde <caminho absoluto para o diretorio raiz do projeto> e exatamente o que sugere e <argumento> idem (Se existir). **Observe a orientacao do caractere `/` (barra) que aqui e demonstrado no padrao de sistemas *NIX. Em Windows/DOS a mesma e invertida.**

* Execucao a partir do pacote:
	Simplesmente va ate o diretorio do pacote `.jar` (`trabalho3_jar` no padrao do projeto) e execute:
```
java -jar trabalho3.jar
```
	Novamente, supondo que o caminho para jdk e definido e o nome do pacote jar seja de fato "trabalho3.jar", como no padrao do projeto.

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

+ Sair:     
                                                          exit;

+ Viagem do Tempo:

    Passe uma data no formato MM/DD/YYYY como argumento no programa. Logo na sua execucao, ele ira imprimir essa data(confirme),
   caso ele nao consiga analisar e pegar essa data, pega-se a data atual do sistema.

    Assim, voce pode viajar ate o futuro e ver toda a magica acontecer

* * *

<h2 id="contribuidores">Contribuidores</h2>

[Danilo Nery](https://github.com/dnery) - Num: 8602430

[Cristiano Lacerda](https://github.com/Ibrahiim) - Num: 8531737

[Marcos Vinicius Junqueira](https://github.com/mvjunq) - Num: 8922393


* * *

<h2 id="copyright">Copyright</h2>
            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
                    Version 2, December 2004

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION

  0. You just DO WHAT THE FUCK YOU WANT TO.

* * *

**Actual README file with command grammar instructions!**






