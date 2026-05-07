import java.util.ArrayList;
import java.util.Scanner;

public class Cadastro {

    // ─────────────────────────────────────────
    // FUNCIONÁRIOS
    // ─────────────────────────────────────────

    public static void cadastrarFuncionario(ArrayList<String[]> funcionarios, Scanner sc) {
        System.out.println("\n── Cadastrar Funcionário ──");

        System.out.print("Nome completo: ");
        String nome = sc.nextLine().trim();

        System.out.print("Matrícula: ");
        String matricula = sc.nextLine().trim();

        // Verifica duplicidade
        if (buscarPorMatricula(funcionarios, matricula) != null) {
            System.out.println("✗ Matrícula já cadastrada!");
            return;
        }

        String tipo = "";
        while (!tipo.equals("1") && !tipo.equals("2")) {
            System.out.print("Tipo (1 - Diarista / 2 - Fixo): ");
            tipo = sc.nextLine().trim();
        }
        String tipoStr = tipo.equals("1") ? "Diarista" : "Fixo";

        String[] funcionario = {nome, matricula, tipoStr};
        funcionarios.add(funcionario);
        System.out.println("✔ Funcionário cadastrado: " + nome + " [" + matricula + "]");
    }

    public static void listarFuncionarios(ArrayList<String[]> funcionarios) {
        System.out.println("\n── Lista de Funcionários ──");
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }
        System.out.printf("%-5s %-25s %-10s%n", "Mat.", "Nome", "Tipo");
        System.out.println("─".repeat(42));
        for (int i = 0; i < funcionarios.size(); i++) {
            String[] f = funcionarios.get(i);
            System.out.printf("%-5s %-25s %-10s%n", f[1], f[0], f[2]);
        }
    }

    public static String[] buscarPorMatricula(ArrayList<String[]> funcionarios, String matricula) {
        for (int i = 0; i < funcionarios.size(); i++) {
            if (funcionarios.get(i)[1].equals(matricula)) {
                return funcionarios.get(i);
            }
        }
        return null;
    }

    // ─────────────────────────────────────────
    // TALHÕES
    // ─────────────────────────────────────────

    public static void cadastrarTalhao(ArrayList<String[]> talhoes, Scanner sc) {
        System.out.println("\n── Cadastrar Talhão ──");

        System.out.print("Código do talhão (ex: T01): ");
        String codigo = sc.nextLine().trim().toUpperCase();

        if (buscarTalhao(talhoes, codigo) != null) {
            System.out.println("✗ Código já cadastrado!");
            return;
        }

        System.out.print("Nome do talhão: ");
        String nome = sc.nextLine().trim();

        System.out.print("Variedade do café: ");
        String variedade = sc.nextLine().trim();

        System.out.print("Estimativa de produção (litros): ");
        String estimativa = sc.nextLine().trim();

        if (!isNumericoPositivo(estimativa)) {
            System.out.println("✗ Estimativa inválida!");
            return;
        }

        String[] talhao = {codigo, nome, variedade, estimativa};
        talhoes.add(talhao);
        System.out.println("✔ Talhão cadastrado: " + nome + " [" + codigo + "]");
    }

    public static void listarTalhoes(ArrayList<String[]> talhoes) {
        System.out.println("\n── Lista de Talhões ──");
        if (talhoes.isEmpty()) {
            System.out.println("Nenhum talhão cadastrado.");
            return;
        }
        System.out.printf("%-6s %-20s %-15s %-12s%n", "Cód.", "Nome", "Variedade", "Estimativa");
        System.out.println("─".repeat(55));
        for (int i = 0; i < talhoes.size(); i++) {
            String[] t = talhoes.get(i);
            System.out.printf("%-6s %-20s %-15s %s L%n", t[0], t[1], t[2], t[3]);
        }
    }

    public static String[] buscarTalhao(ArrayList<String[]> talhoes, String codigo) {
        for (int i = 0; i < talhoes.size(); i++) {
            if (talhoes.get(i)[0].equalsIgnoreCase(codigo)) {
                return talhoes.get(i);
            }
        }
        return null;
    }

    // ─────────────────────────────────────────
    // FROTA (TRATORES)
    // ─────────────────────────────────────────

    public static void cadastrarTrator(ArrayList<String[]> frota, Scanner sc) {
        System.out.println("\n── Cadastrar Trator ──");

        System.out.print("Placa (ex: ABC-1234): ");
        String placa = sc.nextLine().trim().toUpperCase();

        if (buscarTrator(frota, placa) != null) {
            System.out.println("✗ Placa já cadastrada!");
            return;
        }

        System.out.print("Capacidade da carreta (litros): ");
        String capacidade = sc.nextLine().trim();

        if (!isNumericoPositivo(capacidade)) {
            System.out.println("✗ Capacidade inválida!");
            return;
        }

        String[] trator = {placa, capacidade};
        frota.add(trator);
        System.out.println("✔ Trator cadastrado: " + placa + " | Capacidade: " + capacidade + "L");
    }

    public static void listarTratores(ArrayList<String[]> frota) {
        System.out.println("\n── Lista de Tratores ──");
        if (frota.isEmpty()) {
            System.out.println("Nenhum trator cadastrado.");
            return;
        }
        System.out.printf("%-12s %-15s%n", "Placa", "Capacidade (L)");
        System.out.println("─".repeat(28));
        for (int i = 0; i < frota.size(); i++) {
            String[] t = frota.get(i);
            System.out.printf("%-12s %-15s%n", t[0], t[1]);
        }
    }

    public static String[] buscarTrator(ArrayList<String[]> frota, String placa) {
        for (int i = 0; i < frota.size(); i++) {
            if (frota.get(i)[0].equalsIgnoreCase(placa)) {
                return frota.get(i);
            }
        }
        return null;
    }

    // ─────────────────────────────────────────
    // UTILITÁRIO
    // ─────────────────────────────────────────

    private static boolean isNumericoPositivo(String valor) {
        try {
            return Integer.parseInt(valor) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
