// Representa um usuário cadastrado na biblioteca.
public class Usuario {

    // Atributos privados: só a própria classe Usuario pode acessá-los diretamente.
    // Para ler ou alterar esses valores de fora, usamos os métodos get/set abaixo.
    private int id;
    private String nome;
    private String telefone;

    // Construtor: chamado quando criamos um novo objeto Usuario.
    public Usuario(int id, String nome, String telefone) {
        this.id       = id;
        this.nome     = nome;
        this.telefone = telefone;
    }

    // Métodos públicos de leitura (get) e alteração (set) dos atributos privados.

    public int getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    // Transforma o usuário em uma linha de texto para salvar no arquivo.
    // Formato: id;nome;telefone
    public String paraLinha() {
        return id + ";" + nome + ";" + telefone;
    }

    // Lê uma linha do arquivo e devolve um objeto Usuario montado a partir dela.
    public static Usuario daLinha(String linha) {
        String[] partes  = linha.split(";", -1);
        int id           = Integer.parseInt(partes[0].trim());
        String nome      = partes[1];
        String telefone  = partes[2];
        return new Usuario(id, nome, telefone);
    }

    // Retorna o texto formatado para exibir no menu.
    public String exibir() {
        return "[" + id + "] " + nome + " | Tel: " + telefone;
    }
}
