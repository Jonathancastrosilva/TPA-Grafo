package lib;

public class Teste {
    public static void main(String[] args) {
        Grafo<String> grafo = new Grafo<>();

        grafo.adicionarAresta("A", "B", 1.0f);
        grafo.adicionarAresta("A", "C", 2.5f);
        grafo.adicionarAresta("B", "C", 1.2f);
        grafo.adicionarAresta("C", "D", 3.0f);

        System.out.println("Grafo:");
        grafo.imprimirGrafo();

        System.out.println("\n CaminhoEmLargura a partir de A:");
        grafo.caminhoEmLargura("A");
    }
}