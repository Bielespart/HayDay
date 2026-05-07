import java.util.Scanner;

public class Cadastro {

    // Funcionários
    public static String nomesFuncionarios[] = new String[100];
    public static String matriculas[] = new String[100];
    public static String tiposFuncionarios[] = new String[100];
    public static int totalFuncionarios = 0;

    // Talhões
    public static String codigosTalhoes[] = new String[100];
    public static String nomesTalhoes[] = new String[100];
    public static String variedadesCafe[] = new String[100];
    public static String estimativas[] = new String[100];
    public static int totalTalhoes = 0;

    // Frota
    public static String placas[] = new String[100];
    public static String capacidades[] = new String[100];
    public static int totalTratores = 0;

    // ─────────────────────────────────────────
    // FUNCIONÁRIOS
    // ─────────────────────────────────────────

    public static void cadastrarFuncionario(Scanner sc) {

        System.out.println("\n── Cadastrar Funcionário ──");

        System.out.print("Nome completo: ");
        String nome = sc.nextLine().trim();

        System.out.print("Matrícula: ");
        String matricula = sc.nextLine().trim();

        // Verifica duplicidade
        if (buscarPorMatricula(matricula) != -1) {
            System.out.println("✗ Matrícula já cadastrada!");
            return;
        }

        String tipo = "";

        while (!tipo.equals("1") && !tipo.equals("2")) {

            System.out.print("Tipo (1 - Diarista / 2 - Fixo): ");
            tipo = sc.nextLine().trim();
        }

        String tipoStr;

        if (tipo.equals("1")) {
            tipoStr = "Diarista";
        } else {
            tipoStr = "Fixo";
        }

        nomesFuncionarios[totalFuncionarios] = nome;
        matriculas[totalFuncionarios] = matricula;
        tiposFuncionarios[totalFuncionarios] = tipoStr;

        totalFuncionarios++;

        System.out.println("✔ Funcionário cadastrado!");
    }

    public static void listarFuncionarios() {

        System.out.println("\n── Lista de Funcionários ──");

        if (totalFuncionarios == 0) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }

        System.out.printf("%-10s %-25s %-10s%n", "Mat.", "Nome", "Tipo");
        System.out.println("────────────────────────────────────────────");

        for (int i = 0; i < totalFuncionarios; i++) {

            System.out.printf(
                "%-10s %-25s %-10s%n",
                matriculas[i],
                nomesFuncionarios[i],
                tiposFuncionarios[i]
            );
        }
    }

    public static int buscarPorMatricula(String matricula) {

        for (int i = 0; i < totalFuncionarios; i++) {

            if (matriculas[i].equals(matricula)) {
                return i;
            }
        }

        return -1;
    }

    // ─────────────────────────────────────────
    // TALHÕES
    // ─────────────────────────────────────────

    public static void cadastrarTalhao(Scanner sc) {

        System.out.println("\n── Cadastrar Talhão ──");

        System.out.print("Código do talhão (ex: T01): ");
        String codigo = sc.nextLine().trim().toUpperCase();

        if (buscarTalhao(codigo) != -1) {
            System.out.println("✗ Código já cadastrado!");
            return;
        }

        System.out.print("Nome do talhão: ");
        String nome = sc.nextLine().trim();

        System.out.print("Variedade do café: ");
        String variedade = sc.nextLine().trim();

        System.out.print("Estimativa de produção (litros): ");
        String estimativa = sc.nextLine().trim();

        if (isNumericoPositivo(estimativa)) {

            codigosTalhoes[totalTalhoes] = codigo;
            nomesTalhoes[totalTalhoes] = nome;
            variedadesCafe[totalTalhoes] = variedade;
            estimativas[totalTalhoes] = estimativa;

            totalTalhoes++;

            System.out.println("✔ Talhão cadastrado!");

        } else {

            System.out.println("✗ Estimativa inválida!");
        }
    }

    public static void listarTalhoes() {

        System.out.println("\n── Lista de Talhões ──");

        if (totalTalhoes == 0) {
            System.out.println("Nenhum talhão cadastrado.");
            return;
        }

        System.out.printf(
            "%-6s %-20s %-15s %-12s%n",
            "Cód.",
            "Nome",
            "Variedade",
            "Estimativa"
        );

        System.out.println("────────────────────────────────────────────────────");

        for (int i = 0; i < totalTalhoes; i++) {

            System.out.printf(
                "%-6s %-20s %-15s %s L%n",
                codigosTalhoes[i],
                nomesTalhoes[i],
                variedadesCafe[i],
                estimativas[i]
            );
        }
    }

    public static int buscarTalhao(String codigo) {

        for (int i = 0; i < totalTalhoes; i++) {

            if (codigosTalhoes[i].equalsIgnoreCase(codigo)) {
                return i;
            }
        }

        return -1;
    }

    // ─────────────────────────────────────────
    // FROTA (TRATORES)
    // ─────────────────────────────────────────

    public static void cadastrarTrator(Scanner sc) {

        System.out.println("\n── Cadastrar Trator ──");

        System.out.print("Placa (ex: ABC-1234): ");
        String placa = sc.nextLine().trim().toUpperCase();

        if (buscarTrator(placa) != -1) {
            System.out.println("✗ Placa já cadastrada!");
            return;
        }

        System.out.print("Capacidade da carreta (litros): ");
        String capacidade = sc.nextLine().trim();

        if (isNumericoPositivo(capacidade)) {

            placas[totalTratores] = placa;
            capacidades[totalTratores] = capacidade;

            totalTratores++;

            System.out.println("✔ Trator cadastrado!");

        } else {

            System.out.println("✗ Capacidade inválida!");
        }
    }

    public static void listarTratores() {

        System.out.println("\n── Lista de Tratores ──");

        if (totalTratores == 0) {
            System.out.println("Nenhum trator cadastrado.");
            return;
        }

        System.out.printf("%-12s %-15s%n", "Placa", "Capacidade (L)");
        System.out.println("────────────────────────────");

        for (int i = 0; i < totalTratores; i++) {

            System.out.printf(
                "%-12s %-15s%n",
                placas[i],
                capacidades[i]
            );
        }
    }

    public static int buscarTrator(String placa) {

        for (int i = 0; i < totalTratores; i++) {

            if (placas[i].equalsIgnoreCase(placa)) {
                return i;
            }
        }

        return -1;
    }

    // ─────────────────────────────────────────
    // UTILITÁRIO
    // ─────────────────────────────────────────

    private static boolean isNumericoPositivo(String valor) {

        if (valor.length() == 0) {
            return false;
        }

        for (int i = 0; i < valor.length(); i++) {

            char c = valor.charAt(i);

            if (c < '0' || c > '9') {
                return false;
            }
        }

        try {

            int numero = Integer.parseInt(valor);

            return numero > 0;

        } catch (NumberFormatException e) {

            return false;
        }
    }
}