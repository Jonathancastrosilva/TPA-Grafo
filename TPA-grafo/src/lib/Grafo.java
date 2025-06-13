package lib;

import java.util.*;

public class Grafo<T> {
    private ArrayList<Vertice<T>> vertices;


    public Grafo() {
        this.vertices = new ArrayList<>();
    }

    private Vertice<T> obterVertice(T dado) {
        for (Vertice<T> v : vertices) {
            if (v.dado.equals(dado)){
                return v;
            }
        }
        return null;
    }

    public void adicionarVertice(T dado){
        if (obterVertice(dado) == null){
            vertices.add(new Vertice<>(dado));
        }
    }

    public void adicionarAresta(T origem, T destino, float peso) {
        Vertice<T> verticeOrigem = obterVertice(origem);
        Vertice<T> verticeDestino = obterVertice(destino);

        if (verticeOrigem == null) {
            verticeOrigem = new Vertice<>(origem);
            vertices.add(verticeOrigem);
        }
        if (verticeDestino == null) {
            verticeDestino = new Vertice<>(destino);
            vertices.add(verticeDestino);
        }

        verticeOrigem.adjacentes.add(new Aresta<>(verticeDestino, peso));

        // descomenta para grafo nao direcionado
        // verticeOrigem.adjacentes.add(new Aresta<>(verticeDestino, peso));
    }

    public void caminhoEmLargura(T origem) {
        Vertice<T> inicial = obterVertice(origem);
        if (inicial == null) {
            System.out.println("Vértice de origem não encontrado.");
            return;
        }

        ArrayList<Vertice<T>> fila = new ArrayList<>();
        ArrayList<T> visitados = new ArrayList<>();

        fila.add(inicial);
        visitados.add(inicial.dado);

        while (!fila.isEmpty()) {
            Vertice<T> atual = fila.remove(0);
            System.out.println("Visitando: " + atual.dado);

            for (Aresta<T> aresta : atual.adjacentes) {
                T vizinho = aresta.getDestino().dado;
                if (!visitados.contains(vizinho)) {
                    fila.add(aresta.getDestino());
                    visitados.add(vizinho);
                }
            }
        }
    }

    public void imprimirGrafo() {
        for (Vertice<T> v : vertices) {
            System.out.print(v.dado + " -> ");
            for (Aresta<T> a : v.adjacentes) {
                System.out.print(a.getDestino().dado + "(" + a.getPeso() + ") ");
            }
            System.out.println();
        }
    }

}