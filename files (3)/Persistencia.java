import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Persistencia {

    private static final String SEPARADOR = ";";

    public static void carregarTudo(
            String[] datas,
            String[] matriculas,
            String[] codigosTalhao,
            String[] placasColheita,
            String[] litrosColhidos,
            String[] destinos) {

        carregarFuncionarios();
        carregarTalhoes();
        carregarFrota();
        carregarColheitas(datas, matriculas, codigosTalhao, placasColheita, litrosColhidos, destinos);

        System.out.println(
                "Dados carregados: "
                + Cadastro.totalFuncionarios + " funcionario(s), "
                + Cadastro.totalTalhoes + " talhao(oes), "
                + Cadastro.totalTratores + " trator(es), "
                + Colheita.quantidadeColheitas + " colheita(s).");
    }

    public static void salvarTudo(
            String[] datas,
            String[] matriculas,
            String[] codigosTalhao,
            String[] placasColheita,
            String[] litrosColhidos,
            String[] destinos) {

        salvarFuncionarios();
        salvarTalhoes();
        salvarFrota();
        salvarColheitas(datas, matriculas, codigosTalhao, placasColheita, litrosColhidos, destinos);
    }

    private static void salvarFuncionarios() {
        try (BufferedWriter bw = criarEscritor("funcionarios.txt")) {
            for (int i = 0; i < Cadastro.totalFuncionarios; i++) {
                bw.write(limpar(Cadastro.nomesFuncionarios[i]) + SEPARADOR
                        + limpar(Cadastro.matriculas[i]) + SEPARADOR
                        + limpar(Cadastro.tiposFuncionarios[i]));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar funcionarios.txt: " + e.getMessage());
        }
    }

    private static void salvarTalhoes() {
        try (BufferedWriter bw = criarEscritor("talhoes.txt")) {
            for (int i = 0; i < Cadastro.totalTalhoes; i++) {
                bw.write(limpar(Cadastro.codigosTalhoes[i]) + SEPARADOR
                        + limpar(Cadastro.nomesTalhoes[i]) + SEPARADOR
                        + limpar(Cadastro.variedadesCafe[i]) + SEPARADOR
                        + limpar(Cadastro.estimativas[i]));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar talhoes.txt: " + e.getMessage());
        }
    }

    private static void salvarFrota() {
        try (BufferedWriter bw = criarEscritor("frota.txt")) {
            for (int i = 0; i < Cadastro.totalTratores; i++) {
                bw.write(limpar(Cadastro.placas[i]) + SEPARADOR
                        + limpar(Cadastro.capacidades[i]));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar frota.txt: " + e.getMessage());
        }
    }

    private static void salvarColheitas(
            String[] datas,
            String[] matriculas,
            String[] codigosTalhao,
            String[] placasColheita,
            String[] litrosColhidos,
            String[] destinos) {

        try (BufferedWriter bw = criarEscritor("colheitas.txt")) {
            for (int i = 0; i < Colheita.quantidadeColheitas; i++) {
                bw.write(limpar(datas[i]) + SEPARADOR
                        + limpar(matriculas[i]) + SEPARADOR
                        + limpar(codigosTalhao[i]) + SEPARADOR
                        + limpar(placasColheita[i]) + SEPARADOR
                        + limpar(litrosColhidos[i]) + SEPARADOR
                        + limpar(destinos[i]));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar colheitas.txt: " + e.getMessage());
        }
    }

    private static void carregarFuncionarios() {
        Cadastro.totalFuncionarios = 0;

        try (BufferedReader br = criarLeitor("funcionarios.txt")) {
            String linha;

            while ((linha = br.readLine()) != null && Cadastro.totalFuncionarios < Cadastro.nomesFuncionarios.length) {
                String[] partes = linha.split(SEPARADOR, -1);

                if (partes.length >= 3) {
                    int pos = Cadastro.totalFuncionarios;
                    Cadastro.nomesFuncionarios[pos] = partes[0];
                    Cadastro.matriculas[pos] = partes[1];
                    Cadastro.tiposFuncionarios[pos] = partes[2];
                    Cadastro.totalFuncionarios++;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar funcionarios.txt: " + e.getMessage());
        }
    }

    private static void carregarTalhoes() {
        Cadastro.totalTalhoes = 0;

        try (BufferedReader br = criarLeitor("talhoes.txt")) {
            String linha;

            while ((linha = br.readLine()) != null && Cadastro.totalTalhoes < Cadastro.codigosTalhoes.length) {
                String[] partes = linha.split(SEPARADOR, -1);

                if (partes.length >= 4) {
                    int pos = Cadastro.totalTalhoes;
                    Cadastro.codigosTalhoes[pos] = partes[0];
                    Cadastro.nomesTalhoes[pos] = partes[1];
                    Cadastro.variedadesCafe[pos] = partes[2];
                    Cadastro.estimativas[pos] = partes[3];
                    Cadastro.totalTalhoes++;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar talhoes.txt: " + e.getMessage());
        }
    }

    private static void carregarFrota() {
        Cadastro.totalTratores = 0;

        try (BufferedReader br = criarLeitor("frota.txt")) {
            String linha;

            while ((linha = br.readLine()) != null && Cadastro.totalTratores < Cadastro.placas.length) {
                String[] partes = linha.split(SEPARADOR, -1);

                if (partes.length >= 2) {
                    int pos = Cadastro.totalTratores;
                    Cadastro.placas[pos] = partes[0];
                    Cadastro.capacidades[pos] = partes[1];
                    Cadastro.totalTratores++;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar frota.txt: " + e.getMessage());
        }
    }

    private static void carregarColheitas(
            String[] datas,
            String[] matriculas,
            String[] codigosTalhao,
            String[] placasColheita,
            String[] litrosColhidos,
            String[] destinos) {

        Colheita.quantidadeColheitas = 0;

        try (BufferedReader br = criarLeitor("colheitas.txt")) {
            String linha;

            while ((linha = br.readLine()) != null && Colheita.quantidadeColheitas < datas.length) {
                String[] partes = linha.split(SEPARADOR, -1);

                if (partes.length >= 6) {
                    int pos = Colheita.quantidadeColheitas;
                    datas[pos] = partes[0];
                    matriculas[pos] = partes[1];
                    codigosTalhao[pos] = partes[2];
                    placasColheita[pos] = partes[3];
                    litrosColhidos[pos] = partes[4];
                    destinos[pos] = partes[5];
                    Colheita.quantidadeColheitas++;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar colheitas.txt: " + e.getMessage());
        }
    }

    private static BufferedWriter criarEscritor(String nomeArquivo) throws IOException {
        return new BufferedWriter(new FileWriter(new File(pastaDados(), nomeArquivo)));
    }

    private static BufferedReader criarLeitor(String nomeArquivo) throws IOException {
        File arquivo = new File(pastaDados(), nomeArquivo);

        if (!arquivo.exists()) {
            arquivo.createNewFile();
        }

        return new BufferedReader(new FileReader(arquivo));
    }

    private static File pastaDados() {
        File atual = new File(System.getProperty("user.dir"));

        if (atual.getName().equals("files (3)")) {
            return atual.getParentFile();
        }

        return atual;
    }

    private static String limpar(String valor) {
        if (valor == null) {
            return "";
        }

        return valor.replace(SEPARADOR, ",").trim();
    }
}
