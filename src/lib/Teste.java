package lib;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Teste {

    private static final String NOME_ARQUIVO = "grafo.txt";
    public static void main(String[] args) {
        Grafo<String> grafo = new Grafo<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))) {

            String linha;

            // iniciando o tempo de processamento
            long inicio = System.nanoTime();

            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                String origem = partes[0];
                String direcao = partes[1];
                String destino = partes[2];
                float peso = Float.parseFloat(partes[3]);

                if (direcao.equals("->")){
                    grafo.adicionarAresta(origem, destino, peso);
                }

                else {
                    grafo.adicionarArestaBidirecional(origem, destino, peso);
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erro ao processar um dos valores numéricos: " + e.getMessage());
        }

        System.out.println("Grafo:");
        grafo.imprimirGrafo();

        System.out.println("\n Bfs a partir de A:");
        grafo.Bfs("A");

        // Teste de ciclo
        if (grafo.verificacaoDeCiclo()) {
            System.out.println("O grafo contém ciclo.");
        } else {
            System.out.println("O grafo não contém ciclo.");
        }

        // Teste do Dijkstra
        grafo.dijkstra("A");
    }
}