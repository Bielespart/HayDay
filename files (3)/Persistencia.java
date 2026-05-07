import java.io.*;
import java.util.ArrayList;

public class Persistencia {

    public static void salvarDados(
            ArrayList<String[]> funcionarios,
            ArrayList<String[]> talhoes,
            ArrayList<String[]> frota,
            ArrayList<String[]> colheitas) {

        salvarArquivo("funcionarios.txt", funcionarios);
        salvarArquivo("talhoes.txt", talhoes);
        salvarArquivo("frota.txt", frota);
        salvarArquivo("colheitas.txt", colheitas);
        System.out.println("\n✔ Dados salvos com sucesso!");
    }

    private static void salvarArquivo(String nomeArquivo, ArrayList<String[]> lista) {
        try {
            FileWriter fw = new FileWriter(nomeArquivo);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < lista.size(); i++) {
                String[] registro = lista.get(i);
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < registro.length; j++) {
                    sb.append(registro[j]);
                    if (j < registro.length - 1) sb.append(";");
                }
                bw.write(sb.toString());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar " + nomeArquivo + ": " + e.getMessage());
        }
    }

    public static void carregarDados(
            ArrayList<String[]> funcionarios,
            ArrayList<String[]> talhoes,
            ArrayList<String[]> frota,
            ArrayList<String[]> colheitas) {

        carregarArquivo("funcionarios.txt", funcionarios, 3);
        carregarArquivo("talhoes.txt", talhoes, 4);
        carregarArquivo("frota.txt", frota, 2);
        carregarArquivo("colheitas.txt", colheitas, 6);
        System.out.println("✔ Dados carregados: "
                + funcionarios.size() + " funcionário(s), "
                + talhoes.size() + " talhão(ões), "
                + frota.size() + " trator(es), "
                + colheitas.size() + " colheita(s).");
    }

    private static void carregarArquivo(String nomeArquivo, ArrayList<String[]> lista, int campos) {
        try {
            FileReader fr = new FileReader(nomeArquivo);
            BufferedReader br = new BufferedReader(fr);
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] partes = linha.split(";");
                if (partes.length == campos) {
                    lista.add(partes);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            // Arquivo ainda não existe — ignorar na primeira execução
        } catch (IOException e) {
            System.out.println("Erro ao carregar " + nomeArquivo + ": " + e.getMessage());
        }
    }
}
