package app;

public class Hierarquia{

    private Trabalhador origem, destino;
    private float peso;

    public Hierarquia(Trabalhador origem, Trabalhador destino, float peso){
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    public Trabalhador getOrigem() {
        return origem;
    }

    public Trabalhador getDestino() {
        return destino;
    }

    public float getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        return origem.getNome() + " → " + destino.getNome() + ": Peso = " + peso + ".";
    }
}
