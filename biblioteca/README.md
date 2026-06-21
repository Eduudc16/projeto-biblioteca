# Sistema de Biblioteca

Projeto final em Java: sistema orientado a objetos, iterativo, baseado
em terminal, que manipula informações em arquivos de texto.

## Tema

Um sistema de biblioteca simples, com **livros** e **usuários**.
O usuário pode cadastrar livros e pessoas, e alugar/devolver livros.

## Como compilar e executar

Requer **JDK 17 ou superior** instalado.

```bash
# A partir da pasta do projeto:

# 1. Compilar
javac -d bin -encoding UTF-8 src/*.java

# 2. Executar
java -cp bin Main
```

> **Dica para Windows:** se os acentos aparecerem como `?` no Prompt
> de Comando, rode `chcp 65001` antes de executar o programa.

Ao rodar, uma pasta `dados/` é criada automaticamente com dois
arquivos: `livros.txt` e `usuarios.txt`. Eles são lidos quando o
programa inicia e regravados a cada alteração, então nada se perde
entre uma execução e outra.

## Estrutura das classes

O projeto tem só **4 classes**, todas na mesma pasta (sem pacotes),
para ficar fácil de entender:

```
src/
├── Livro.java       -> representa um livro (id, título, autor, status, quem alugou)
├── Usuario.java     -> representa um usuário cadastrado (id, nome, telefone)
├── Biblioteca.java  -> guarda as listas de livros/usuários e tem toda
│                       a lógica: cadastrar, listar, remover, alugar,
│                       devolver + salvar/carregar os arquivos
└── Main.java        -> só o menu, que chama os métodos de Biblioteca
```

### Como as classes se relacionam

- `Main` mostra o menu e, conforme a opção escolhida, chama um método
  de `Biblioteca` (por exemplo: `biblioteca.cadastrarLivro(...)`).
- `Biblioteca` é quem realmente faz o trabalho: guarda as listas
  (`List<Livro>` e `List<Usuario>`) em memória, e depois de qualquer
  alteração regrava os arquivos `.txt`.
- `Livro` e `Usuario` são só os "moldes" dos dados — sabem se
  transformar em uma linha de texto (`paraLinha()`) e voltar a ser um
  objeto a partir de uma linha lida do arquivo (`daLinha()`).

### Conceitos de POO usados

- **Encapsulamento:** atributos `private` em `Livro` e `Usuario`,
  acessados só por meio de getters/setters.
- **Classes colaborando entre si:** `Main` não mexe direto nas listas
  nem nos arquivos — sempre pede pra `Biblioteca` fazer isso.
- **Cada classe com uma responsabilidade:** `Livro`/`Usuario` só
  guardam dados, `Biblioteca` cuida da lógica e dos arquivos, `Main`
  cuida só da tela.

## Funcionalidades (menu)

```
1 - Cadastrar livro
2 - Listar livros
3 - Remover livro
4 - Cadastrar usuário
5 - Listar usuários
6 - Remover usuário
7 - Alugar livro
8 - Devolver livro
0 - Sair
```

Regras simples: um livro só pode ser alugado se estiver disponível;
ao tentar alugar um livro já alugado, ou informar um ID de livro/usuário
que não existe, o sistema mostra uma mensagem de erro explicando o
motivo.

## Formato dos arquivos de dados

- `livros.txt`: `id;titulo;autor;status;usuarioId`
  (status é `DISPONIVEL` ou `ALUGADO`; `usuarioId` é `0` quando ninguém
  está com o livro)
- `usuarios.txt`: `id;nome;telefone`

## Possíveis melhorias (se quiserem ir além)

- Adicionar data de devolução prevista e cálculo de atraso
- Permitir editar livro/usuário (não só cadastrar e remover)
- Mostrar, na ficha do usuário, quais livros ele está com ele no momento
