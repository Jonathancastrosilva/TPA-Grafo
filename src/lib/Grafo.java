package lib;

import java.io.FileReader;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;

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
        //verticeDestino.adjacentes.add(new Aresta<>(verticeOrigem, peso));
    }

    public void adicionarArestaBidirecional(T origem, T destino, float peso) {
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

        verticeDestino.adjacentes.add(new Aresta<>(verticeOrigem, peso));
    }

    public boolean verificacaoDeCiclo() {
        ArrayList<Vertice<T>> visitados = new ArrayList<>();

        for (Vertice<T> vertice : vertices) {
            if (!visitados.contains(vertice)) {
                if (dfsCiclo(vertice, visitados, null)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfsCiclo(Vertice<T> atual, ArrayList<Vertice<T>> visitados, Vertice<T> pai) {
        visitados.add(atual);

        for (Aresta<T> aresta : atual.adjacentes) {
            Vertice<T> vizinho = aresta.getDestino();

            if (!visitados.contains(vizinho)) {
                if (dfsCiclo(vizinho, visitados, atual)) {
                    return true;
                }
            } 
            
            else if (pai == null || !vizinho.equals(pai)) {
                return true; // Encontrou um ciclo
            }
        }
        return false;
    }

    public void dijkstra(T origem) {
        Vertice<T> inicial = obterVertice(origem);
        if (inicial == null) {
            System.out.println("Vértice de origem não encontrado.");
            return;
        }

        ArrayList<Vertice<T>> naoVisitados = new ArrayList<>(vertices);
        ArrayList<Float> distancias = new ArrayList<>();

        // Inicializa todas as distâncias como infinito
        for (int i = 0; i < vertices.size(); i++) {
            distancias.add(Float.MAX_VALUE);
        }

        // Define distância da origem como 0
        int indiceOrigem = vertices.indexOf(inicial);
        distancias.set(indiceOrigem, 0f);

        while (!naoVisitados.isEmpty()) {
            // Encontra o vértice com a menor distância
            Vertice<T> atual = null;
            float menorDistancia = Float.MAX_VALUE;

            for (Vertice<T> v : naoVisitados) {
                int idx = vertices.indexOf(v);
                if (distancias.get(idx) < menorDistancia) {
                    menorDistancia = distancias.get(idx);
                    atual = v;
                }
            }

            if (atual == null) break;  // Não há mais vértices alcançáveis

            naoVisitados.remove(atual);
            int indiceAtual = vertices.indexOf(atual);

            for (Aresta<T> aresta : atual.adjacentes) {
                Vertice<T> vizinho = aresta.getDestino();
                int indiceVizinho = vertices.indexOf(vizinho);

                if (naoVisitados.contains(vizinho)) {
                    float novaDistancia = distancias.get(indiceAtual) + aresta.getPeso();
                    if (novaDistancia < distancias.get(indiceVizinho)) {
                        distancias.set(indiceVizinho, novaDistancia);
                    }
                }
            }
        }

        System.out.println("Distâncias mínimas a partir de " + origem + ":");
        for (int i = 0; i < vertices.size(); i++) {
            System.out.println("Até " + vertices.get(i).dado + ": " + distancias.get(i));
        }
    }

    public void Bfs(T origem) {
        
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