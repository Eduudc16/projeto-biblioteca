// Representa um livro do acervo da biblioteca.
public class Livro {

    // Atributos privados: só a própria classe Livro pode acessá-los diretamente.
    // Para ler ou alterar esses valores de fora, usamos os métodos get/set abaixo.
    private int id;
    private String titulo;
    private String autor;
    private boolean alugado;  // true = emprestado, false = disponível
    private int usuarioId;    // id de quem alugou (0 = ninguém)

    // Construtor: chamado quando criamos um novo objeto Livro.
    // Recebe os dados e os armazena nos atributos com "this.".
    public Livro(int id, String titulo, String autor, boolean alugado, int usuarioId) {
        this.id       = id;
        this.titulo   = titulo;
        this.autor    = autor;
        this.alugado  = alugado;
        this.usuarioId = usuarioId;
    }

    // Métodos públicos de leitura (get) e alteração (set) dos atributos privados.
    // Como os atributos são private, é por aqui que o resto do programa os acessa.

    public int getId() { return id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public boolean isAlugado() { return alugado; }
    public void setAlugado(boolean alugado) { this.alugado = alugado; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    // Transforma o livro em uma linha de texto para salvar no arquivo.
    // Formato: id;titulo;autor;status;usuarioId
    public String paraLinha() {
        String status = alugado ? "ALUGADO" : "DISPONIVEL";
        return id + ";" + titulo + ";" + autor + ";" + status + ";" + usuarioId;
    }

    // Lê uma linha do arquivo e devolve um objeto Livro montado a partir dela.
    public static Livro daLinha(String linha) {
        String[] partes = linha.split(";", -1);
        int id          = Integer.parseInt(partes[0].trim());
        String titulo   = partes[1];
        String autor    = partes[2];
        boolean alugado = partes[3].trim().equals("ALUGADO");
        int usuarioId   = Integer.parseInt(partes[4].trim());
        return new Livro(id, titulo, autor, alugado, usuarioId);
    }

    // Retorna o texto formatado para exibir no menu.
    public String exibir() {
        String status = alugado ? "Alugado (usuário " + usuarioId + ")" : "Disponível";
        return "[" + id + "] \"" + titulo + "\" - " + autor + " | " + status;
    }
}
