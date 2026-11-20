# Projeto Aplicado I — Sistema de Calibração Metrológica

**Parceria**: Schulz S.A. & UniSENAI  
**Tecnologias**: Python 3 | SQLite 3 | Programação Orientada a Objetos (POO)  
**Avaliação**: AV 03 — Construção do MVP (Parte 1)

---

## 📌 Sobre o Projeto

O **Sistema de Calibração Metrológica** é um estudo de caso e MVP desenvolvido no âmbito do Projeto Aplicado I para a **Schulz S.A.** O objetivo do sistema é automatizar e padronizar o registro de calibração de instrumentos de medição (como máquinas MMC e micrômetros digitais), substituindo processos manuais de transcrição de dados e reduzindo falhas humanas na validação de qualidade de componentes industriais.

A solução integra um banco de dados relacional **SQLite** a uma arquitetura baseada em **Programação Orientada a Objetos (POO)** em Python, permitindo o registro de técnicos, equipamentos e medições, a verificação automática de regras de tolerância metrológica e a geração de relatórios consolidados.

---

## 📂 Estrutura do Repositório

O repositório é organizado de forma sequencial, cobrindo desde a modelagem conceitual do banco de dados até a implementação do MVP em POO:

```text
.
├── 01_estrutura.md       # Documentação de modelagem conceitual e justificativa relacional
├── 02_dicionario.md      # Dicionário de dados do SQLite (tipos, chaves e restrições)
├── 03_banco.sql          # Script DDL com comandos CREATE TABLE e restrições FK/CHECK
├── 04_classes.py         # Mapeamento POO (Tecnico, Equipamento, Medicao) e camada DAO
├── 05_main.py            # Script principal de execução, teste e relatório (JOIN)
├── 06_mvp_parte1.md      # Documentação técnica final da entrega AV 03
└── README.md             # Instruções e visão geral do projeto
```

---

## ⚙️ Regras de Negócio e Metrologia

O sistema valida automaticamente o status de cada medição efetuada no processo produtivo:

1. **Cálculo do Erro Metrológico**:
   $$\text{Erro Calculado} = \text{Valor Obtido} - \text{Valor Nominal}$$

2. **Critério de Aprovação**:
   $$\text{Tolerância Mínima} \le \text{Erro Calculado} \le \text{Tolerância Máxima}$$

3. **Status de Resultado**:
   - **`APROVADO`**: O erro calculado está estritamente dentro da faixa de tolerância do instrumento.
   - **`REPROVADO`**: O erro violou os limites inferior ou superior de tolerância.

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
* **Python 3.8+** instalado.
* Não é necessária a instalação de dependências externas (`pip`), pois o projeto utiliza exclusivamente a biblioteca padrão `sqlite3`.

### Passos para Execução

1. **Clonar ou baixar o repositório**:
   Garanta que os arquivos `04_classes.py` e `05_main.py` estejam no mesmo diretório.

2. **Navegar até a pasta do projeto**:
   ```bash
   cd caminho/para/o/projeto
   ```

3. **Executar o script principal**:
   * **Windows**:
     ```cmd
     python 05_main.py
     ```
   * **Linux / macOS**:
     ```bash
     python3 05_main.py
     ```

### O que esperar da execução:
* O sistema inicializará o banco de dados `schulz_calibracao.db`.
* As tabelas `tecnico`, `equipamento` e `medicao` serão estruturadas via DDL.
* Registros de demonstração serão inseridos nas entidades.
* A regra de tolerância será avaliada em tempo real.
* O relatório final consolidado com consulta `JOIN` será exibido no terminal.

---

## 📊 Modelo Relacional do Banco de Dados

O banco SQLite é composto por 3 tabelas relacionadas com integridade referencial:

| Tabela | Função | Chave Primária | Chaves Estrangeiras |
| :--- | :--- | :--- | :--- |
| **`tecnico`** | Cadastro de calibradores e operadores | `id_tecnico` | — |
| **`equipamento`** | Cadastro dos instrumentos e TAGs | `id_equipamento` | — |
| **`medicao`** | Registros de ensaio e erro metrológico | `id_medicao` | `id_equipamento`, `id_tecnico` |

---

## 📜 Licença e Créditos

Projeto desenvolvido como parte da avaliação **AV 03** da disciplina de **Projeto Aplicado I (UniSENAI)** em cooperação técnica com a **Schulz S.A.**
