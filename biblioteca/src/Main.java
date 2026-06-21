import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

/**
 * Sistema de Biblioteca - Projeto Final
 *
 * Sistema orientado a objetos, iterativo, baseado em terminal, que
 * manipula informações em arquivos de texto (livros.txt e usuarios.txt,
 * dentro da pasta "dados").
 *
 * Classes do sistema:
 *  - Livro      -> entidade que representa um livro do acervo
 *  - Usuario    -> entidade que representa um usuário cadastrado
 *  - Biblioteca -> guarda as listas e contém toda a lógica (cadastrar,
 *                  listar, remover, alugar, devolver) + leitura/escrita
 *                  dos arquivos
 *  - Main       -> apenas o menu, que chama os métodos de Biblioteca
 */
public class Main {

    private static Scanner scanner;
    private static Biblioteca biblioteca;

    public static void main(String[] args) {
        // Garante que acentos (ã, ç, é...) apareçam certo no console,
        // independente do sistema operacional.
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        biblioteca = new Biblioteca();

        System.out.println("=========================================");
        System.out.println("       SISTEMA DE BIBLIOTECA");
        System.out.println("=========================================");

        boolean executando = true;
        while (executando) {
            exibirMenu();
            int opcao = lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarLivro();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    removerLivro();
                    break;
                case 4:
                    cadastrarUsuario();
                    break;
                case 5:
                    listarUsuarios();
                    break;
                case 6:
                    removerUsuario();
                    break;
                case 7:
                    alugarLivro();
                    break;
                case 8:
                    devolverLivro();
                    break;
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

    private static void exibirMenu() {
        System.out.println("\n----------------- MENU -----------------");
        System.out.println("1 - Cadastrar livro");
        System.out.println("2 - Listar livros");
        System.out.println("3 - Remover livro");
        System.out.println("4 - Cadastrar usuário");
        System.out.println("5 - Listar usuários");
        System.out.println("6 - Remover usuário");
        System.out.println("7 - Alugar livro");
        System.out.println("8 - Devolver livro");
        System.out.println("0 - Sair");
        System.out.println("-----------------------------------------");
    }

    // ===================== LIVROS =====================

    private static void cadastrarLivro() {
        System.out.println("\n-- Cadastro de Livro --");
        String titulo = lerTexto("Título: ");
        String autor = lerTexto("Autor: ");
        Livro livro = biblioteca.cadastrarLivro(titulo, autor);
        System.out.println("Livro cadastrado com sucesso! ID: " + livro.getId());
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
        int id = lerInt("Digite o ID do livro a remover: ");
        boolean ok = biblioteca.removerLivro(id);
        System.out.println(ok ? "Livro removido com sucesso." : "Livro não encontrado.");
    }

    // ===================== USUÁRIOS =====================

    private static void cadastrarUsuario() {
        System.out.println("\n-- Cadastro de Usuário --");
        String nome = lerTexto("Nome: ");
        String telefone = lerTexto("Telefone: ");
        Usuario usuario = biblioteca.cadastrarUsuario(nome, telefone);
        System.out.println("Usuário cadastrado com sucesso! ID: " + usuario.getId());
    }

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

    private static void removerUsuario() {
        listarUsuarios();
        int id = lerInt("Digite o ID do usuário a remover: ");
        boolean ok = biblioteca.removerUsuario(id);
        System.out.println(ok ? "Usuário removido com sucesso." : "Usuário não encontrado.");
    }

    // ===================== ALUGAR / DEVOLVER =====================

    private static void alugarLivro() {
        System.out.println("\n-- Alugar Livro --");
        listarLivros();
        int idLivro = lerInt("ID do livro: ");
        listarUsuarios();
        int idUsuario = lerInt("ID do usuário: ");
        String mensagem = biblioteca.alugarLivro(idLivro, idUsuario);
        System.out.println(mensagem);
    }

    private static void devolverLivro() {
        System.out.println("\n-- Devolver Livro --");
        listarLivros();
        int idLivro = lerInt("ID do livro a devolver: ");
        String mensagem = biblioteca.devolverLivro(idLivro);
        System.out.println(mensagem);
    }

    // ===================== LEITURA DE DADOS =====================

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
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }
}
