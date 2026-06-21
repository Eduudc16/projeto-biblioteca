import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe "gerente" do sistema: guarda a lista de livros e de usuários
 * em memória, e é responsável por toda a lógica de negócio (cadastrar,
 * listar, remover, alugar, devolver) e por salvar/carregar tudo em
 * arquivos de texto, para que os dados não se percam quando o
 * programa for fechado.
 */
public class Biblioteca {

    private static final String ARQUIVO_LIVROS = "dados/livros.txt";
    private static final String ARQUIVO_USUARIOS = "dados/usuarios.txt";

    private List<Livro> livros;
    private List<Usuario> usuarios;

    public Biblioteca() {
        livros = new ArrayList<>();
        usuarios = new ArrayList<>();
        carregarLivros();
        carregarUsuarios();
    }

    // ===================== LIVROS =====================

    public Livro cadastrarLivro(String titulo, String autor) {
        int novoId = proximoId(livros.size() == 0 ? 0 : maiorIdLivro());
        Livro livro = new Livro(novoId, titulo, autor, false, 0);
        livros.add(livro);
        salvarLivros();
        return livro;
    }

    public List<Livro> listarLivros() {
        return livros;
    }

    public Livro buscarLivroPorId(int id) {
        for (Livro l : livros) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }

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

    public Usuario cadastrarUsuario(String nome, String telefone) {
        int novoId = proximoId(usuarios.size() == 0 ? 0 : maiorIdUsuario());
        Usuario usuario = new Usuario(novoId, nome, telefone);
        usuarios.add(usuario);
        salvarUsuarios();
        return usuario;
    }

    public List<Usuario> listarUsuarios() {
        return usuarios;
    }

    public Usuario buscarUsuarioPorId(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

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

    /**
     * Tenta alugar um livro para um usuário. Devolve uma mensagem
     * explicando o resultado (sucesso ou o motivo do erro), para que
     * a tela (Main) só precise exibir o que for retornado.
     */
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

    private int proximoId(int maiorAtual) {
        return maiorAtual + 1;
    }

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

    /**
     * Garante que a pasta "dados" e o arquivo existam antes de ler ou
     * escrever, criando-os vazios caso ainda não existam.
     */
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
