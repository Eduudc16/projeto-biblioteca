/**
 * Representa um livro do acervo da biblioteca.
 *
 * Conceitos de POO usados aqui:
 *  - Encapsulamento: os atributos são "private" e só podem ser
 *    lidos/alterados através dos métodos get/set.
 */
public class Livro {

    private int id;
    private String titulo;
    private String autor;
    private boolean alugado;   // true = está emprestado, false = disponível
    private int usuarioId;     // id de quem alugou (0 = ninguém)

    public Livro(int id, String titulo, String autor, boolean alugado, int usuarioId) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.alugado = alugado;
        this.usuarioId = usuarioId;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public boolean isAlugado() {
        return alugado;
    }

    public void setAlugado(boolean alugado) {
        this.alugado = alugado;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    /**
     * Transforma o livro em uma linha de texto para salvar no arquivo.
     * Formato: id;titulo;autor;status;usuarioId
     */
    public String paraLinha() {
        String status = alugado ? "ALUGADO" : "DISPONIVEL";
        return id + ";" + titulo + ";" + autor + ";" + status + ";" + usuarioId;
    }

    /**
     * Faz o caminho inverso: recebe uma linha do arquivo e devolve um
     * objeto Livro montado a partir dela.
     */
    public static Livro daLinha(String linha) {
        String[] partes = linha.split(";", -1);
        int id = Integer.parseInt(partes[0].trim());
        String titulo = partes[1];
        String autor = partes[2];
        boolean alugado = partes[3].trim().equals("ALUGADO");
        int usuarioId = Integer.parseInt(partes[4].trim());
        return new Livro(id, titulo, autor, alugado, usuarioId);
    }

    /**
     * Texto amigável para mostrar ao usuário no menu.
     */
    public String exibir() {
        String status = alugado ? "Alugado (usuário " + usuarioId + ")" : "Disponível";
        return "[" + id + "] \"" + titulo + "\" - " + autor + " | " + status;
    }
}
