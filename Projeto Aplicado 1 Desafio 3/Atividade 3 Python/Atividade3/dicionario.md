# Projeto Aplicado I — Schulz S.A.
## Entrega 02 — Dicionário de Dados — SQLite

### Tabela `tecnico`
| Campo | Tipo | Chave / restrição | Descrição |
|---|---|---|---|
| `id_tecnico` | INTEGER | PK AUTOINCREMENT | Identificador único |
| `nome_tecnico` | TEXT | NOT NULL | Nome do executor |
| `matricula` | TEXT | NOT NULL UNIQUE | Matrícula do colaborador |
| `email` | TEXT | NOT NULL | Contato institucional |
| `setor` | TEXT | NOT NULL | Área de atuação |

### Tabela `equipamento`
| Campo | Tipo | Chave / restrição | Descrição |
|---|---|---|---|
| `id_equipamento` | INTEGER | PK AUTOINCREMENT | Identificador único |
| `codigo_tag` | TEXT | NOT NULL UNIQUE | Código do dispositivo |
| `nome_equipamento` | TEXT | NOT NULL | Denominação do instrumento |
| `fabricante` | TEXT | NOT NULL | Fabricante |
| `modelo` | TEXT | NOT NULL | Modelo do instrumento |

### Tabela `medicao`
| Campo | Tipo | Chave / restrição | Descrição |
|---|---|---|---|
| `id_medicao` | INTEGER | PK AUTOINCREMENT | Identificador único |
| `id_equipamento` | INTEGER | NOT NULL FK | Referência ao equipamento |
| `id_tecnico` | INTEGER | NOT NULL FK | Referência ao técnico executor |
| `parametro_medido` | TEXT | NOT NULL | Característica avaliada |
| `valor_nominal` | REAL | NOT NULL | Valor de referência |
| `tolerancia_min` | REAL | NOT NULL | Limite inferior do erro |
| `tolerancia_max` | REAL | NOT NULL | Limite superior do erro |
| `valor_obtido` | REAL | NOT NULL | Resultado obtido na medição |
| `status_resultado` | TEXT | NOT NULL CHECK | APROVADO ou REPROVADO |
| `data_medicao` | DATETIME | DEFAULT CURRENT_TIMESTAMP | Data e hora do registro |

## Regras de negócio
* Código/tag do equipamento e matrícula do técnico são únicos.
* Medição deve referenciar um técnico e equipamento existentes.
* Status limitado a APROVADO ou REPROVADO.
* Tolerância aplicada sobre o erro (valor obtido - valor nominal).
