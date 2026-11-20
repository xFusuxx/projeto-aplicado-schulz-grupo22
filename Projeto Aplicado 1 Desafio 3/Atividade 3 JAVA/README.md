# Sistema de Gestão de Calibração – Schulz S.A.

## Projeto Aplicado I – Análise e Desenvolvimento de Sistemas

Este projeto tem como objetivo desenvolver uma aplicação inicial para auxiliar o processo de gestão de calibração de instrumentos da Schulz S.A.

O sistema foi desenvolvido em Java, utilizando Programação Orientada a Objetos (POO) e banco de dados SQLite.

## Funcionalidades implementadas

- Cadastro de técnicos.
- Consulta de técnicos.
- Atualização e exclusão de técnicos.
- Cadastro de equipamentos.
- Consulta de equipamentos.
- Atualização e exclusão de equipamentos.
- Registro de medições.
- Consulta, atualização e exclusão de medições.
- Cálculo automático do resultado da medição.
- Classificação da medição como `APROVADO` ou `REPROVADO`.
- Consulta de medições relacionadas a técnicos e equipamentos por meio de `INNER JOIN`.
- Exibição de relatório de calibração no terminal.

## Tecnologias utilizadas

- Java
- SQLite
- JDBC
- NetBeans
- Driver SQLite JDBC (Xerial)

## Estrutura principal do projeto

```text
src/
└── br/com/shulz/calibracao/
    ├── Main.java
    ├── Tecnico.java
    ├── Equipamento.java
    ├── Medicao.java
    ├── ConexaoBanco.java
    └── PersistenciaBanco.java

banco/
└── schulz_calibracao.db

06_mvp_parte1.md
README.md
```

## Como executar

1. Abra o projeto no Apache NetBeans.
2. Confira se a biblioteca do driver SQLite JDBC está adicionada ao projeto.
3. Verifique se a pasta `banco` existe na estrutura do projeto.
4. Execute a classe `Main.java`.
5. Confira no terminal as mensagens de execução e o relatório de calibração.

O caminho do banco de dados utilizado pela aplicação é:

```text
banco/schulz_calibracao.db
```

## Regra de avaliação da medição

O sistema calcula o erro da medição pela diferença entre o valor obtido e o valor nominal:

```text
erro = valorObtido - valorNominal
```

O resultado será `APROVADO` quando o erro estiver entre a tolerância mínima e a tolerância máxima. Caso contrário, será `REPROVADO`.

## Persistência e consulta

As operações de inclusão, consulta, atualização e exclusão são realizadas pela classe `PersistenciaBanco`.

A consulta do relatório utiliza `INNER JOIN` para relacionar as tabelas de medições, técnicos e equipamentos.

## Integrantes

- José Altair Araujo Camargo
- Guilherme Ceschiatti Barbosa Moreira
- Davi da Silva Simao
- Maurício Fusuma Oliveira
- Rafael Nunes da Fonseca

---

**Observação:** este repositório contém a primeira versão funcional (MVP) do sistema, desenvolvida no contexto da disciplina Projeto Aplicado I.
