
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner;
    private static Biblioteca biblioteca;

    public static void main(String[] args) {
        scanner   = new Scanner(System.in, StandardCharsets.UTF_8);
        biblioteca = new Biblioteca();

        System.out.println("=========================================");
        System.out.println("       SISTEMA DE BIBLIOTECA");
        System.out.println("=========================================");

        boolean executando = true;
        while (executando) {
            exibirMenu();
            int opcao = lerInt("Escolha uma opcao: ");

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
                    System.out.println("\nDados salvos na pasta 'dados/'. Ate logo!");
                    executando = false;
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }

            if (executando) {
                pausar();
            }
        }
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n------------- MENU -------------");
        System.out.println("1 - Cadastrar livro");
        System.out.println("2 - Listar livros");
        System.out.println("3 - Remover livro");
        System.out.println("4 - Cadastrar usuario");
        System.out.println("5 - Listar usuarios");
        System.out.println("6 - Remover usuario");
        System.out.println("7 - Alugar livro");
        System.out.println("8 - Devolver livro");
        System.out.println("0 - Sair");
        System.out.println("--------------------------------");
    }

    private static void cadastrarLivro() {
        System.out.println("\n-- Cadastro de Livro --");
        String titulo = lerTexto("Titulo: ");
        String autor  = lerTexto("Autor: ");
        Livro livro = biblioteca.cadastrarLivro(titulo, autor);
        System.out.println("Livro cadastrado! ID: " + livro.getId());
    }

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

    private static void removerLivro() {
        listarLivros();
        int id = lerInt("ID do livro a remover: ");
        boolean ok = biblioteca.removerLivro(id);
        System.out.println(ok ? "Livro removido com sucesso." : "Livro nao encontrado.");
    }

    private static void cadastrarUsuario() {
        System.out.println("\n-- Cadastro de Usuario --");
        String nome     = lerTexto("Nome: ");
        String telefone = lerTexto("Telefone: ");
        Usuario usuario = biblioteca.cadastrarUsuario(nome, telefone);
        System.out.println("Usuario cadastrado! ID: " + usuario.getId());
    }

    private static void listarUsuarios() {
        System.out.println("\n-- Lista de Usuarios --");
        List<Usuario> usuarios = biblioteca.listarUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuario cadastrado.");
            return;
        }
        for (Usuario u : usuarios) {
            System.out.println(u.exibir());
        }
    }

    private static void removerUsuario() {
        listarUsuarios();
        int id = lerInt("ID do usuario a remover: ");
        boolean ok = biblioteca.removerUsuario(id);
        System.out.println(ok ? "Usuario removido com sucesso." : "Usuario nao encontrado.");
    }

    private static void alugarLivro() {
        System.out.println("\n-- Alugar Livro --");
        listarLivros();
        int idLivro = lerInt("ID do livro: ");
        listarUsuarios();
        int idUsuario = lerInt("ID do usuario: ");
        System.out.println(biblioteca.alugarLivro(idLivro, idUsuario));
    }

    private static void devolverLivro() {
        System.out.println("\n-- Devolver Livro --");
        listarLivros();
        int idLivro = lerInt("ID do livro a devolver: ");
        System.out.println(biblioteca.devolverLivro(idLivro));
    }

    private static void pausar() {
        System.out.print("\nPressione ENTER para voltar ao menu...");
        scanner.nextLine();
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private static int lerInt(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String linha = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido. Digite um numero inteiro.");
            }
        }
    }
}
