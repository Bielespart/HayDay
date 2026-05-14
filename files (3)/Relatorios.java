import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Relatorios {

    public static void exportarRelatorioQuinzenal(
            String[] datas,
            String[] matriculas,
            String[] codigosTalhao,
            String[] placas,
            String[] litrosColhidos,
            String[] destinos,
            int totalColheitas,
            Scanner sc) {

        System.out.println("\n--- Exportar Relatorio Geral ---");

        if (totalColheitas == 0) {
            System.out.println("Nenhuma colheita registrada para exportar.");
            return;
        }

        System.out.print("Identificacao da quinzena (ex: 01/05/2026 a 15/05/2026): ");
        String quinzena = sc.nextLine().trim();

        if (quinzena.equals("")) {
            System.out.println("Identificacao da quinzena nao pode ficar vazia.");
            return;
        }

        System.out.print("Valor de venda por litro: ");
        double valorVenda = lerDouble(sc.nextLine().trim());

        if (valorVenda <= 0) {
            System.out.println("Valor de venda invalido.");
            return;
        }

        System.out.print("Gasto por litro: ");
        double gastoLitro = lerDouble(sc.nextLine().trim());

        if (gastoLitro < 0) {
            System.out.println("Gasto invalido.");
            return;
        }

        int producaoTotal = 0;
        int totalTerreiro = 0;
        int totalSecador = 0;

        for (int i = 0; i < totalColheitas; i++) {
            int litros = converterInteiro(litrosColhidos[i], "litros colhidos");
            producaoTotal += litros;

            if (destinos[i] != null && destinos[i].equalsIgnoreCase("Terreiro")) {
                totalTerreiro += litros;
            } else if (destinos[i] != null && destinos[i].equalsIgnoreCase("Secador")) {
                totalSecador += litros;
            }
        }

        double receita = producaoTotal * valorVenda;
        double gastoProducao = producaoTotal * gastoLitro;
        double gastoSalarios = calcularGastoSalarios();
        double lucro = receita - gastoProducao - gastoSalarios;
        double valorTotalTratores = calcularValorTotalTratores();

        try {
            FileWriter fw = new FileWriter(new File(pastaDados(), "relatorio_quinzenal.txt"));
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("RELATORIO GERAL DO SISTEMA");
            bw.newLine();
            bw.write("Quinzena: " + quinzena);
            bw.newLine();
            bw.newLine();

            escreverResumoFinanceiro(bw, producaoTotal, totalTerreiro, totalSecador, valorVenda, gastoLitro,
                    receita, gastoProducao, gastoSalarios, lucro, valorTotalTratores);
            escreverFuncionarios(bw);
            escreverTalhoes(bw);
            escreverFrota(bw);
            escreverColheitas(bw, datas, matriculas, codigosTalhao, placas, litrosColhidos, destinos, totalColheitas);
            escreverArmazenamento(bw, totalTerreiro, totalSecador);

            bw.close();

            System.out.println("Relatorio exportado com sucesso para relatorio_quinzenal.txt");
            System.out.println("Relatorio exportado com dados da equipe, lavouras, maquinario, safra e armazenamento.");
            System.out.println("Producao total: " + producaoTotal + " L");
            System.out.println("Lucro se vender todo o estoque: R$ " + formatarDinheiro(lucro));
        } catch (IOException e) {
            System.out.println("Erro ao exportar relatorio_quinzenal.txt: " + e.getMessage());
        }
    }

    private static void escreverResumoFinanceiro(
            BufferedWriter bw,
            int producaoTotal,
            int totalTerreiro,
            int totalSecador,
            double valorVenda,
            double gastoLitro,
            double receita,
            double gastoProducao,
            double gastoSalarios,
            double lucro,
            double valorTotalTratores) throws IOException {

        bw.write("== RESUMO FINANCEIRO E PRODUCAO ==");
        bw.newLine();
        bw.write("Producao total: " + producaoTotal + " L");
        bw.newLine();
        bw.write("Total no terreiro: " + totalTerreiro + " L");
        bw.newLine();
        bw.write("Total no secador: " + totalSecador + " L");
        bw.newLine();
        bw.write("Valor de venda por litro: R$ " + formatarDinheiro(valorVenda));
        bw.newLine();
        bw.write("Gasto por litro: R$ " + formatarDinheiro(gastoLitro));
        bw.newLine();
        bw.write("Receita estimada: R$ " + formatarDinheiro(receita));
        bw.newLine();
        bw.write("Gasto estimado com producao: R$ " + formatarDinheiro(gastoProducao));
        bw.newLine();
        bw.write("Gasto com salarios: R$ " + formatarDinheiro(gastoSalarios));
        bw.newLine();
        bw.write("Lucro se vender todo o estoque: R$ " + formatarDinheiro(lucro));
        bw.newLine();
        bw.write("Valor total dos tratores: R$ " + formatarDinheiro(valorTotalTratores));
        bw.newLine();
        bw.newLine();
    }

    private static void escreverFuncionarios(BufferedWriter bw) throws IOException {
        bw.write("== EQUIPE DE CAMPO ==");
        bw.newLine();
        bw.write("Numero de funcionarios: " + Cadastro.totalFuncionarios);
        bw.newLine();
        bw.write("Gasto total com salarios: R$ " + formatarDinheiro(calcularGastoSalarios()));
        bw.newLine();

        for (int i = 0; i < Cadastro.totalFuncionarios; i++) {
            bw.write((i + 1) + " - Matricula: " + Cadastro.matriculas[i]
                    + " | Nome: " + Cadastro.nomesFuncionarios[i]
                    + " | Tipo: " + Cadastro.tiposFuncionarios[i]
                    + " | Salario: R$ " + Cadastro.salariosFuncionarios[i]);
            bw.newLine();
        }

        bw.newLine();
    }

    private static void escreverTalhoes(BufferedWriter bw) throws IOException {
        bw.write("== LAVOURAS E TALHOES ==");
        bw.newLine();
        bw.write("Numero de talhoes: " + Cadastro.totalTalhoes);
        bw.newLine();

        for (int i = 0; i < Cadastro.totalTalhoes; i++) {
            bw.write((i + 1) + " - Codigo: " + Cadastro.codigosTalhoes[i]
                    + " | Nome: " + Cadastro.nomesTalhoes[i]
                    + " | Variedade: " + Cadastro.variedadesCafe[i]
                    + " | Estimativa: " + Cadastro.estimativas[i] + " L");
            bw.newLine();
        }

        bw.newLine();
    }

    private static void escreverFrota(BufferedWriter bw) throws IOException {
        bw.write("== MAQUINARIO AGRICOLA ==");
        bw.newLine();
        bw.write("Numero de tratores: " + Cadastro.totalTratores);
        bw.newLine();
        bw.write("Valor total dos tratores: R$ " + formatarDinheiro(calcularValorTotalTratores()));
        bw.newLine();

        for (int i = 0; i < Cadastro.totalTratores; i++) {
            bw.write((i + 1) + " - Placa: " + Cadastro.placas[i]
                    + " | Capacidade: " + Cadastro.capacidades[i] + " L"
                    + " | Valor: R$ " + Cadastro.valoresTratores[i]);
            bw.newLine();
        }

        bw.newLine();
    }

    private static void escreverColheitas(
            BufferedWriter bw,
            String[] datas,
            String[] matriculas,
            String[] codigosTalhao,
            String[] placas,
            String[] litrosColhidos,
            String[] destinos,
            int totalColheitas) throws IOException {

        bw.write("== SAFRA COLHIDA ==");
        bw.newLine();
        bw.write("Numero de colheitas: " + totalColheitas);
        bw.newLine();

        for (int i = 0; i < totalColheitas; i++) {
            bw.write((i + 1) + " - Data: " + datas[i]
                    + " | Matricula: " + matriculas[i]
                    + " | Talhao: " + codigosTalhao[i]
                    + " | Trator: " + placas[i]
                    + " | Litros: " + litrosColhidos[i]
                    + " | Destino: " + destinos[i]);
            bw.newLine();
        }

        bw.newLine();
    }

    private static void escreverArmazenamento(BufferedWriter bw, int totalTerreiro, int totalSecador) throws IOException {
        bw.write("== ARMAZENAMENTO DA PRODUCAO ==");
        bw.newLine();
        bw.write("Total no terreiro: " + totalTerreiro + " L");
        bw.newLine();
        bw.write("Total no secador: " + totalSecador + " L");
        bw.newLine();
        bw.write("Total armazenado: " + (totalTerreiro + totalSecador) + " L");
        bw.newLine();
        bw.newLine();
    }

    // ─────────────────────────────────────────────────────────────
    // RELATÓRIO A — Acerto da Quinzena
    // ─────────────────────────────────────────────────────────────
    public static void acertoQuinzena(
            ArrayList<String[]> funcionarios,
            ArrayList<String[]> colheitas) {

        System.out.println("\n══════════════════════════════════════════");
        System.out.println("   RELATÓRIO A — Acerto da Quinzena");
        System.out.println("══════════════════════════════════════════");

        if (colheitas.isEmpty()) {
            System.out.println("Nenhuma colheita registrada.");
            return;
        }

        int totalGeral = 0;

        for (int i = 0; i < funcionarios.size(); i++) {
            String[] func = funcionarios.get(i);
            String matricula = func[1];
            int totalFuncionario = 0;

            for (int j = 0; j < colheitas.size(); j++) {
                if (colheitas.get(j)[1].equals(matricula)) {
                    totalFuncionario += converterInteiro(colheitas.get(j)[4], "litros colhidos");
                }
            }

            String linha = func[0] + " (" + matricula + ")";
            System.out.printf("%-35s %,d litros%n", linha, totalFuncionario);
            totalGeral += totalFuncionario;
        }

        System.out.println("──────────────────────────────────────────");
        System.out.printf("%-35s %,d litros%n", "TOTAL GERAL", totalGeral);
        System.out.println("══════════════════════════════════════════");
    }

    // ─────────────────────────────────────────────────────────────
    // RELATÓRIO B — Fechamento do Talhão
    // ─────────────────────────────────────────────────────────────
    public static void fechamentoTalhao(
            ArrayList<String[]> talhoes,
            ArrayList<String[]> colheitas) {

        System.out.println("\n══════════════════════════════════════════");
        System.out.println("   RELATÓRIO B — Fechamento do Talhão");
        System.out.println("══════════════════════════════════════════");

        if (talhoes.isEmpty()) {
            System.out.println("Nenhum talhão cadastrado.");
            return;
        }

        for (int i = 0; i < talhoes.size(); i++) {
            String[] talhao = talhoes.get(i);
            String codigo = talhao[0];
            int estimativa = converterInteiro(talhao[3], "estimativa do talhão");
            int produzido = 0;

            for (int j = 0; j < colheitas.size(); j++) {
                if (colheitas.get(j)[2].equalsIgnoreCase(codigo)) {
                    produzido += converterInteiro(colheitas.get(j)[4], "litros colhidos");
                }
            }

            int percentual = (estimativa > 0) ? (produzido * 100 / estimativa) : 0;
            String status = produzido >= estimativa ? "META ATINGIDA ✓" : "EM ANDAMENTO";

            System.out.println("Talhão " + codigo + " — " + talhao[1] + " (" + talhao[2] + ")");
            System.out.printf("  Estimativa: %,d litros%n", estimativa);
            System.out.printf("  Produzido:  %,d litros (%d%%) → %s%n", produzido, percentual, status);
            System.out.println();
        }
        System.out.println("══════════════════════════════════════════");
    }

    // ─────────────────────────────────────────────────────────────
    // RELATÓRIO C — Secagem
    // ─────────────────────────────────────────────────────────────
    public static void relatorioSecagem(ArrayList<String[]> colheitas) {

        System.out.println("\n══════════════════════════════════════════");
        System.out.println("   RELATÓRIO C — Destino da Produção");
        System.out.println("══════════════════════════════════════════");

        int totalTerreiro = 0;
        int totalSecador = 0;

        for (int i = 0; i < colheitas.size(); i++) {
            String[] c = colheitas.get(i);
            int litros = converterInteiro(c[4], "litros colhidos");
            if (c[5].equals("Terreiro")) {
                totalTerreiro += litros;
            } else if (c[5].equals("Secador")) {
                totalSecador += litros;
            }
        }

        int totalGeral = totalTerreiro + totalSecador;
        System.out.printf("  Secador Mecânico:     %,d litros%n", totalSecador);
        System.out.printf("  Terreiro de Cimento:  %,d litros%n", totalTerreiro);
        System.out.println("  ──────────────────────────────────");
        System.out.printf("  Total geral:          %,d litros%n", totalGeral);
        System.out.println("══════════════════════════════════════════");
    }

    // ─────────────────────────────────────────────────────────────
    // DIFERENCIAL ★ — Alerta de Meta (80%)
    // ─────────────────────────────────────────────────────────────
    public static void alertaEstimativa(
            ArrayList<String[]> talhoes,
            ArrayList<String[]> colheitas) {

        System.out.println("\n══════════════════════════════════════════");
        System.out.println("   ★  ALERTA DE META — Talhões Críticos");
        System.out.println("══════════════════════════════════════════");

        boolean encontrou = false;

        for (int i = 0; i < talhoes.size(); i++) {
            String[] talhao = talhoes.get(i);
            String codigo = talhao[0];
            int estimativa = converterInteiro(talhao[3], "estimativa do talhão");
            int produzido = 0;

            for (int j = 0; j < colheitas.size(); j++) {
                if (colheitas.get(j)[2].equalsIgnoreCase(codigo)) {
                    produzido += converterInteiro(colheitas.get(j)[4], "litros colhidos");
                }
            }

            if (estimativa > 0) {
                int percentual = produzido * 100 / estimativa;
                if (percentual >= 80 && produzido < estimativa) {
                    int restante = estimativa - produzido;
                    System.out.println("⚠  ALERTA — Talhão " + codigo + " (" + talhao[1] + ") atingiu " + percentual + "% da estimativa!");
                    System.out.printf("   Estimativa: %,dL  |  Produzido: %,dL  |  Restante: %,dL%n",
                            estimativa, produzido, restante);
                    System.out.println();
                    encontrou = true;
                } else if (produzido >= estimativa) {
                    System.out.println("✔  Talhão " + codigo + " (" + talhao[1] + ") — META ATINGIDA (" + percentual + "%)");
                    encontrou = true;
                }
            }
        }

        if (!encontrou) {
            System.out.println("  Nenhum talhão atingiu 80% da estimativa ainda.");
        }
        System.out.println("══════════════════════════════════════════");
    }

    private static int converterInteiro(String valor, String campo) {

        if (valor == null) {
            System.out.println("Valor vazio em " + campo + ". Considerando 0.");
            return 0;
        }

        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            System.out.println("Valor invalido em " + campo + ": " + valor + ". Considerando 0.");
            return 0;
        }
    }

    private static double lerDouble(String valor) {
        try {
            return Double.parseDouble(valor.replace(",", "."));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double calcularGastoSalarios() {
        double total = 0;

        for (int i = 0; i < Cadastro.totalFuncionarios; i++) {
            total += lerDoubleSeguro(Cadastro.salariosFuncionarios[i]);
        }

        return total;
    }

    private static double calcularValorTotalTratores() {
        double total = 0;

        for (int i = 0; i < Cadastro.totalTratores; i++) {
            total += lerDoubleSeguro(Cadastro.valoresTratores[i]);
        }

        return total;
    }

    private static double lerDoubleSeguro(String valor) {
        if (valor == null) {
            return 0;
        }

        try {
            return Double.parseDouble(valor.replace(",", "."));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static String formatarDinheiro(double valor) {
        return String.format("%.2f", valor);
    }

    private static File pastaDados() {
        File atual = new File(System.getProperty("user.dir"));

        if (atual.getName().equals("files (3)")) {
            return atual.getParentFile();
        }

        return atual;
    }
}
