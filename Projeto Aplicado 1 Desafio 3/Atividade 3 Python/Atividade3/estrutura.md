# Projeto Aplicado I — Schulz S.A.
## Entrega 02 — Modelagem e Estruturação de Banco de Dados

**Grupo:** 22  
**Integrantes:** José Altair Araujo Camargo; Guilherme Ceschiatti Barbosa Moreira; Davi da Silva Simao; Maurício Fusuma Oliveira; Rafael Nunes da Fonseca  
**Orientadores:** Prof. MSc. Hugo Menezes Barra e Prof. Rodrigo Losina  

## 1. Contexto e problema
No Desafio 1, identificamos que o técnico executante realiza as medições na Máquina de Medição por Coordenadas (MMC), enquanto outro técnico transfere manualmente os resultados para o certificado.
Essa transferência manual aumenta o tempo de emissão e pode provocar erros de digitação, associação incorreta de parâmetros e retrabalho.
O responsável pela área continua encarregado da conferência e assinatura do certificado.
O objetivo desta etapa é organizar o armazenamento dos dados para apoiar a futura importação, validação e emissão automatizada de certificados em PDF.
O banco relacional escolhido no roteiro é o SQLite.

## 2. Entidades principais
### 2.1 Técnico
A tabela `tecnico` identifica o profissional responsável pela execução da medição.
Contém identificador, nome, matrícula, e-mail e setor. O cadastro é único e pode ser relacionado a diversas medições.

### 2.2 Equipamento
A tabela `equipamento` registra o instrumento ou dispositivo calibrado.
Armazena identificador, código/tag, denominação, fabricante e modelo. A tag é única para evitar duplicação.

### 2.3 Medição
A tabela `medicao` guarda cada resultado por parâmetro medido.
Relaciona um equipamento e um técnico por chaves estrangeiras.

## 3. Relacionamentos e integridade
Um técnico pode realizar muitas medições (1:N). Um equipamento pode ter muitas medições (1:N).
As chaves estrangeiras garantem a integridade referencial. O status fica limitado a APROVADO ou REPROVADO.

## 4. Justificativa da normalização
Separar técnico e equipamento evita repetir dados cadastrais a cada nova medição, diminuindo inconsistências e facilitando consultas.

## 5. Limites desta entrega
Este modelo é um primeiro recorte acadêmico do sistema completo. Funcionalidades como importação automática de arquivos MMC e geração de PDF serão tratadas nas próximas etapas.
