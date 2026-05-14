import java.io.*;


public class Persistencia {

    // ─────────────────────────────────────────
    // SALVAR DADOs
    // ─────────────────────────────────────────

    public static void salvarDados(

            String funcionarios[],
            int totalFuncionarios,

            String talhoes[],
            int totalTalhoes,

            String frota[],
            int totalTratores,

            String colheitas[],
            int totalColheitas) {

        salvarArquivo("funcionarios.txt", funcionarios, totalFuncionarios);
        salvarArquivo("talhoes.txt", talhoes, totalTalhoes);
        salvarArquivo("frota.txt", frota, totalTratores);
        salvarArquivo("colheitas.txt", colheitas, totalColheitas);

        System.out.println("\n✔ Dados salvos com sucesso!");
    }

    private static void salvarArquivo(

            String nomeArquivo,
            String dados[],
            int total) {

        try {

            FileWriter fw = new FileWriter(nomeArquivo);
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < total; i++) {

                if (dados[i] != null) {
                    bw.write(dados[i]);
                    bw.newLine();
                }
            }

            bw.close();

        } catch (IOException e) {

            System.out.println(
                "Erro ao salvar " + nomeArquivo + ": " + e.getMessage()
            );
        }
    }

    // ─────────────────────────────────────────
    // CARREGAR DADOS
    // ─────────────────────────────────────────

    public static void carregarDados(

            String funcionarios[],
            String talhoes[],
            String frota[],
            String colheitas[]) {

        Cadastro.totalFuncionarios =
            carregarArquivo("funcionarios.txt", funcionarios);

        Cadastro.totalTalhoes =
            carregarArquivo("talhoes.txt", talhoes);

        Cadastro.totalTratores =
            carregarArquivo("frota.txt", frota);

        Colheita.quantidadeColheitas =
            carregarArquivo("colheitas.txt", colheitas);

        System.out.println(
            "✔ Dados carregados: "
            + Cadastro.totalFuncionarios + " funcionário(s), "
            + Cadastro.totalTalhoes + " talhão(ões), "
            + Cadastro.totalTratores + " trator(es), "
            + Colheita.quantidadeColheitas + " colheita(s)."
        );
    }

    private static int carregarArquivo(

            String nomeArquivo,
            String dados[]) {

        int total = 0;

        try {

            FileReader fr = new FileReader(nomeArquivo);
            BufferedReader br = new BufferedReader(fr);

            String linha;

            while ((linha = br.readLine()) != null) {

                if (!linha.trim().equals("")) {

                    if (total >= dados.length) {
                        System.out.println("Arquivo " + nomeArquivo + " possui mais registros que o limite permitido.");
                        break;
                    }

                    dados[total] = linha;
                    total++;
                }
            }

            br.close();

        } catch (FileNotFoundException e) {

            // Arquivo ainda não existe

        } catch (IOException e) {

            System.out.println(
                "Erro ao carregar " + nomeArquivo + ": " + e.getMessage()
            );
        }

        return total;
    }
}
