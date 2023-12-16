## LDTS_T10G04  - Pong Game

Neste trabalho pretende-se desenvolver o clássico jogo "Pong". O jogo consiste em dois paddle que se movimentam para cima ou para baixo, controlados por dois jogadores ou por um jogador e o computador, e uma bola que se movimenta livremente pelo campo de jogo e, ao embater num objeto, muda a sua direção para uma direção no sentido oposto.

Este projeto foi desenvolvido por *Rafael Azevedo Alves* (up202004476@fc.up.pt), *Pedro João Gonçalves Novo Paixão* (up202008467@fe.up.pt) e *João Victor Botelho Duarte* (up202102361@up.pt) para LDTS 2022/2023.

### IMPLEMENTED FEATURES

**Golos** - Quando o valor de x da posição da bola estiver além do valor de x da posição do paddle o score aumenta em 1 valor, dependendo do lado para qual o golo foi marcado

**Vitória** - O jogador que chegar primeiro a X golos ganha

**Velocidade** - A velocidade de movimento da bola aumenta à medida que sobe o nível de dificuldade

**Multiplayer** - Haver um modo em que são permitidos dois jogadores em simultâneo

**Direções Paddle** - Os paddles apenas podem andar para cima ou para baixo, dependendo da tecla pressionada pelo utilizador

**Direções Bola** - A bola pode andar em 12 direções (6 para cada lado)

**Embater Paddles** - Quando a bola embate num paddle, a sua direção muda para uma aleatória no sentido oposto

**Embater obstáculos** - Quando a bola embate num obstáculo (limites do jogo, por exemplo), a sua direção muda para a sua oposta

**Singleplayer** - Haver um modo em que será o utilizador contra o computador

**Bola passar paredes** - Existir um nível em que a bola pode passar os limites do terreno de jogo e aparece do outro lado

**Criação de nível** - Existir um modo de jogo em que o utilizador pode criar o seu jogo (tamanho dos paddles, velocidade da bola, obstaculos no campo)

**Obstáculos** - Existir obstáculos no meio do campo de jogo. Existem dois tipos diferentes de obstáculos, o 'X' que, caso a bola embata nele, envia-a numa direção aleatória no sentido oposto, e o '$' que, caso a bola embata nele, envia-a numa direção aleatória no mesmo sentido em que seguia anteriormente


### PLANNED FEATURES

Todas as features planeadas foram implementadas.

### DESIGN

#### O menu deve responder consoante a tecla pressionada no teclado

**Problem in Context**

O problema consiste em associar a ação de pressionar uma tecla, ao código que contem a ação que lhe corresponde.

**The Pattern**

Aplicamos o **Command** pattern. Este pattern permite-nos transmitir a informação sobre a tecla que é pressionada quando o utilizador se encontra no menu para o código que, ao receber essa informação executa o código tendo como base a informação recolhida.

**Implementation**

![command](https://github.com/FEUP-LDTS-2022/project-l10gr04/blob/main/docs/screenshots/command.png)

**Consequences**

O uso de Command Pattern neste design permite os seguintes benefícios:

- Permite que ao carregar na tecla 's' no menu inicial, o jogador comece o jogo.
- Permite que ao carregar na tecla 'i' no menu inicial, o jogador seja direcionado para as instruções do jogo.
- Permite que ao carregar na tecla 'q' no menu inicial, a janela do jogo seja fechada.
- Permite que ao movimentar-se através dos números '1' a '5', o utilizador possa selecionar o nível que pretende jogar.

#### O movimento do paddle deve variar consoante a tecla que seja pressionado no teclado

**Problem in Context**

O problema foi perceber como fazer com que os paddles se movimentassem na mesma direção que a tecla pressionada pelo utilizador no teclado.

**The Pattern**

Nós aplicamos o **State** pattern. Este pattern permite que o objeto altere o seu comportamento quando o seu estado interno muda (neste caso, quando tecla do teclado é pressionada).

**Implementation**

![state](https://github.com/FEUP-LDTS-2022/project-l10gr04/blob/main/docs/screenshots/state.png)

**Consequences**

O uso do de State pattern no design atual permite que a habilidade dos paddles mudarem de direção se torne explícita no código.

#### Haver classe para implementação e abstração

**Problem in Context**

O problema foi dividir uma grande classe em duas classes, uma para implementação e outra para abstração.

**The Pattern**

Nós aplicamos o **Bridge** pattern. Este pattern permite dividir uma grande classe intimamente relacionadas em duas hierarquias separadas - abstração e implementação - que podem ser desenvolvidas independentemente uma da outra.

**Implementation**

![bridge](https://github.com/FEUP-LDTS-2022/project-l10gr04/blob/main/docs/screenshots/bridge.png)

**Consequences**

O uso do de Bridge pattern no design atual permite que haja uma ligação entre Game e GameBuild, sem a necessidade de existir uma grande classe com o código todo dentro dela.

#### O programa deve avaliar a posição da bola

**Problem in Context**

O problema consiste em saber a posição da bola com o intuito de avaliar se a mesma se encontra na mesma posição que uma parede ou obstáculo (paddles incluidos), o que provoca que a sua direção tenha de mudar.

**The Pattern**

Aplicamos o **Observer** pattern. Este pattern permite-nos saber a posição da bola e notificar caso se encontre na mesma posição dos outros objetos e assim mudar a sua direção.

**Implementation**

![observer](https://github.com/FEUP-LDTS-2022/project-l10gr04/blob/main/docs/screenshots/observer.png)

**Consequences**

O uso de Observer Pattern neste design permite os seguintes benefícios:

- Permite que assim que a bola se encontre na mesma posiçao que um obstáculo muda a sua direção no sentido oposto.
- Permite que assim que a bola se encontre na mesma posiçao que uma parede ressalta continuando o seu percurso na mesma direção.

#### O programa deve poder fazer alterações utilizando em conjunto várias classes entre os ficheiros do jogo

**Problem in Context**

O problema consiste em poder alterar a bola existindo uma ligação entre os ficheiros e classes.

**The Pattern**

Aplicamos o **Strategy** pattern. Este pattern permite-nos fazer alterações na bola, a partir de um ficheiro que a cria e a altera utilizando classes de um outro ficheiro.

**Implementation**

![strategy](https://github.com/FEUP-LDTS-2022/project-l10gr04/blob/main/docs/screenshots/strategy.png)

**Consequences**

O uso de Strategy Pattern neste design permite os seguintes benefícios:

- Permite que a bola seja criada com uma direção inicial aleatória.
- Permite que a bola mude de direção consoante os acontecimentos do jogo.
- Permite mudar a velocidade da bola consoante o nível.

### CODE SMELLS AND REFACTORING

#### **Large Class**

A classe GameBuild contém muitos métodos. Neste caso, nós achamos justificável pois esta classe é a principal classe do programa e precisa de guardar uma grande quantidade de dados e necessita de vários métodos para a interface do jogo. No entanto, para ajudar a perceber mais facilmente esta classe, organizamos os métodos por "secções".  
Uma possível solução para este smell code seria aplicar o refactoring **Extract Class**, criando, por exemplo, uma classe para os métodos dos diferentes menus.  

#### **Long Method**

O método updateGame() (classe GameBuild) contém muitas linhas de código, pois é onde se passa a parte mais importante do jogo, em que temos de fazer diversas comparações no que toca a colisões, movimentos, fim de jogo, etc.
Uma possível solução para este smell code seria aplicar o refactoring **Extract Method**, dividindo o método em várias partes.

#### **Duplicate Code**

O método updateGame() (classe GameBuild) contém código idêntico, para verificar o estado dos Paddles quando o jogo se encontra no modo Singleplayer ou Multiplayer.  
Uma possível solução para este smell code seria aplicar o refactoring **Extract Method**, dividindo o método em dois métodos, consoante nos encontramos no Singleplayer e no Multiplayer.

Foi feito um refactoring nos métodos que abordavam o paddle do jogador 2 e do computador que apresentavam um código idêntico para ambos. Para resolver este smell code foi aplicado o refactoring **Extract Method**.

#### **Switch Statements**

O método move (classe Ball) contém um complexo switch operator, para definir o movimento da bola no jogo.  
Nós achamos este smell code justificável pois era a maneira mais prática de avaliar os movimentos possíveis da bola durante o jogo.

#### **Alternative Classes with Different Interfaces**

As classes Paddle e PaddleMultiplayer são idênticas, realizando as mesmas funções, só que para Paddles diferentes.  
Uma solução seria criar uma classe geral que conseguissemos aplicar aos dois paddles diferentes.

#### **Dead Code**

Dentro das diferentes classes foi apagado código que já não era utilizado para o jogo criado.

#### **Comments**

Alguns métodos dentro da classe Game tinham comentários para explicar os diferentes métodos. Para resolver este smell code foi aplicado o refactoring **Rename Method**.

### TESTING

#### **Screenshot of coverage report**

![PongTest](https://github.com/FEUP-LDTS-2022/project-l10gr04/blob/main/docs/screenshots/tests-coverage.PNG)

#### **Link to mutation testing report and screenhot**

[Mutation Report](https://github.com/FEUP-LDTS-2022/project-l10gr04/tree/main/docs/pitest-report)  
![MutationReport](https://github.com/FEUP-LDTS-2022/project-l10gr04/blob/main/docs/screenshots/pitest.PNG)

### SELF-EVALUATION

O trabalho foi dividido de maneira igual por todos os elementos do grupo, e toda a gente contribuiu para o mesmo.   
Este trabalho ajudou-nos a enriquecer os nossos conhecimentos em java, design patterns e smell codes, tal como o nosso trabalho em equipa.

 - João Botelho: 33.3%
 - Pedro Paixão: 33.3%
 - Rafael Alves: 33.3%
 
