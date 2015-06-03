# SCC0103 - Trabaho 3 - Aplicativo de Biblioteca

## Sumario

* [Introducao](#introducao)
* [Execu��o](#execucao)
* [Comandos](#comandos)
* [Consideracoes](#consideracoes)
* [Contribuidores](#contribuidores)
* [Copyright](#copyright)

* * *

<h2 id="introducao">Introducao</h2>

Aplicativo de Biblioteca implementado no formato de uma Shell interativa.

Suporte para as seguintes funcionalidades diretas � oferecido:

* Cadastramento de usu�rios e livros
* Cadastramento de empr�stimo
* Atualizacao de empr�stimo (retorno)
* Listagem de usu�rios, livros e empr�stimos
* Finalizacao da *Shell*

Adicionalmente, todas operacoes sao realizadas sob uma determinada data referencial, **que pode ser
configurada**: A data � estabelecida com base no argumento de entrada �nico que o programa recebe,
no formato `MM/dd/yyyy`. **Se nenhum argumento � oferecido, o programa toma a data atual como a
data refer�ncia.**

* * *

<h2 id="execucao">Execu��o</h2>

* Compilando e executando manualmente:

    Do diretorio raiz do projeto, execute o seguinte comando para fazer a compila��o:
    ```
    javac -d out src/br/usp/icmc/ssc0103/*.java 
    ```
    Supondo que o caminho para o `javac` esteja incluso na vari�vel `PATH` do seu sistema.
    Caso contrario, o caminho completo para o JDK tera que ser especificado no lugar.
    A flag `-d` sinaliza um caminho para o output da compila��o; N�o precisa necessariamente ser
    `out` mas **precisa** ser especificado para que o output da compila��o inclua a �rvore de 
    diret�rios do pacote (`-d .` geraria a �rvore a partir do diret�rio atual, por exemplo).
    Execute o programa com:
    ```
    java -cp <caminho absoluto para o diretorio raiz do projeto>/out br.usp.icmc.ssc0103.Main <argumento>
    ```
    Tambem supondo caminho definido para `java`, onde `<caminho absoluto para o diretorio raiz do projeto>`
    � exatamente o que sugere e `<argumento>` idem (Se existir). **Observe a orientacao do caractere `/`
    que aqui � demonstrado no padr�o de sistemas *NIX. Em Windows/DOS a mesma � invertida.**

* Gerando e executando um pacote manualmente:

    Considerando que o projeto est� todo contido num pacote, ser� necess�rio criar um arquivo
    com no m�nimo o seguinte conte�do:
    
        Main-Class: br.usp.icmc.ssc0103.Main

    **Terminando com uma linha em branco _ou_ carriage return.** O nome desse arquivo ser� passado
    como argumento para a ferramenta `jar` e seu conte�do ser� adicionado ao `META-INF/MANIFEST.MD`
    automaticamente gerado e inclu�do no pacote pela ferramenta. Suponha que nosso arquivo tenha
    o nome `Manifest.MD`. Geraremos um pacote, ent�o, com o seguinte comando (levando em conta
    um caminho definido para a ferramenta `jar`):
    ```
    jar cfm <resultante.jar> <manifest> -C <diretoriodopacote>
    ```
    Onde `<resultante.jar>` � obviamente o nome do arquivo `.jar` resultante, `<manifest>` o nome
    do arquivo com conte�do a ser adicionado no *manifest*, `Manifest.MD` no nosso caso, e
    `<diretoriodopacote>` � o diret�rio contendo o *output* da compila��o. No padr�o do projeto
    *IntelliJ IDEA*, que � o usado, este seria `out/`, e o resultado da compila��o � a �rvore
    `br/usp/icmc/ssc0103/` onde `ssc0103` cont�m todos os `.class` do projeto. A flag `-C` sinaliza
    a inclus�o de um diret�rio, oposto a inclus�o de um arquivo que seria o padr�o.
    
    Com o nosso `.jar` gerado, basta execut�-lo com:
    ```
    java -jar <resultante.jar> <argumento>
    ```
    Onde `<resultante.jar>` � exatamente o que sugere e `<argumento>` igem (Se existir).

* Executando a partir do pacote pronto:

    Simplesmente v� at� o diretorio do pacote `.jar` (`pkg` no padr�o do projeto) e execute:
    ```
    java -jar trabalho3.jar
    ```
    Ou simplesmente duplo-clique no `.jar`, novamente, supondo que o caminho para jre � definido
    e o nome do pacote jar seja de fato "trabalho3.jar", como no padr�o do projeto. O diret�rio
    `pkg` j� inclui uma database razoavelmente populada para conveni�ncia. Vale informar que
    o pacote foi gerado e testado com JDK/JRE Oracle vers�o *1.8.0_45*. Do `release`:
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
**A valida��o de entrada � _case insensitive_. Ao final de todo comando � preciso 
inferir um `;`. Mantenha `"` nos comandos, onde indicado. por ex: a ocorrencia
`"<nomedolivro>"` inclui as aspas literalmente. Sempre utilize o comando `exit;` 
para finalizar o programa, pois o mesmo garante a atualizacao das tabelas `.csv`.**

* Cadastramento de novos usu�rios e livros:

    - Cadastrar usu�rio:
        ```
        user add "<nomedousuario>" <tipo>;
        ```
        Onde `<nomedousuario>` suporta caracteres alfanumericos somente e `<tipo>` pode ser:
        + `tutor` *(professor)*
        + `student` *(estudante)*
        + `community` *(comunidade)*
        + Nulo: assume-se `community` como padr�o.

    - Cadastrar livro:
        ```
        catalog add "<nomedolivro>" <tipo>;
        ```
        Onde `<nomedolivro>` suporta caracteres alfanumericos somente e `<tipo>` pode ser:
        + `text` *(livro texto)*
        + `general` *(livro literario)*
        + Nulo: assume-se `general` como padr�o.

    - Exemplos de uso:
        ```
        user add "Danilo" tutor;
        ```
        *Cadastra um novo usu�rio de nome "Danilo" como professor.*
        ```
        user add "Cristiano";
        ```
        *Cadastra um novo usu�rio de nome "Cristiano" como da comunidade.*
        ```
        catalog add "Calculus" text;
        ```
        *Cadastra um novo livro de titulo "Calculus" como livro texto.*
        ```
        catalog add "Brave new world";
        ```
        *Cadastra um novo livro de titulo "Brave new world" como livro literario.*


* Realizacao e devolu��o de empr�stimos:

    - Registrar empr�stimo:
        ```
        lend "<nomedolivro>" to "<nomedousuario>";
        ```
        Onde ambos `<nomedolivro>` e `<nomedousuario>` suportam caracteres alfanumericos somente.

    - Registrar devolu��o:
        ```
        return "<nomedolivro>";
        ```
        Onde, novamente, `<nomedolivro>` suporta caracteres alfanumericos somente.

    - Exemplo de uso:
        ```
        lend "Brave new world" to "Danilo";
        ```
        *Registra o empr�stimo de "Brave new world" para "Danilo".*
        ```
        return "Brave new world";
        ```
        *Registra a devolu��o do livro de titulo "Brave new world".*


* Listagem de tabelas:

    - Listagem de Usu�rios:
        ```
        list users;
        ```

        *Lista todos usu�rios cadastrados classificados por tipo e com auxilio de 
        cores e mensagens apropriadas para melhor visualiza��o.*
    - Listagem de Livros:
        ```
        list books;
        ```

        *Lista todos livros cadastrados classificados por tipo e com auxilio de 
        cores e mensagens apropriadas para melhor visualiza��o.*
    - Listagem de Empr�stimos:
        ```
        list loans;
        ```

        *Lista todos empr�stimos registrados classificados por estado (entregue, n�o entregue ou
        overdue) e com auxilio de  cores e mensagens apropriadas para melhor visualiza��o.*

    O comando `list` aceita, inicialmente, um �nico argumento n�o-nulo. Por�m podem ser concatenados
    indefinidamente. Praticamente o comando � simplesmente repetido para v�rios argumentos.
    Por exemplo, o seguinte comando:
    ```
    list users books loans;
    ```
    Listar�, consecutivamente, as tabelas `users`, `books` e `loans`, na ordem em que os
    respectivos argumentos foram fornecidos. Podem ser repetidos. **Indefinidamente.**

* Finalizar aplica��o:     
    ```
    exit;
    ```
    *Serializa todas entradas nas tabelas `.csv`, atualiza os arquivos e encerra a execu��o.*

* * *

<h2 id="consideracoes">Consideracoes</h2>

* Sintaxe dos comandos:

    Inicialmente, o input do usu�rio � avaliado contra o padr�o `"^(.*[^\\\\];)$"` que basicamente
    casa com qualquer input que seja finalizado por `;` e n�o contenha `\` ou `,` em seu interim.
    
    O input, se valido, � avaliado contra uma expressao regular mais geral de algum comando; 
    Se houver casamento, o comando � estabelecido e h� uma tentativa de processamento do input contra 
    a expressao regular compilada para dado comando. Se at� ent�o n�o houverem erros de sintaxe,
    o comando real de manipulacao da database � lancado usando as informacoes extraidas do input.
    
    Veja as express�es regulares usadas para valida��o final dos comandos usados:
    
    - [Cadastra usu�rio](https://regex101.com/r/cZ7lK1/8)
    - [Cadastra livro](https://regex101.com/r/nU9qD4/2)
    - [Registra empr�stimo](https://regex101.com/r/lV3vI3/2)
    - [Registra devolu��o](https://regex101.com/r/rT4hC9/3)
    - [Lista tabela](https://regex101.com/r/qI7wF9/10)

* Viagem no tempo:

    Na implementa��o escolhida, como � possivel entrar com uma data para operar a aplica��o,
    o tempo � tratado como absolutamente linear. Nessa condi��o, eis as duas situa��es
    particularmente question�veis e como elas s�o tratadas:
    
    - Usu�rios suspensos ou com empr�stimos pendentes:
    
        Uma data de "suspenso at�" so � estabelecida para o usu�rio se ele de fato entregou
        algum livro apos a data de devolu��o maxima. **Isso significa que, se o usu�rio foi
        marcado como suspenso em algum dado momento, uma listagem de usu�rios num tempo
        anterior ao atual o acusara como suspenso por um periodo ainda maior, pois a data
        de suspensao � sempre avaliada contra a data de consulta**. Isso *tambem* significa
        que se um usu�rio possui um empr�stimo *pendente* e *vencido* numa certa data, uma
        listagem pode acusa-lo como suspenso agora mas n�o numa data anterior, uma vez que
        uma devolu��o n�o foi registrada para que uma data de "suspenso at�" fosse estabelecida.
        **Um usu�rio que nunca foi suspenso perde a oportunidade de ser incluido em tal situa��o.**
        
    - Empr�stimos em aberto, talvez extrapolados:
    
        Um empr�stimo em aberto sempre � avaliado contra a data de consulta e so � de fato 
        fechado quando a devolu��o � registrada. **Isso significa que um empr�stimo pode ser
        listado como aberto mesmo numa data anterior a do empr�stimo em si, dado que o livro
        n�o tenha sido devolvido ainda.** Isso *tambem* significa que um mesmo empr�stimo
        pode ser listado como aberto ou como aberto e extrapolado em consultas realizadas
        em diferentes datas. **Um empr�stimo fechado n�o faz parte de tal situa��o.**
        
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
