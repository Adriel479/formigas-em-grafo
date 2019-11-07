# Formigas em Grafos

Formigas em Grafos é um jogo educacional desenvolvido para apoior o ensino e aprendizagem dos algoritmos de busca em largura e busca em profundidade.

Para baixar o executável acesse: jar/formigas-em-grafo-teste.jar

# O jogo

O objetivo do jogo é realizar a alimentação de vários formigueiros antes que eles entrem em colapso. No jogo, cada formigueiro possui um tempo de vida que é reduzido dependendo da sua localização no mapa com intuito de favorecer o ambiente para utilização do algoritmo de busca em largura ou em profundidade. Atualmente estão implementadas doze fases, sendo seis para cada método de busca.

Para cada fase existe um mapa diferente e o jogador é livre para se mover por ele, mas ao sair da trilha entre os formigueiros, a formiga fica lenta e desorientada, comprometendo o seu deslocamento e dificultando a chegada até os formigueiros. O mapa é composto por vários formigueiros representando os vértices de um grafo que são conectados pelo feromônio das formigas. 

![Primeira fase da busca em largura](https://raw.githubusercontent.com/Adriel479/formigas-em-grafo/master/recursos-visuais-tralha/imagens-do-jogo/primeira-fase-busca-em-largura.png)

**Figura 01:** Primeira fase da busca em largura.

Durante as fases é esperado que o jogador aplique a sequência de visitação dos algoritmos. Entretanto, quando a sequência de visitação não é seguida como esperado, o jogador é penalizado na sua pontuação e o tempo de vida dos formigueiros que deveriam ser visitados é severamente acelerado, fazendo com que entrem em colapso. O jogador só consegue avançar para a próxima fase quando todos os formigueiros são visitados e alimentados à tempo.

Antes de chegar às fases de um desafio, o jogador visualiza uma breve descrição sobre os algoritmos e lhe é mostrado uma pequena animação da mecânica do jogo. O intuito é orientar o usuário sobre as regras que ele precisa seguir. Portanto, a ferramenta não disponibiliza um material completo sobre os algoritmos e sua implementação, mas possibilita que o discente internalize o funcionamento concreto da movimentação executada pelos algoritmos abstratos, por meio da alimentação dos formigueiros.



# Autor

  - **Nome:** Adriel Vieira Santos
  - **E-mail:** adriel.klt@gmail.com

License
----
MIT


