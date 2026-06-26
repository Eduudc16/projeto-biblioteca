import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// Classe principal do sistema: guarda a lista de livros e usuários,
// contém toda a lógica de negócio e é responsável por ler e gravar os arquivos.
public class Biblioteca {

    // Caminhos dos arquivos onde os dados são salvos.
    private static final String ARQUIVO_LIVROS   = "dados/livros.txt";
    private static final String ARQUIVO_USUARIOS = "dados/usuarios.txt";

    // Listas que guardam os livros e usuários em memória enquanto o programa roda.
    private List<Livro>   livros;
    private List<Usuario> usuarios;

    // Construtor: chamado quando o programa inicia.
    // Cria as listas e carrega os dados dos arquivos.
    public Biblioteca() {
        livros   = new ArrayList<>();
        usuarios = new ArrayList<>();
        carregarLivros();
        carregarUsuarios();

        // Se os arquivos estiverem vazios (primeira execução),
        // adiciona dados de exemplo para facilitar a demonstração.
        if (livros.isEmpty()) {
            carregarDadosExemploLivros();
        }
        if (usuarios.isEmpty()) {
            carregarDadosExemploUsuarios();
        }
    }

    // ===================== DADOS DE EXEMPLO =====================

    // Pré-cadastra livros para a primeira execução do programa.
    private void carregarDadosExemploLivros() {
        livros.add(new Livro(1, "Dom Casmurro",      "Machado de Assis",  false, 0));
        livros.add(new Livro(2, "O Cortiço",         "Aluísio Azevedo",   false, 0));
        livros.add(new Livro(3, "Iracema",           "José de Alencar",   false, 0));
        livros.add(new Livro(4, "Vidas Secas",       "Graciliano Ramos",  false, 0));
        livros.add(new Livro(5, "O Guarani",         "José de Alencar",   false, 0));
        livros.add(new Livro(6, "Memórias Póstumas", "Machado de Assis",  false, 0));
        salvarLivros();
    }

    // Pré-cadastra usuários para a primeira execução do programa.
    private void carregarDadosExemploUsuarios() {
        usuarios.add(new Usuario(1, "Ana Lima",     "47991110001"));
        usuarios.add(new Usuario(2, "Carlos Souza", "47991110002"));
        usuarios.add(new Usuario(3, "Beatriz Melo", "47991110003"));
        salvarUsuarios();
    }

    // ===================== LIVROS =====================

    // Cria um novo livro, adiciona na lista e salva no arquivo.
    public Livro cadastrarLivro(String titulo, String autor) {
        int novoId = maiorIdLivro() + 1;
        Livro livro = new Livro(novoId, titulo, autor, false, 0);
        livros.add(livro);
        salvarLivros();
        return livro;
    }

    // Retorna a lista completa de livros.
    public List<Livro> listarLivros() {
        return livros;
    }

    // Percorre a lista procurando um livro com o id informado.
    // Retorna null se não encontrar.
    public Livro buscarLivroPorId(int id) {
        for (Livro l : livros) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }

    // Remove o livro da lista e salva o arquivo atualizado.
    // Retorna false se o id não existir.
    public boolean removerLivro(int id) {
        Livro livro = buscarLivroPorId(id);
        if (livro == null) {
            return false;
        }
        livros.remove(livro);
        salvarLivros();
        return true;
    }

    // ===================== USUÁRIOS =====================

    // Cria um novo usuário, adiciona na lista e salva no arquivo.
    public Usuario cadastrarUsuario(String nome, String telefone) {
        int novoId = maiorIdUsuario() + 1;
        Usuario usuario = new Usuario(novoId, nome, telefone);
        usuarios.add(usuario);
        salvarUsuarios();
        return usuario;
    }

    // Retorna a lista completa de usuários.
    public List<Usuario> listarUsuarios() {
        return usuarios;
    }

    // Percorre a lista procurando um usuário com o id informado.
    // Retorna null se não encontrar.
    public Usuario buscarUsuarioPorId(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    // Remove o usuário da lista e salva o arquivo atualizado.
    // Retorna false se o id não existir.
    public boolean removerUsuario(int id) {
        Usuario usuario = buscarUsuarioPorId(id);
        if (usuario == null) {
            return false;
        }
        usuarios.remove(usuario);
        salvarUsuarios();
        return true;
    }

    // ===================== ALUGAR / DEVOLVER =====================

    // Aluga um livro para um usuário. Retorna uma mensagem com o resultado.
    public String alugarLivro(int idLivro, int idUsuario) {
        Livro livro = buscarLivroPorId(idLivro);
        if (livro == null) {
            return "Erro: livro não encontrado.";
        }
        if (livro.isAlugado()) {
            return "Erro: este livro já está alugado.";
        }
        Usuario usuario = buscarUsuarioPorId(idUsuario);
        if (usuario == null) {
            return "Erro: usuário não encontrado.";
        }
        livro.setAlugado(true);
        livro.setUsuarioId(idUsuario);
        salvarLivros();
        return "Livro \"" + livro.getTitulo() + "\" alugado para " + usuario.getNome() + " com sucesso!";
    }

    // Devolve um livro, marcando-o como disponível novamente.
    public String devolverLivro(int idLivro) {
        Livro livro = buscarLivroPorId(idLivro);
        if (livro == null) {
            return "Erro: livro não encontrado.";
        }
        if (!livro.isAlugado()) {
            return "Erro: este livro já está disponível (não estava alugado).";
        }
        livro.setAlugado(false);
        livro.setUsuarioId(0);
        salvarLivros();
        return "Livro \"" + livro.getTitulo() + "\" devolvido com sucesso!";
    }

    // ===================== GERAÇÃO DE ID =====================

    // Percorre a lista e devolve o maior id encontrado (ou 0 se a lista estiver vazia).
    // Usado para gerar o próximo id automaticamente.
    private int maiorIdLivro() {
        int maior = 0;
        for (Livro l : livros) {
            if (l.getId() > maior) {
                maior = l.getId();
            }
        }
        return maior;
    }

    private int maiorIdUsuario() {
        int maior = 0;
        for (Usuario u : usuarios) {
            if (u.getId() > maior) {
                maior = u.getId();
            }
        }
        return maior;
    }

    // ===================== ARQUIVOS =====================

    // Lê o arquivo livros.txt linha por linha e monta a lista de livros.
    private void carregarLivros() {
        File arquivo = new File(ARQUIVO_LIVROS);
        garantirArquivo(arquivo);
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo, StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    livros.add(Livro.daLinha(linha));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar livros: " + e.getMessage());
        }
    }

    // Grava a lista de livros no arquivo livros.txt, substituindo o conteúdo anterior.
    private void salvarLivros() {
        File arquivo = new File(ARQUIVO_LIVROS);
        garantirArquivo(arquivo);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo, StandardCharsets.UTF_8))) {
            for (Livro l : livros) {
                bw.write(l.paraLinha());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar livros: " + e.getMessage());
        }
    }

    // Lê o arquivo usuarios.txt linha por linha e monta a lista de usuários.
    private void carregarUsuarios() {
        File arquivo = new File(ARQUIVO_USUARIOS);
        garantirArquivo(arquivo);
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo, StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    usuarios.add(Usuario.daLinha(linha));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }

    // Grava a lista de usuários no arquivo usuarios.txt, substituindo o conteúdo anterior.
    private void salvarUsuarios() {
        File arquivo = new File(ARQUIVO_USUARIOS);
        garantirArquivo(arquivo);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo, StandardCharsets.UTF_8))) {
            for (Usuario u : usuarios) {
                bw.write(u.paraLinha());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    // Cria a pasta "dados" e o arquivo caso ainda não existam.
    private void garantirArquivo(File arquivo) {
        try {
            File pasta = arquivo.getParentFile();
            if (pasta != null && !pasta.exists()) {
                pasta.mkdirs();
            }
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Erro ao preparar arquivo: " + e.getMessage());
        }
    }
}
