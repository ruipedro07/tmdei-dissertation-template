# Engenharia de Requisitos — Sistema Spring AI

## 1. Introdução
- **Objetivo do documento**
- **Escopo do sistema**
- **Definições, acrônimos e abreviações**
- **Referências**

## 2. Descrição Geral
- **Perspectiva do produto**
- **Funções do sistema**
- **Características dos usuários**
- **Restrições**
- **Suposições e dependências**

## 3. Requisitos Funcionais


| Código | Requisito | Regras de Negócio |
|--------|-----------|--------------------|
| RF01 | O sistema deve prestar auxílio à equipa de suporte através de um chatbot para consultas de regras de negócio e recomendação na resolução de incidentes. | - O chatbot deve recolher o **top 3** de resultados mais relevantes da documentação. <br> - Cada resultado deve incluir os seguintes metadados: **nome**, **data**, **fonte** e **link**. |
| RF02 | Deve existir um portal de administração para que utilizadores com privilégios possam injetar e gerir documentação manualmente. | - A injeção automática deve aceitar ficheiros nos formatos: **PDF**, **Markdown**, **TXT** e **Word**. |
| RF03 | O sistema deve executar um processo automático para extrair, indexar e armazenar documentação atualizada. | - O processo de extração, indexação e armazenamento deve ser **diário** e **automático**. |
| RF04 | O portal de administração deve permitir controlo sobre o processo de extração automática. | - Deve existir um sistema de **exclusão de documentos** com base em **keywords** ou **metadados específicos** (nome, link, etc). <br> - Deve ser possível configurar quais **fontes de extração** serão utilizadas. <br> - Deve ser possível definir o **intervalo de inclusão**, como por exemplo um **space específico no Confluence**. |


## 4. Requisitos Não Funcionais
- RNF01 — [Descrição do requisito não funcional 1]
- RNF02 — [Descrição do requisito não funcional 2]
- ...

## 5. Casos de Uso
- **Identificação dos atores**
- **Descrição dos principais casos de uso**
- **Diagramas de casos de uso**

## 6. Regras de Negócio
- [Listar regras de negócio relevantes]

## 7. Modelos e Diagramas
- **Diagrama de contexto**
- **Diagrama de classes**
- **Outros diagramas relevantes**

## 8. Critérios de Aceitação
- [Critérios para validação dos requisitos]

## 9. Rastreabilidade dos Requisitos
- **Matriz de rastreabilidade**

## 10. Anexos
- [Documentos complementares, glossário, etc.]