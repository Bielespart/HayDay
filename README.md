# HayDay
Este é um projeto envolvendo a criação de um sistema para controlar a safra de uma fazenda fictícia chamada "Fazenda Esperança"

## Diario da IA

### 1. Ferramentas e bibliotecas proibidas que a IA tentou incluir

Durante o desenvolvimento, a IA chegou a sugerir ou usar recursos que poderiam ficar fora do padrao estudado na disciplina, principalmente:

- Interface grafica com Swing, na classe `TelaTeste`, usada apenas como tela separada para testes rapidos.
- Uso de classes especificas de arquivo como `File`, `FileReader`, `FileWriter`, `BufferedReader` e `BufferedWriter`.
- Uso de `try/catch` e `try-with-resources` para tratar erros de leitura e escrita.
- Uso de estruturas mais prontas, como `ArrayList`, presentes no modulo de relatorios.

Esses recursos foram analisados porque o objetivo principal do trabalho era manter o sistema dentro do conteudo visto em aula: funcoes, modularizacao, vetores, lacos, condicionais e persistencia em arquivos `.txt`.

### 2. Prompt usado para obrigar a IA a refatorar o codigo para o padrao da disciplina

O prompt usado foi:

```text
Em primeiro momento, apenas faça uma analise de que tudo que você utilizou no codigo,
nós já aprendemos, está tudo dentro desse arquivo, não delete nada do código nem faça alterações.
```

Depois da analise, a orientacao foi manter o codigo no padrao da disciplina, evitando banco de dados e usando apenas arquivos `.txt` para salvar os dados.

### 3. Regra de negocio que a IA falhou e precisou ser debugada

A regra de negocio principal que precisou ser corrigida foi a validacao do registro de colheita.

O sistema precisava verificar se:

- a matricula do funcionario ja estava cadastrada;
- o codigo do talhao ja estava cadastrado;
- a placa do trator ja estava cadastrada;
- a quantidade de litros colhidos nao ultrapassava a capacidade da carreta do trator.

No inicio, o registro de colheita aceitava dados sem validar corretamente a capacidade do trator. O grupo precisou identificar esse problema e ajustar a logica para bloquear o registro quando o volume informado fosse maior que a capacidade cadastrada.

Exemplo da regra corrigida:

```text
Se o trator tem capacidade de 500 litros, uma colheita de 600 litros nao pode ser registrada.
```
