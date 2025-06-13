package lib;

import java.util.*;

public class Vertice<T> {
    T dado;
    ArrayList<Aresta<T>> adjacentes;

    public Vertice(T dado) {
        this.dado = dado;
        this.adjacentes = new ArrayList<>();
    }

    public T getDado(){
        return dado;
    }

    public ArrayList<Aresta<T>> getAdjacentes() {
        return adjacentes;
    }
}