import java.util.ArrayList;

public class Relatorios {

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
                    totalFuncionario += Integer.parseInt(colheitas.get(j)[4]);
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
            int estimativa = Integer.parseInt(talhao[3]);
            int produzido = 0;

            for (int j = 0; j < colheitas.size(); j++) {
                if (colheitas.get(j)[2].equalsIgnoreCase(codigo)) {
                    produzido += Integer.parseInt(colheitas.get(j)[4]);
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
            int litros = Integer.parseInt(c[4]);
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
            int estimativa = Integer.parseInt(talhao[3]);
            int produzido = 0;

            for (int j = 0; j < colheitas.size(); j++) {
                if (colheitas.get(j)[2].equalsIgnoreCase(codigo)) {
                    produzido += Integer.parseInt(colheitas.get(j)[4]);
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
}
