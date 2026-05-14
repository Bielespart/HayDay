import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Armazenamento {

    public static int totalTerreiro = 0;
    public static int totalSecador = 0;

    public static void atualizarResumo(String[] litrosColhidos, String[] destinos, int totalColheitas) {
        totalTerreiro = 0;
        totalSecador = 0;

        for (int i = 0; i < totalColheitas; i++) {
            int litros = converterInteiro(litrosColhidos[i]);

            if (destinos[i] != null && destinos[i].equalsIgnoreCase("Terreiro")) {
                totalTerreiro += litros;
            } else if (destinos[i] != null && destinos[i].equalsIgnoreCase("Secador")) {
                totalSecador += litros;
            }
        }
    }

    public static void salvarResumo(String[] litrosColhidos, String[] destinos, int totalColheitas) {
        atualizarResumo(litrosColhidos, destinos, totalColheitas);

        try {
            FileWriter fw = new FileWriter(new File(pastaDados(), "armazenamento.txt"));
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("Terreiro;" + totalTerreiro);
            bw.newLine();
            bw.write("Secador;" + totalSecador);
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar armazenamento.txt: " + e.getMessage());
        }
    }

    public static void carregarResumo() {
        totalTerreiro = 0;
        totalSecador = 0;

        try {
            File arquivo = new File(pastaDados(), "armazenamento.txt");

            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }

            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);

            String linha;

            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";", -1);

                if (partes.length >= 2) {
                    if (partes[0].equalsIgnoreCase("Terreiro")) {
                        totalTerreiro = converterInteiro(partes[1]);
                    } else if (partes[0].equalsIgnoreCase("Secador")) {
                        totalSecador = converterInteiro(partes[1]);
                    }
                }
            }

            br.close();
        } catch (IOException e) {
            System.out.println("Erro ao carregar armazenamento.txt: " + e.getMessage());
        }
    }

    public static void listarResumo(String[] datas, String[] litrosColhidos, String[] destinos, int totalColheitas) {
        atualizarResumo(litrosColhidos, destinos, totalColheitas);

        System.out.println("\n--- Armazenamento da Colheita ---");
        System.out.printf("%-12s %-15s%n", "Local", "Total acumulado");
        System.out.println("-----------------------------");
        System.out.printf("%-12s %-15s%n", "Secador", totalSecador + " L");
        System.out.printf("%-12s %-15s%n", "Terreiro", totalTerreiro + " L");

        System.out.println("\nDetalhamento por colheita:");

        if (totalColheitas == 0) {
            System.out.println("Nenhuma colheita registrada.");
            return;
        }

        System.out.printf("%-4s %-12s %-12s %-12s%n", "N.", "Data", "Local", "Quantidade");
        System.out.println("------------------------------------------");

        for (int i = 0; i < totalColheitas; i++) {
            System.out.printf(
                    "%-4d %-12s %-12s %-12s%n",
                    i + 1,
                    datas[i],
                    destinos[i],
                    litrosColhidos[i] + " L");
        }

        System.out.println("------------------------------------------");
        System.out.printf("%-30s %-12s%n", "Total Terreiro:", totalTerreiro + " L");
        System.out.printf("%-30s %-12s%n", "Total Secador:", totalSecador + " L");
    }

    private static int converterInteiro(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static File pastaDados() {
        File atual = new File(System.getProperty("user.dir"));

        if (atual.getName().equals("files (3)")) {
            return atual.getParentFile();
        }

        return atual;
    }
}
