package app;

import lib.Grafo;
import lib.Vertice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {
    // NOSSO VAI SER UM SISTEMA DE USUARIOS HIERARQUICOS (P1 precisa que P2 libere algo e etc)


    private static final String NOME_ARQUIVO = "grafo.txt";

    public static void main(String[] args) {

        ArrayList<Trabalhador> listaTrabalhadores = new ArrayList<>();
        Grafo<Trabalhador> grafo = new Grafo<>();

        Boolean continuar = true;
        Scanner s = new Scanner(System.in);

        try (BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))) {

            String linha;

            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");

                if (partes[0].equals("ADD")) {
                    String id = partes[1];
                    String nome = partes[2];

                    Trabalhador trabalhador = new Trabalhador(nome, id);

                    listaTrabalhadores.add(trabalhador);
                    grafo.adicionarVertice(trabalhador);
                }

                if (partes[0].equals("REL")) {
                    String idOrigem = partes[1];
                    String idDestino = partes[2];
                    float peso = Float.parseFloat(partes[3]);

                    int indiceOrigem = Integer.parseInt(idOrigem);
                    int indiceDestino = Integer.parseInt(idDestino);

                    if (indiceOrigem >= 0 && indiceOrigem < listaTrabalhadores.size() && indiceDestino >= 0 && indiceDestino < listaTrabalhadores.size()) {

                        Trabalhador origem = listaTrabalhadores.get(indiceOrigem);
                        Trabalhador destino = listaTrabalhadores.get(indiceDestino);

                        if (origem != null && destino != null) {
                            grafo.adicionarAresta(origem, destino, peso);
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erro ao processar um dos valores numéricos: " + e.getMessage());
        }

        // Teste de ciclo
        if (grafo.verificacaoDeCiclo()) {
            System.out.println("O grafo contém ciclo. Crie um grafo sem ciclos para continuar");
            continuar = false;
        } else {
            System.out.println("O grafo não contém ciclo. Indo para o sistema");
        }

        //System.out.println("\n Bfs a partir de A:");
        //grafo.Bfs("A");

        // Exibe o menu enquanto a opção 0 não for escolhida
        while (continuar.equals(true)) {

            System.out.println("Escolha uma opção:");
            System.out.println("1 para imprimir os trabalhadores do sistema");
            System.out.println("2 para imprimir o grafo da hierarquia dos usuarios");
            System.out.println("3 para usar o dijkstra");
            System.out.println("0 para sair");

            String opcao = s.nextLine();

            // Sai do menu se escolhida
            if (opcao.equals("0")){

                continuar = false;
            }

            // Imprimir os trabalhadores do sistema
            if (opcao.equals("1")){

                System.out.println("Lista de Trabalhadores:");

                for (int i = 0; i < listaTrabalhadores.size(); i++) {
                    Trabalhador t = listaTrabalhadores.get(i);
                    System.out.println("Nome: " + t.getNome() + ", ID Interno: " + t.getId());
                }

            }

            if (opcao.equals("2")){

                System.out.println("Grafo Da Hierarquia Dos Usuarios");
                grafo.imprimirGrafo();

            }

            if (opcao.equals("3")) {

                System.out.println("Qual o ID do usuário que deve ser usado no Dijkstra?");
                String idDigitado = s.nextLine();

                Trabalhador trabalhadorOrigem = null;

                for (Trabalhador t : listaTrabalhadores) {
                    if (t.getId().equals(idDigitado)) {
                        trabalhadorOrigem = t;
                        break;
                    }
                }

                if (trabalhadorOrigem == null) {
                    System.out.println("ID não encontrado.");
                } else {
                    grafo.dijkstra(trabalhadorOrigem);
                }
            }
        }
    }
}