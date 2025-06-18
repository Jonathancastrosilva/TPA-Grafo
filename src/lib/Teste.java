package lib;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Teste {
    // NOSSO VAI SER UM SISTEMA DE USUARIOS HIERARQUICOS (P1 precisa que P2 libere algo e etc)

    private static final String NOME_ARQUIVO = "grafo.txt";
    public static void main(String[] args) {

        Grafo<String> grafo = new Grafo<>();

        Boolean continuar = true;
        Scanner s = new Scanner(System.in);

        try (BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))) {

            String linha;

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
            System.out.println("1 para imprimir o grafo da hierarquia dos usuarios");
            System.out.println("2 para usar o dijkstra");
            System.out.println("0 para sair");

            String opcao = s.nextLine();

            // Sai do menu se escolhida
            if (opcao.equals("0")){

                continuar = false;
            }

            // Imprimir o grafo da rede
            if (opcao.equals("1")){

                System.out.println("Grafo Dos Usuarios");
                grafo.imprimirGrafo();
            }

            if (opcao.equals("2")){

                System.out.println("Qual usuario deve ser usado no Dijkstra");
                String userEscolhido = s.nextLine();
                grafo.dijkstra(userEscolhido);
            }


        }
    }
}