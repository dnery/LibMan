# SCC0103 - Trabaho 3 - Aplicativo de Biblioteca

## Sumario

* [Introducao](#introducao)
* [Execução](#execucao)
* [Comandos](#comandos)
* [Consideracoes](#consideracoes)
* [Contribuidores](#contribuidores)
* [Copyright](#copyright)

* * *

<h2 id="introducao">Introducao</h2>

Aplicativo de Biblioteca implementado no formato de uma Shell interativa.

Suporte para as seguintes funcionalidades diretas é oferecido:

* Cadastramento de usuários e livros
* Cadastramento de empréstimo
* Atualizacao de empréstimo (retorno)
* Listagem de usuários, livros e empréstimos
* Finalizacao da *Shell*

Adicionalmente, todas operacoes sao realizadas sob uma determinada data referencial, **que pode ser
configurada**: A data é estabelecida com base no argumento de entrada único que o programa recebe,
no formato `MM/dd/yyyy`. **Se nenhum argumento é oferecido, o programa toma a data atual como a
data referência.**

* * *

<h2 id="execucao">Execução</h2>

* Compilando e executando manualmente:

    Do diretorio raiz do projeto, execute o seguinte comando para fazer a compilação:
    ```
    javac -d out src/br/usp/icmc/ssc0103/*.java 
    ```
    Supondo que o caminho para o `javac` esteja incluso na variável `PATH` do seu sistema.
    Caso contrario, o caminho completo para o JDK tera que ser especificado no lugar.
    A flag `-d` sinaliza um caminho para o output da compilação; Não precisa necessariamente ser
    `out` mas **precisa** ser especificado para que o output da compilação inclua a árvore de 
    diretórios do pacote (`-d .` geraria a árvore a partir do diretório atual, por exemplo).
    Execute o programa com:
    ```
    java -cp <caminho absoluto para o diretorio raiz do projeto>/out br.usp.icmc.ssc0103.Main <argumento>
    ```
    Tambem supondo caminho definido para `java`, onde `<caminho absoluto para o diretorio raiz do projeto>`
    é exatamente o que sugere e `<argumento>` idem (Se existir). **Observe a orientacao do caractere `/`
    que aqui é demonstrado no padrão de sistemas *NIX. Em Windows/DOS a mesma é invertida.**

* Gerando e executando um pacote manualmente:

    Considerando que o projeto está todo contido num pacote, será necessário criar um arquivo
    com no mínimo o seguinte conteúdo:
    
        Main-Class: br.usp.icmc.ssc0103.Main

    **Terminando com uma linha em branco _ou_ carriage return.** O nome desse arquivo será passado
    como argumento para a ferramenta `jar` e seu conteúdo será adicionado ao `META-INF/MANIFEST.MD`
    automaticamente gerado e incluído no pacote pela ferramenta. Suponha que nosso arquivo tenha
    o nome `Manifest.MD`. Geraremos um pacote, então, com o seguinte comando (levando em conta
    um caminho definido para a ferramenta `jar`):
    ```
    jar cfm <resultante.jar> <manifest> -C <diretoriodopacote>
    ```
    Onde `<resultante.jar>` é obviamente o nome do arquivo `.jar` resultante, `<manifest>` o nome
    do arquivo com conteúdo a ser adicionado no *manifest*, `Manifest.MD` no nosso caso, e
    `<diretoriodopacote>` é o diretório contendo o *output* da compilação. No padrão do projeto
    *IntelliJ IDEA*, que é o usado, este seria `out/`, e o resultado da compilação é a árvore
    `br/usp/icmc/ssc0103/` onde `ssc0103` contém todos os `.class` do projeto. A flag `-C` sinaliza
    a inclusão de um diretório, oposto a inclusão de um arquivo que seria o padrão.
    
    Com o nosso `.jar` gerado, basta executá-lo com:
    ```
    java -jar <resultante.jar> <argumento>
    ```
    Onde `<resultante.jar>` é exatamente o que sugere e `<argumento>` igem (Se existir).

* Executando a partir do pacote pronto:

    Simplesmente vá até o diretorio do pacote `.jar` (`pkg` no padrão do projeto) e execute:
    ```
    java -jar trabalho3.jar
    ```
    Ou simplesmente duplo-clique no `.jar`, novamente, supondo que o caminho para jre é definido
    e o nome do pacote jar seja de fato "trabalho3.jar", como no padrão do projeto. O diretório
    `pkg` já inclui uma database razoavelmente populada para conveniência. Vale informar que
    o pacote foi gerado e testado com JDK/JRE Oracle versão *1.8.0_45*. Do `release`:
    ```
    JAVA_VERSION="1.8.0_45"
    OS_NAME="Windows"
    OS_VERSION="5.2"
    OS_ARCH="amd64"
    SOURCE=" .:d195213dc77e corba:681b5c54c9a8 deploy:8ceddb02649f hotspot:13990387b643 hotspot/make/closed:05aa2680eb9f hotspot/src/closed:91caea42673b hotspot/test/closed:60b47b8c1721 install:4318d1f7b3a5 jaxp:1c4cdf942059 jaxws:1a0139074296 jdk:d177c684b874 jdk/make/closed:137c6a750834 jdk/src/closed:f47229507b9a jdk/test/closed:42d53147784b langtools:3c7d5e1ec7e5 nashorn:7a2d26de1826 pubs:b60ba41c22d2 sponsors:9a5a318d7ca6"
    BUILD_TYPE="commercial"
    ```

* * *

<h2 id="comandos">Comandos</h2>
**A validação de entrada é _case insensitive_. Ao final de todo comando é preciso 
inferir um `;`. Mantenha `"` nos comandos, onde indicado. por ex: a ocorrencia
`"<nomedolivro>"` inclui as aspas literalmente. Sempre utilize o comando `exit;` 
para finalizar o programa, pois o mesmo garante a atualizacao das tabelas `.csv`.**

* Cadastramento de novos usuários e livros:

    - Cadastrar usuário:
        ```
        user add "<nomedousuario>" <tipo>;
        ```
        Onde `<nomedousuario>` suporta caracteres alfanumericos somente e `<tipo>` pode ser:
        + `tutor` *(professor)*
        + `student` *(estudante)*
        + `community` *(comunidade)*
        + Nulo: assume-se `community` como padrão.

    - Cadastrar livro:
        ```
        catalog add "<nomedolivro>" <tipo>;
        ```
        Onde `<nomedolivro>` suporta caracteres alfanumericos somente e `<tipo>` pode ser:
        + `text` *(livro texto)*
        + `general` *(livro literario)*
        + Nulo: assume-se `general` como padrão.

    - Exemplos de uso:
        ```
        user add "Danilo" tutor;
        ```
        *Cadastra um novo usuário de nome "Danilo" como professor.*
        ```
        user add "Cristiano";
        ```
        *Cadastra um novo usuário de nome "Cristiano" como da comunidade.*
        ```
        catalog add "Calculus" text;
        ```
        *Cadastra um novo livro de titulo "Calculus" como livro texto.*
        ```
        catalog add "Brave new world";
        ```
        *Cadastra um novo livro de titulo "Brave new world" como livro literario.*


* Realizacao e devolução de empréstimos:

    - Registrar empréstimo:
        ```
        lend "<nomedolivro>" to "<nomedousuario>";
        ```
        Onde ambos `<nomedolivro>` e `<nomedousuario>` suportam caracteres alfanumericos somente.

    - Registrar devolução:
        ```
        return "<nomedolivro>";
        ```
        Onde, novamente, `<nomedolivro>` suporta caracteres alfanumericos somente.

    - Exemplo de uso:
        ```
        lend "Brave new world" to "Danilo";
        ```
        *Registra o empréstimo de "Brave new world" para "Danilo".*
        ```
        return "Brave new world";
        ```
        *Registra a devolução do livro de titulo "Brave new world".*


* Listagem de tabelas:

    - Listagem de Usuários:
        ```
        list users;
        ```

        *Lista todos usuários cadastrados classificados por tipo e com auxilio de 
        cores e mensagens apropriadas para melhor visualização.*
    - Listagem de Livros:
        ```
        list books;
        ```

        *Lista todos livros cadastrados classificados por tipo e com auxilio de 
        cores e mensagens apropriadas para melhor visualização.*
    - Listagem de Empréstimos:
        ```
        list loans;
        ```

        *Lista todos empréstimos registrados classificados por estado (entregue, não entregue ou
        overdue) e com auxilio de  cores e mensagens apropriadas para melhor visualização.*

    O comando `list` aceita, inicialmente, um único argumento não-nulo. Porém podem ser concatenados
    indefinidamente. Praticamente o comando é simplesmente repetido para vários argumentos.
    Por exemplo, o seguinte comando:
    ```
    list users books loans;
    ```
    Listará, consecutivamente, as tabelas `users`, `books` e `loans`, na ordem em que os
    respectivos argumentos foram fornecidos. Podem ser repetidos. **Indefinidamente.**

* Finalizar aplicação:     
    ```
    exit;
    ```
    *Serializa todas entradas nas tabelas `.csv`, atualiza os arquivos e encerra a execução.*

* * *

<h2 id="consideracoes">Consideracoes</h2>

* Sintaxe dos comandos:

    Inicialmente, o input do usuário é avaliado contra o padrão `"^(.*[^\\\\];)$"` que basicamente
    casa com qualquer input que seja finalizado por `;` e não contenha `\` ou `,` em seu interim.
    
    O input, se valido, é avaliado contra uma expressao regular mais geral de algum comando; 
    Se houver casamento, o comando é estabelecido e há uma tentativa de processamento do input contra 
    a expressao regular compilada para dado comando. Se até então não houverem erros de sintaxe,
    o comando real de manipulacao da database é lancado usando as informacoes extraidas do input.
    
    Veja as expressões regulares usadas para validação final dos comandos usados:
    
    - [Cadastra usuário](https://regex101.com/r/cZ7lK1/8)
    - [Cadastra livro](https://regex101.com/r/nU9qD4/2)
    - [Registra empréstimo](https://regex101.com/r/lV3vI3/2)
    - [Registra devolução](https://regex101.com/r/rT4hC9/3)
    - [Lista tabela](https://regex101.com/r/qI7wF9/10)

* Viagem no tempo:

    Na implementação escolhida, como é possivel entrar com uma data para operar a aplicação,
    o tempo é tratado como absolutamente linear. Nessa condição, eis as duas situações
    particularmente questionáveis e como elas são tratadas:
    
    - Usuários suspensos ou com empréstimos pendentes:
    
        Uma data de "suspenso até" so é estabelecida para o usuário se ele de fato entregou
        algum livro apos a data de devolução maxima. **Isso significa que, se o usuário foi
        marcado como suspenso em algum dado momento, uma listagem de usuários num tempo
        anterior ao atual o acusara como suspenso por um periodo ainda maior, pois a data
        de suspensao é sempre avaliada contra a data de consulta**. Isso *tambem* significa
        que se um usuário possui um empréstimo *pendente* e *vencido* numa certa data, uma
        listagem pode acusa-lo como suspenso agora mas não numa data anterior, uma vez que
        uma devolução não foi registrada para que uma data de "suspenso até" fosse estabelecida.
        **Um usuário que nunca foi suspenso perde a oportunidade de ser incluido em tal situação.**
        
    - Empréstimos em aberto, talvez extrapolados:
    
        Um empréstimo em aberto sempre é avaliado contra a data de consulta e so é de fato 
        fechado quando a devolução é registrada. **Isso significa que um empréstimo pode ser
        listado como aberto mesmo numa data anterior a do empréstimo em si, dado que o livro
        não tenha sido devolvido ainda.** Isso *tambem* significa que um mesmo empréstimo
        pode ser listado como aberto ou como aberto e extrapolado em consultas realizadas
        em diferentes datas. **Um empréstimo fechado não faz parte de tal situação.**
        
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

* * *
