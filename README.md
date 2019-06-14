# LI3-Java
GestVendas

Introdução

O presente trabalho prático desenvolve-se no âmbito da Unidade Curricular Laboratórios de Informática III, lecionada no 2º semestre do 2º ano do curso de Engenharia Informática.
Este trabalho tem como objetivo aumentar os conhecimentos na linguagem Java e, fundamentalmente, a apresentação dos desafios que se colocam a quem concebe e programa aplicações software com grandes volumes de dados e com a mais elevada complexidade algorítmica e estrutural. 
Procura-se a criação de uma aplicação de uma aplicação Desktop em Java, baseada na utilização das interfaces e das coleções de JCF (“Java Collections Framework”), cujo objetivo é a realização de consultas interativas e estatísticas de informações relativas à gestão de uma cadeia de distribuição, com vista a facilitar a organização e aumentar o rendimento.
O mesmo trabalho prático reveste-se de uma mais valia para colocar em prática conhecimentos, fomentar e consolidar aprendizagens e desbravar terreno no mundo da linguagem Java e do paradigma dos objetos. 
 
Modelo Visão Controlador (MVC)

Para a realização deste projeto, seguiu-se a recomendação dada pelos docentes da Unidade Curricular. Implementou-se a arquitetura de software MVC, que consiste na separação entre a informação da interação do utilizador com ela.
Tem como vantagens a fácil reutilização de código, melhor performance – fruto da separação das camadas -, melhor desempenho e produtividade - devido à estrutura de pacotes modulares - permitindo que os programadores possam trabalhar em paralelo e que as partes da aplicação possam ser alteradas sem que haja necessidade de alterar outras.
Tem como principal desvantagem a necessidade de mais tempo para explorar e modelar o sistema.

 
Modelo

A principal função do modelo é o armazenamento e tratamento de dados.
Assim sendo, dividimos o nosso modelo em dois submodelos, os ModelosBase (ver Anexo – Figura 1) que estruturam e validam a informação de toda a aplicação e os Catalogos (ver Anexo – Figura 2) que armazenam e tratam toda a informação. 
Neste package possuímos uma classe agregadora, denominada SGV, com a finalidade de incluir todos os catálogos com o intuito de preservar o encapsulamento. Nesta classe, é realizada a leitura (carregamento da informação dos ficheiros de texto).

Modelos Base

Cliente
A classe Cliente (ver Anexo - Figura 7) é uma simples estrutura de dados, utilizada para validar uma linha lida do ficheiro “Clientes.txt”, contendo uma String como variável de instância a qual representa um código de cliente.

Produto
A classe Produto (ver Anexo – Figura 8) é uma simples estrutura de dados, utilizada para validar uma linha lida do ficheiro “Produtos.txt”, contendo uma String como variável de instância a qual representa um código de produto. 

Venda
A classe Venda (ver Anexo – Figura 10), que a sua principal função é validar uma linha de venda, lida de um ficheiro de texto “Vendas_.txt”, para que posteriormente os dados validados possam ser armazenados nos catálogos.

Fatura
A classe Fatura (ver Anexo – Figura 9) tem como funcionalidade armazenar os totais de unidades vendidas e o faturado, diferenciando-os em tipo ‘N’, normal, ou ‘P’, em promoção.
 
Módulos de Dados
Catálogo de Produtos 
(ver Anexo – Figura 4 e Figura 12)
Para a realização deste módulo de dados, julgou-se de relevante interesse a utilização de um TreeSet de Produtos, ordenado alfabeticamente utilizando um Comparator denominado compProdutoCodigo, de modo a que a validação da existência de um produto na estrutura seja mais eficiente e que cada elemento é único. 

Catálogo de Clientes 
(ver Anexo – Figura 3 e Figura 11)
Para a realização deste módulo de dados, julgou-se pertinente a utilização de um TreeSet de Clientes, ordenado alfabeticamente utilizando um Comparator denominado compClienteCodigo, de modo a que a validação da existência de um produto na estrutura seja mais eficiente e que cada elemento é único.

Faturação Global 
(ver Anexo – Figura 5 e Figura 13) 
Esta classe tem como principal função determinar o total faturado para todos os produtos existentes, para tal utilizou-se um HashMap onde a chave é um código de produto e o valor uma Matriz, onde as linhas representam os meses e as colunas as filiais, responsável por armazenar instâncias da classe Fatura. Assim para todos os produtos, é possível determinar o total faturado e o número de quantidades vendidas, tendo em conta os meses e as filiais.

Gestão de Filial 
(ver Anexo – Figura 6 e Figura 14) 
Esta classe tem como variáveis de instância um HashMap, cujas chaves são os códigos de clientes que realizaram compras, e os valores são HashMap’s, cujas chaves são os códigos dos produtos que o cliente comprou e o valor é uma instância da classe Matriz. Esta Matriz tem como dimensão o número de meses por número de filiais e armazena objetos do tipo Fatura. Sempre que uma venda é acrescentada é acedido a matriz através dos códigos de cliente e produtos desta, e a fatura na posição mês-filial é atualizada com a quantidade de unidades vendidas e o total faturado dessa venda, dependendo se é do tipo N ou P (inicialmente a fatura é inicializada com quantidade e faturado a 0).


Visão
(ver Anexo – Figura 15 a Figura 18)
A visão gera uma representação dos dados presentes no Modelo solicitado, exibindo-os ao utilizador.
Aqui, temos implementados todos os menus e diferentes modos de Listagem (de uma String e de uma lista de String’s). 
Controlador
A missão do controlador é enviar comandos para o Modelo, com a finalidade de atualizar o seu estado, e para a Visão, com o intuito de alterar a visão da informação no Modelo.
Assim, toda a lógica de negócio é implementada neste módulo. 
Performance

Como critérios para a escolha das melhores estruturas, usou-se o tempo despendido e o espaço em memória gasto.
Como tal fizemos dez testes (ver Anexo – Figura 19) para cada estrutura e realizou-se a média entre os diferentes tempos obtidos e a média entre os diferentes espaços ocupados.
É de realçar que nem sempre o melhor tempo significa que seja a melhor estrutura. A título exemplificativo, temos o catálogo de produtos e de clientes. O TreeSet é a estrutura mais demorada na sua povoação, em contrapartida é a mais rápida ao validar as vendas.
 
Conclusão

Com a realização deste trabalho desenvolvemos os nossos conhecimentos na linguagem de Java e no tratamento de grandes quantidades de dados, que nos criou dificuldades em termos de espaço na memória. 
O facto de validar os códigos existentes nas vendas tornou-se bastante demorado e deve ser implementada uma forma mais otimizada. Outra alteração a fazer seria o facto de o total faturado de uma venda não estar a multiplicar pelas unidades vendidas, isto por erro de compreensão nosso, que entendemos que o faturado era da venda de todas as unidades. A implementação MVC mostrou-se bastante útil na divisão de tarefas entre membros e mostrou-se ser uma boa técnica de programação devido às vantagens anteriormente referidas. 
Em forma de conclusão, o trabalho em geral foi concretizado, faltando apenas a validação, a questão do faturado e uma melhor gestão de memória, aspetos esses que deveriam ter sido melhorados e solucionados.
