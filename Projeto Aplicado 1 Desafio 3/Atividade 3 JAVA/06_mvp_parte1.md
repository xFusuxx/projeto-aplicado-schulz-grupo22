# 06 - MVP Parte 1

## Sistema de Gestão de Calibração -- Schulz S.A.

**Curso:** Análise e Desenvolvimento de Sistemas\
**Disciplina:** Projeto Aplicado I\
**Projeto:** Sistema de Gestão de Calibração\
**Linguagem:** Java\
**Banco de dados:** SQLite\
**IDE:** NetBeans

### Integrantes do grupo

-   José Altair Araujo Camargo
-   Guilherme Ceschiatti Barbosa Moreira
-   Davi da Silva Simao
-   Maurício Fusuma Oliveira
-   Rafael Nunes da Fonseca

------------------------------------------------------------------------

## 1. Objetivo do MVP

O objetivo deste MVP é desenvolver a primeira versão funcional de um
sistema para auxiliar o processo de gestão de calibração de instrumentos
da Schulz S.A.

O sistema permite cadastrar técnicos, equipamentos e medições, armazenar
informações em um banco de dados SQLite e consultar os resultados das
calibrações.

A proposta busca reduzir a necessidade de registros manuais e facilitar
a consulta das informações relacionadas aos processos de calibração.

## 2. Programação Orientada a Objetos (POO)

O desenvolvimento foi realizado utilizando os conceitos de Programação
Orientada a Objetos em Java.

Foram utilizadas classes para representar os principais elementos do
sistema, separando os dados e as responsabilidades de cada parte do
programa.

O encapsulamento foi aplicado por meio de atributos privados (`private`)
e métodos públicos getters, permitindo consultar os dados dos objetos
sem acessar diretamente seus atributos.

### 2.1 Classe Tecnico

Representa os profissionais responsáveis pelas medições.

**Atributos:** - `idTecnico` - `matricula` - `nomeTecnico` - `email` -
`setor`

A classe possui um construtor para inicializar os dados e métodos
getters para consultar os atributos.

### 2.2 Classe Equipamento

Representa os instrumentos utilizados nos processos de calibração.

**Atributos:** - `idEquipamento` - `codigoTag` - `nomeEquipamento` -
`fabricante` - `modelo`

Assim como a classe `Tecnico`, possui construtor e métodos getters.

### 2.3 Classe Medicao

Representa os resultados obtidos durante uma calibração.

**Atributos:** - `idMedicao` - `idTecnico` - `idEquipamento` -
`parametroMedido` - `valorNominal` - `toleranciaMin` - `toleranciaMax` -
`valorObtido` - `statusResultado` - `dataMedicao`

A classe possui um método privado chamado `calcularStatus()`,
responsável por verificar se o resultado da medição está dentro dos
limites de tolerância.

O cálculo utilizado é:

``` text
erro = valorObtido - valorNominal
```

O resultado é definido automaticamente como:

-   **APROVADO:** quando o erro da medição está dentro dos limites
    estabelecidos.
-   **REPROVADO:** quando o erro está fora dos limites estabelecidos.

Antes do cálculo, o sistema verifica se a tolerância mínima não é maior
que a tolerância máxima.

## 3. Conexão e persistência dos dados

Para armazenar as informações, foi utilizado o banco de dados SQLite.

A classe `ConexaoBanco` é responsável por estabelecer a conexão com o
arquivo do banco de dados por meio do JDBC.

A classe `PersistenciaBanco` concentra as operações de comunicação entre
o sistema Java e o banco de dados.

Foram utilizados os recursos:

-   `Connection`: estabelece a conexão com o banco.
-   `PreparedStatement`: prepara e executa os comandos SQL.
-   `ResultSet`: recebe os registros retornados pelas consultas.
-   `try-with-resources`: permite fechar automaticamente os recursos
    utilizados.

## 4. Operações CRUD

Foram implementadas operações CRUD para as tabelas de técnicos,
equipamentos e medições.

CRUD representa quatro operações fundamentais:

  Operação   Comando SQL   Finalidade
  ---------- ------------- ---------------------
  Create     INSERT        Inserir registros
  Read       SELECT        Consultar registros
  Update     UPDATE        Atualizar registros
  Delete     DELETE        Excluir registros

### 4.1 Operações implementadas

**Técnicos:** - `inserirTecnico()` - `listarTecnicos()` -
`atualizarTecnico()` - `excluirTecnico()`

**Equipamentos:** - `inserirEquipamento()` - `listarEquipamentos()` -
`atualizarEquipamento()` - `excluirEquipamento()`

**Medições:** - `inserirMedicao()` - `listarMedicoes()` -
`atualizarMedicao()` - `excluirMedicao()`

Também foram desenvolvidos métodos auxiliares para localizar registros e
evitar duplicidade durante a execução do programa:

-   `buscarIdTecnico()`
-   `buscarIdEquipamento()`
-   `medicaoExiste()`

As consultas utilizam parâmetros no `PreparedStatement`, evitando a
montagem direta de comandos SQL com valores fornecidos pelo programa.

## 5. Relacionamento entre as tabelas

O banco de dados utiliza três tabelas principais:

-   `tecnico`
-   `equipamento`
-   `medicao`

A tabela `medicao` possui as chaves estrangeiras `id_tecnico` e
`id_equipamento`, relacionando cada medição ao profissional responsável
e ao equipamento utilizado.

Esse relacionamento permite consultar informações das três tabelas em
uma única consulta SQL.

## 6. Diagrama de classes

O relacionamento entre as classes pode ser representado de forma
simplificada:

``` text
+----------------------+
|       Tecnico        |
+----------------------+
| - idTecnico          |
| - matricula          |
| - nomeTecnico        |
| - email              |
| - setor              |
+----------------------+
| + getters()          |
+----------------------+

+----------------------+
|     Equipamento      |
+----------------------+
| - idEquipamento      |
| - codigoTag          |
| - nomeEquipamento    |
| - fabricante         |
| - modelo             |
+----------------------+
| + getters()          |
+----------------------+

+----------------------+
|       Medicao        |
+----------------------+
| - idMedicao          |
| - idTecnico          |
| - idEquipamento      |
| - parametroMedido    |
| - valorNominal       |
| - toleranciaMin      |
| - toleranciaMax      |
| - valorObtido        |
| - statusResultado    |
| - dataMedicao        |
+----------------------+
| - calcularStatus()   |
| + getters()          |
+----------------------+

+----------------------+
|     ConexaoBanco     |
+----------------------+
| + conectar()         |
+----------------------+

+----------------------+
|   PersistenciaBanco  |
+----------------------+
| + inserirTecnico()   |
| + listarTecnicos()   |
| + atualizarTecnico() |
| + excluirTecnico()   |
| + inserirEquipamento()|
| + listarEquipamentos()|
| + atualizarEquipamento()|
| + excluirEquipamento()|
| + inserirMedicao()   |
| + listarMedicoes()   |
| + atualizarMedicao() |
| + excluirMedicao()   |
+----------------------+

+----------------------+
|         Main         |
+----------------------+
| + main()             |
+----------------------+
```

A classe `Main` inicia a execução do sistema, cria os objetos e utiliza
a classe `PersistenciaBanco` para realizar as operações no SQLite.

A classe `PersistenciaBanco` utiliza a `ConexaoBanco` para acessar o
banco de dados.

## 7. Tratamento de erros e validações

As operações de banco de dados utilizam a exceção `SQLException`,
declarada nos métodos por meio de `throws SQLException`.

Os recursos de conexão e execução SQL são utilizados com
`try-with-resources`, permitindo seu fechamento automático ao final das
operações.

Na classe `Medicao`, foi implementada uma validação para impedir que a
tolerância mínima seja maior que a tolerância máxima.

Quando essa condição ocorre, o sistema lança uma exceção
`IllegalArgumentException`.

Também foi implementado o cálculo automático do resultado da medição,
verificando se o erro está dentro dos limites permitidos.

## 8. Funcionamento do Main

A classe `Main` é responsável por demonstrar o funcionamento do MVP.

Durante sua execução, o programa:

1.  Cria o objeto responsável pela persistência dos dados.
2.  Cria objetos das classes `Tecnico` e `Equipamento`.
3.  Verifica se o técnico já está cadastrado.
4.  Verifica se o equipamento já está cadastrado.
5.  Recupera os respectivos IDs no banco de dados.
6.  Cria um objeto da classe `Medicao`.
7.  Calcula automaticamente o resultado da medição.
8.  Verifica se a medição já existe antes de inseri-la.
9.  Executa a consulta de relatório utilizando `INNER JOIN`.

A verificação de existência evita que a execução repetida do exemplo de
teste cadastre novamente os mesmos registros, considerando os critérios
definidos no método `medicaoExiste()`.

## 9. Consulta utilizando INNER JOIN

Foi desenvolvida uma consulta SQL utilizando `INNER JOIN` para
relacionar as tabelas `medicao`, `tecnico` e `equipamento`.

Com essa consulta, o sistema consegue apresentar em um único relatório:

-   Identificação da medição.
-   Nome do técnico responsável.
-   Código TAG do equipamento.
-   Nome do equipamento.
-   Parâmetro medido.
-   Valor nominal.
-   Limites de tolerância.
-   Valor obtido.
-   Resultado da calibração.
-   Data da medição.

Os resultados são recebidos pelo `ResultSet` e apresentados no terminal
por meio de uma estrutura de repetição `while`.

## 10. Testes e validação do MVP

Foram realizados testes de execução para verificar o funcionamento das
principais funcionalidades.

Os testes demonstraram:

-   Criação dos objetos Java.
-   Consulta de técnicos e equipamentos existentes.
-   Inserção de novos registros quando não encontrados.
-   Verificação de existência da medição antes da inserção.
-   Cálculo automático dos resultados das medições.
-   Classificação de resultados como APROVADO ou REPROVADO.
-   Atualização e exclusão de registros por meio dos métodos CRUD.
-   Consulta dos dados relacionados utilizando `INNER JOIN`.
-   Exibição do relatório de calibração no terminal.

Durante os testes, foi possível verificar resultados aprovados e
reprovados de acordo com os limites de tolerância configurados.

## 11. Conclusão

O desenvolvimento do MVP Parte 1 permitiu implementar a estrutura
inicial do sistema de gestão de calibração utilizando Java e SQLite.

As classes foram desenvolvidas aplicando conceitos de Programação
Orientada a Objetos, com separação de responsabilidades e encapsulamento
dos atributos.

A integração com o banco de dados possibilitou implementar as operações
CRUD, validar os resultados das medições e consultar informações
relacionadas por meio de `JOIN`.

Com isso, foi construída uma primeira versão funcional do sistema, que
poderá receber melhorias e novas funcionalidades nas próximas etapas do
projeto.
