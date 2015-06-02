# SCC0103 - Trabaho 3 - Aplicativo de Biblioteca

## Sumario

* [Introducao](#introducao)
* [Execucao](#execucao)
* [Comandos](#comandos)
* [Consideracoes](#consideracoes)
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

Adicionalmente, todas operacoes sao realizadas sob uma determinada data referencial, **que pode ser
configurada**: A data e estabelecida com base no argumento de entrada unico que o programa recebe,
no formato `MM/dd/yyyy`. **Se nenhum argumento e oferecido, o programa toma a data atual como a
data referencia.**

* * *

<h2 id="execucao">Execucao</h2>

* Compilacao e execucao manual:

    Do diretorio raiz do projeto, execute para compilar:
    ```
    javac -d out src/br/usp/icmc/ssc0103/*.java 
    ```
    Supondo que o caminho para o `javac` esteja incluso na variavel `PATH` do seu sistema.
    Caso contrario, o caminho completo para o JDK tera que ser especificado no lugar.
    Para rodar o programa, execute:
    ```
    java -cp <caminho absoluto para o diretorio raiz do projeto>/out br.usp.icmc.ssc0103.Main <argumento>
    ```
    Tambem supondo caminho definido para `java`, onde `<caminho absoluto para o diretorio raiz do projeto>`
    e exatamente o que sugere e `<argumento>` idem (Se existir). **Observe a orientacao do caractere `/`
    (barra) que aqui e demonstrado no padrao de sistemas *NIX. Em Windows/DOS a mesma e invertida.**
    

* Execucao a partir do pacote:

    Simplesmente va ate o diretorio do pacote `.jar` (`package` no padrao do projeto) e execute:
    ```
    java -jar trabalho3.jar
    ```
    Novamente, supondo que o caminho para jdk e definido e o nome do pacote jar seja de fato 
    "trabalho3.jar", como no padrao do projeto.
    
* * *

<h2 id="comandos">Comandos</h2>
**A validacao de entrada e _case insensitive_. Ao final de todo comando e preciso inserir o `;`
(ponto e virgula) Insira `"` (aspas) nos comandos, onde indicado. por ex: a ocorrencia
`"<nomedolivro>"` inclui as aspas literalmente. Sempre utilize o comando `exit;` 
para finalizar o programa, pois o mesmo garante a atualizacao das tabelas `.csv`.**

+ Cadastramendo de novos usuarios e livros:

    - Cadastrar usuario:
        ```
        user add "<nomedousuario>" <tipo>;
        ```
        Onde `<nomedousuario>` suporta caracteres alfanumericos somente e `<tipo>` pode ser:
        + `tutor` *(professor)*
        + `student` *(estudante)*
        + `community` *(comunidade)*
        + Nulo: assume-se `community` como padrao.

    - Cadastrar livro:
        ```
        catalog add "<nomedolivro>" <tipo>;
        ```
        Onde `<nomedolivro>` suporta caracteres alfanumericos somente e `<tipo>` pode ser:
        + `text` *(livro texto)*
        + `general` *(livro literario)*
        + Nulo: assume-se `general` como padrao.
        
    - Exemplos de uso:
        ```
        user add "Danilo" tutor;
        ```
        *Cadastra um novo usuario de nome "Danilo" como professor.*
        ```
        user add "Cristiano";
        ```
        *Cadastra um novo usuario de nome "Cristiano" como da comunidade.*
        ```
        catalog add "Calculus" text;
        ```
        *Cadastra um novo livro de titulo "Calculus" como livro texto.*
        ```
        catalog add "Brave new world";
        ```
        *Cadastra um novo livro de titulo "Brave new world" como livro literario.*


+ Realizacao e devolucao de emprestimos:

    - Registrar emprestimo:
        ```
        lend "<nomedolivro>" to "<nomedousuario>";
        ```
        Onde ambos `<nomedolivro>` e `<nomedousuario>` suportam caracteres alfanumericos somente.

    - Registrar devolucao:
        ```
        return "<nomedolivro>";
        ```
        Onde, novamente, `<nomedolivro>` suporta caracteres alfanumericos somente.

    - Exemplo de uso:
        ```
        lend "Brave new world" to "Danilo";
        ```
        *Registra o emprestimo de "Brave new world" para "Danilo".*
        ```
        return "Brave new world";
        ```
        *Registra a devolucao do livro de titulo "Brave new world".*
        
        
+ Listagem de tabelas:

    - Listagem de Usuarios:
        ```
        list users;
        ```
        *Lista todos usuarios cadastrados classificados por tipo e com auxilio de 
        cores e mensagens apropriadas para melhor visualizacao.*
    - Listagem de Livros:
        ```
        list books;
        ```
        *Lista todos livros cadastrados classificados por tipo e com auxilio de 
        cores e mensagens apropriadas para melhor visualizacao.*
    - Listagem de Emprestimos:
        ```
        list loans;
        ```
        *Lista todos emprestimos registrados classificados por estado (entregue, nao entregue ou
        overdue) e com auxilio de  cores e mensagens apropriadas para melhor visualizacao.*

+ Finalizar aplicacao:     
    ```
    exit;
    ```
    *Serializa todas entradas nas tabelas `.csv`, atualiza os arquivos e encerra a execucao.*

* * *

<h2 id="consideracoes">Consideracoes</h2>

+ Sintaxe dos comandos:

    Inicialmente, o input do usuario e avaliado contra o padrao `"^(.*[^\\\\];)$"` que basicamente
    casa com qualquer input que seja finalizado por `;` e nao contenha `\` ou `,` em seu interim.
    
    O input, se valido, e entao avaliado contra uma expressao regular mais geral de algum comando; 
    Se houver casamento, o comando e estabelecido e ha uma tentativa de processamento do input contra 
    a expressao regular compilada para dado comando. Se ate entao nao houverem erros de sintaxe,
    o comando real de manipulacao da database e lancado usando as informacoes extraidas do input.
    
    Veja as expressoes regulares usadas para validacao final dos comandos usados:
    
        - [Cadastra usuario](https://regex101.com/r/cZ7lK1/8)
        - [Cadastra livro](https://regex101.com/r/nU9qD4/2)
        - [Registra emprestimo](https://regex101.com/r/lV3vI3/2)
        - [Registra devolucao](https://regex101.com/r/rT4hC9/3)
        - [Lista tabela](https://regex101.com/r/qI7wF9/10)

+ Viagem no tempo:

    Na implementacao escolhida, como e possivel entrar com uma data para operar a aplicacao,
    o tempo e tratado como absolutamente linear. Nessa condicao, eis as duas situacoes
    particularmente questionaveis e como elas sao tratadas:
    
    - Usuarios suspensos ou com emprestimos pendentes:
    
        Uma data de "suspenso ate" so e estabelecida para o usuario se ele de fato entregou
        algum livro apos a data de devolucao maxima. **Isso significa que, se o usuario foi
        marcado como suspenso em algum dado momento, uma listagem de usuarios num tempo
        anterior ao atual o acusara como suspenso por um periodo ainda maior, pois a data
        de suspensao e sempre avaliada contra a data de consulta**. Isso *tambem* significa
        que se um usuario possui um emprestimo *pendente* e *vencido* numa certa data, uma
        listagem pode acusa-lo como suspenso agora mas nao numa data anterior, uma vez que
        uma devolucao nao foi registrada para que uma data de "suspenso ate" fosse estabelecida.
        **Um usuario que nunca foi suspenso perde a oportunidade de ser incluido na
        situacao descrita acima.**
        
    - Emprestimos em aberto, talvez extrapolados:
    
        Um emprestimo em aberto sempre e avaliado contra a data de consulta e so e de fato 
        fechado quando a devolucao e registrada. **Isso significa que um emprestimo pode ser
        listado como aberto mesmo numa data anterior a do emprestimo em si, dado que o livro
        nao tenha sido devolvido ainda.** Isso *tambem* significa que um mesmo emprestimo
        pode ser listado como aberto ou como aberto e extrapolado em consultas realizadas
        em diferentes datas. **Um emprestimo fechado nao participa do dilema acima."
        
    ### Hilaridade segue.

* * *

<h2 id="contribuidores">Contribuidores</h2>

[Danilo Nery](https://github.com/dnery) - No. USP: 8602430

[Cristiano Lacerda](https://github.com/Ibrahiim) - No. USP: 8531737

[Marcos Vinicius Junqueira](https://github.com/mvjunq) - No. USP: 8922393

* * *

<h2 id="copyright">Copyright</h2>
            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
                    Version 2, December 2004

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION

  0. You just DO WHAT THE FUCK YOU WANT TO.

* * *
