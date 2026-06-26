
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

// Ponto de entrada do sistema. Exibe o menu e chama os métodos da Biblioteca.
public class Main {

    // Variáveis compartilhadas entre todos os métodos da classe.
    private static Scanner scanner;
    private static Biblioteca biblioteca;

    public static void main(String[] args) {
        // Garante que acentos apareçam corretamente no terminal.
        

        scanner   = new Scanner(System.in, StandardCharsets.UTF_8);
        biblioteca = new Biblioteca();

        System.out.println("=========================================");
        System.out.println("       SISTEMA DE BIBLIOTECA");
        System.out.println("=========================================");

        // Loop principal: o programa fica rodando até o usuário digitar 0.
        boolean executando = true;
        while (executando) {
            exibirMenu();
            int opcao = lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1: cadastrarLivro();   break;
                case 2: listarLivros();     break;
                case 3: removerLivro();     break;
                case 4: cadastrarUsuario(); break;
                case 5: listarUsuarios();   break;
                case 6: removerUsuario();   break;
                case 7: alugarLivro();      break;
                case 8: devolverLivro();    break;
                case 0:
                    System.out.println("\nDados salvos na pasta 'dados/'. Até logo!");
                    executando = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
    }

    // Exibe as opções do menu principal.
    private static void exibirMenu() {
        System.out.println("\n------------- MENU -------------");
        System.out.println("1 - Cadastrar livro");
        System.out.println("2 - Listar livros");
        System.out.println("3 - Remover livro");
        System.out.println("4 - Cadastrar usuário");
        System.out.println("5 - Listar usuários");
        System.out.println("6 - Remover usuário");
        System.out.println("7 - Alugar livro");
        System.out.println("8 - Devolver livro");
        System.out.println("0 - Sair");
        System.out.println("--------------------------------");
    }

    // ===================== LIVROS =====================

    // Lê título e autor, manda cadastrar na Biblioteca e mostra o id gerado.
    private static void cadastrarLivro() {
        System.out.println("\n-- Cadastro de Livro --");
        String titulo = lerTexto("Título: ");
        String autor  = lerTexto("Autor: ");
        Livro livro = biblioteca.cadastrarLivro(titulo, autor);
        System.out.println("Livro cadastrado! ID: " + livro.getId());
    }

    // Busca a lista de livros na Biblioteca e exibe um por linha.
    private static void listarLivros() {
        System.out.println("\n-- Lista de Livros --");
        List<Livro> livros = biblioteca.listarLivros();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }
        for (Livro l : livros) {
            System.out.println(l.exibir());
        }
    }

    // Mostra a lista para o usuário escolher e pede o id do livro a remover.
    private static void removerLivro() {
        listarLivros();
        int id = lerInt("ID do livro a remover: ");
        boolean ok = biblioteca.removerLivro(id);
        System.out.println(ok ? "Livro removido com sucesso." : "Livro não encontrado.");
    }

    // ===================== USUÁRIOS =====================

    // Lê nome e telefone, manda cadastrar na Biblioteca e mostra o id gerado.
    private static void cadastrarUsuario() {
        System.out.println("\n-- Cadastro de Usuário --");
        String nome     = lerTexto("Nome: ");
        String telefone = lerTexto("Telefone: ");
        Usuario usuario = biblioteca.cadastrarUsuario(nome, telefone);
        System.out.println("Usuário cadastrado! ID: " + usuario.getId());
    }

    // Busca a lista de usuários na Biblioteca e exibe um por linha.
    private static void listarUsuarios() {
        System.out.println("\n-- Lista de Usuários --");
        List<Usuario> usuarios = biblioteca.listarUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }
        for (Usuario u : usuarios) {
            System.out.println(u.exibir());
        }
    }

    // Mostra a lista para o usuário escolher e pede o id do usuário a remover.
    private static void removerUsuario() {
        listarUsuarios();
        int id = lerInt("ID do usuário a remover: ");
        boolean ok = biblioteca.removerUsuario(id);
        System.out.println(ok ? "Usuário removido com sucesso." : "Usuário não encontrado.");
    }

    // ===================== ALUGAR / DEVOLVER =====================

    // Mostra livros e usuários disponíveis, pede os ids e chama o método da Biblioteca.
    // A mensagem de resultado (sucesso ou erro) vem de dentro da Biblioteca.
    private static void alugarLivro() {
        System.out.println("\n-- Alugar Livro --");
        listarLivros();
        int idLivro = lerInt("ID do livro: ");
        listarUsuarios();
        int idUsuario = lerInt("ID do usuário: ");
        System.out.println(biblioteca.alugarLivro(idLivro, idUsuario));
    }

    // Mostra os livros, pede o id e chama o método de devolução da Biblioteca.
    private static void devolverLivro() {
        System.out.println("\n-- Devolver Livro --");
        listarLivros();
        int idLivro = lerInt("ID do livro a devolver: ");
        System.out.println(biblioteca.devolverLivro(idLivro));
    }

    // ===================== LEITURA DE DADOS =====================

    // Exibe a mensagem e lê uma linha de texto digitada pelo usuário.
    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    // Exibe a mensagem e lê um número inteiro.
    // Fica em loop pedindo de novo caso o usuário digite algo inválido.
    private static int lerInt(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String linha = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }
}
