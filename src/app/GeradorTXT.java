package app;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class GeradorTXT{
    private static final int NUM_REGISTROS = 10; // Quantidade de registros a serem gerados
    private static final String NOME_ARQUIVO = "grafo.txt";

    private static final String[] PRENOMES = {
            "Ana", "Bruno", "Carlos", "Daniela", "Eduardo", "Fernanda", "Gabriel", "Helena", "Isabela", "João",
            "Juliana", "Lucas", "Mariana", "Nathan", "Olivia", "Paulo", "Quésia", "Rafael", "Sofia", "Thiago",
            "Victor", "William", "Xavier", "Yasmin", "Zuleica", "Alfredo", "Beatriz", "Caio", "Denise", "Eliana",
            "Felipe", "Gustavo", "Heitor", "Igor", "Jéssica", "Kevin", "Larissa", "Mateus", "Natália", "Otávio",
            "Patrícia", "Renato", "Sandra", "Tadeu", "Ursula", "Vinícius", "Wellington", "Zilda", "Adriana", "Benício",
            "Cristina", "Davi", "Emanuel", "Flávia", "Geraldo", "Heloísa", "Ícaro", "Jaqueline", "Leonardo", "Marta",
            "Nelson", "Orlando", "Priscila", "Raquel", "Saulo", "Tatiane", "Ubirajara", "Vera", "Wesley", "Zenaide",
            "Alice", "Brenda", "Caetano", "Danilo", "Enzo", "Fabiana", "Gilberto", "Henrique", "Isadora", "José",
            "Kátia", "Lorena", "Maurício", "Natanael", "Osvaldo", "Pamela", "Regina", "Sandro", "Tânia", "Ulisses",
            "Vânia", "Wilson", "Yago", "Zélia", "Amélia", "Bernardo", "Celso", "Dulce", "Edson", "Fátima", "Gilmar",
            "Humberto", "Irene", "Jorge", "Kleber", "Luciana", "Marcelo", "Nadir", "Otacílio", "Paula", "Renata"
    };

    private static final String[] SOBRENOMES = {
            "Almeida", "Barbosa", "Campos", "Dias", "Evangelista", "Ferreira", "Gomes", "Henrique", "Iglesias", "Junqueira",
            "Klein", "Lima", "Medeiros", "Nascimento", "Oliveira", "Pereira", "Queiroz", "Rodrigues", "Silva", "Teixeira",
            "Uchoa", "Vasconcelos", "Watanabe", "Ximenes", "Yamamoto", "Zanetti", "Araújo", "Borges", "Coelho", "Dantas",
            "Esteves", "Farias", "Guimarães", "Holanda", "Ivo", "Jardim", "Krieger", "Lacerda", "Monteiro", "Neves",
            "Oliveira", "Porto", "Quintana", "Ramos", "Sanches", "Torrico", "Urbano", "Vieira", "Wanderley", "Xavier",
            "Yunes", "Zampieri", "Abreu", "Barreto", "Coutinho", "Delgado", "Elias", "França", "Godoy", "Haddad",
            "Ibrahim", "Jacob", "Lopes", "Moura", "Nogueira", "Ortega", "Pinto", "Quaresma", "Reis", "Souto",
            "Torres", "Ubaldo", "Valente", "Weber", "Ximenes", "Yamaguchi", "Zanella", "Alvarenga", "Bittencourt", "Carvalho",
            "Duarte", "Espíndola", "Freitas", "Gonçalves", "Herrera", "Ishikawa", "Junqueira", "Lacerda", "Mancini", "Noronha",
            "Orsini", "Paz", "Quevedo", "Rangel", "Souza", "Tavares", "Uchoa", "Vilela", "Werneck", "Xisto"
    };

    public static void main(String[] args) {
        gerarArquivo();

        gerarRelacoesAleatorias(5);
    }

    private static void gerarArquivo() {
        Random random = new Random();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO, StandardCharsets.UTF_8))) {
            //writer.write(NUM_REGISTROS + "\n");

            for (int i = 1; i <= NUM_REGISTROS; i++) {
                String nome = gerarNomeAleatorio(random);
                writer.write("ADD;" + i + ";" + nome + "\n");
                if (i % 1_000_000 == 0) {
                    System.out.println(i + " registros gerados...");
                }
            }

            System.out.println("Arquivo gerado com sucesso: " + NOME_ARQUIVO);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    public static void gerarRelacoesAleatorias(int quantidade) {
        Random random = new Random();

        for (int i = 0; i < quantidade; i++) {
            int idOrigem = random.nextInt(NUM_REGISTROS) + 1; // de 1 até NUM_REGISTROS
            int idDestino;
            do {
                idDestino = random.nextInt(NUM_REGISTROS) + 1;
            } while (idDestino == idOrigem);

            float peso = 1.0f + random.nextFloat() * 9.0f;
            peso = Math.round(peso * 100) / 100.0f;
            adicionarRelacao(idOrigem, idDestino, peso);
        }
    }

    public static void adicionarRelacao(int idOrigem, int idDestino, float peso) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO, true))) {
            writer.write("REL;" + idOrigem + ";" + idDestino + ";" + peso + "\n");
            //System.out.println("Relação adicionada: REL;" + idOrigem + ";" + idDestino + ";" + peso);
        } catch (IOException e) {
            System.err.println("Erro ao adicionar relação: " + e.getMessage());
        }
    }

    private static String gerarNomeAleatorio(Random random) {
        String primeiroNome = PRENOMES[random.nextInt(PRENOMES.length)];
        String sobrenome = SOBRENOMES[random.nextInt(SOBRENOMES.length)];
        return primeiroNome + " " + sobrenome;
    }
}