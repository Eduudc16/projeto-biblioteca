public class Usuario {

    private int id;
    private String nome;
    private String telefone;

    public Usuario(int id, String nome, String telefone) {
        this.id       = id;
        this.nome     = nome;
        this.telefone = telefone;
    }

    public int getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String paraLinha() {
        return id + ";" + nome + ";" + telefone;
    }

    public static Usuario daLinha(String linha) {
        String[] partes  = linha.split(";", -1);
        int id           = Integer.parseInt(partes[0].trim());
        String nome      = partes[1];
        String telefone  = partes[2];
        return new Usuario(id, nome, telefone);
    }

    public String exibir() {
        return "[" + id + "] " + nome + " | Tel: " + telefone;
    }
}
