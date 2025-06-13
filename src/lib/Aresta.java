package lib;

public class Aresta<T> {
    private Vertice<T> destino;
    private float peso;

    public Aresta(Vertice<T> destino, float p) {
        this.destino = destino;
        this.peso = p;
    }

    public float getPeso(){
        return peso;
    }

    public Vertice<T> getDestino(){
        return destino;
    }
}