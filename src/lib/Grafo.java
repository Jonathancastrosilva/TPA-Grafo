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

    public Vertice<T> adicionarVertice(T dado){

        Vertice<T> vert = obterVertice(dado);
        if (vert != null) {
            return vert;
        }

        Vertice<T> novoVertice = new Vertice<>(dado);
        this.vertices.add(novoVertice);
        return novoVertice;
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

    }

    public void adicionarArestaBidirecional(T origem, T destino, float peso) {

        adicionarAresta(origem, destino, peso);
        adicionarAresta(destino, origem, peso);
    }

    public boolean verificacaoDeCiclo() {
        ArrayList<Vertice<T>> visitados = new ArrayList<>();
        ArrayList<Vertice<T>> visitando = new ArrayList<>();

        for (Vertice<T> vertice : vertices) {
            if (!visitados.contains(vertice)) {
                if (dfsCiclo(vertice, visitados, visitando)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfsCiclo(Vertice<T> atual, ArrayList<Vertice<T>> visitados, ArrayList<Vertice<T>> visitando) {
        visitando.add(atual);

        for (Aresta<T> aresta : atual.adjacentes) {
            Vertice<T> vizinho = aresta.getDestino();

            if (visitando.contains(vizinho)) {
                System.out.println("Ciclo encontrado:");

                // Imprime o ciclo encontrado (do vizinho até o final da lista visitando)
                int inicio = visitando.indexOf(vizinho);
                for (int i = inicio; i < visitando.size(); i++) {
                    System.out.print(visitando.get(i).dado + " -> ");
                }
                System.out.println(vizinho.dado); // fecha o ciclo

                return true;
            }

            if (!visitados.contains(vizinho)) {
                if (dfsCiclo(vizinho, visitados, visitando)) {
                    return true;
                }
            }
        }

        visitando.remove(atual);
        visitados.add(atual);
        return false;
    }

    public void dijkstra(T origem) {
        Vertice<T> inicial = obterVertice(origem);
        if (inicial == null) {
            System.out.println("Vértice de origem não encontrado.");
            return;
        }

        ArrayList<Vertice<T>> naoVisitados = new ArrayList<>(vertices);
        ArrayList<Float> custos = new ArrayList<>();

        // Inicializa todas os custo como infinito
        for (int i = 0; i < vertices.size(); i++) {
            custos.add(Float.MAX_VALUE);
        }

        // Define custo da origem como 0
        int indiceOrigem = vertices.indexOf(inicial);
        custos.set(indiceOrigem, 0f);

        while (!naoVisitados.isEmpty()) {
            // Encontra o vértice com a menor custo
            Vertice<T> atual = null;
            float menorCusto = Float.MAX_VALUE;

            for (Vertice<T> v : naoVisitados) {
                int idx = vertices.indexOf(v);
                if (custos.get(idx) < menorCusto) {
                    menorCusto = custos.get(idx);
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
                    float novoCusto = custos.get(indiceAtual) + aresta.getPeso();
                    if (novoCusto < custos.get(indiceVizinho)) {
                        custos.set(indiceVizinho, novoCusto);
                    }
                }
            }
        }

        System.out.println("Custos mínimas a partir de " + origem + ":");
        for (int i = 0; i < vertices.size(); i++) {
            float custo = custos.get(i);
            if (custo == Float.MAX_VALUE) {
                System.out.println("Até (" + vertices.get(i).dado + "): Sem caminho");
            } else {
                System.out.println("Até (" + vertices.get(i).dado + "): " + custo);
            }
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
            System.out.print("(" + v.dado + ") -> ");
            for (Aresta<T> a : v.adjacentes) {
                System.out.print("(" + a.getDestino().dado + ")" + " (Peso = " + a.getPeso() + ") ");
            }
            System.out.println();
        }
    }
}