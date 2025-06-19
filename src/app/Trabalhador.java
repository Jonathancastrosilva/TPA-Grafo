package app;

public class Trabalhador {

    private String nome;
    private String id;

    public Trabalhador(String nome, String id){
        this.nome = nome;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + " Id: " + id;
    }
}