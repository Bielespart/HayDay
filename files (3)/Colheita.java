import java.util.ArrayList;
import java.util.Scanner;

public class Colheita {

    public static void registrarColheita(
            ArrayList<String[]> funcionarios,
            ArrayList<String[]> talhoes,
            ArrayList<String[]> frota,
            ArrayList<String[]> colheitas,
            Scanner sc) {

        System.out.println("\n── Registrar Colheita ──");

        // ── Data ────────────────────────────────
        System.out.print("Data (dd/mm/aaaa): ");
        String data = sc.nextLine().trim();

        // ── Funcionário ─────────────────────────
        System.out.print("Matrícula do funcionário: ");
        String matricula = sc.nextLine().trim();
        String[] funcionario = Cadastro.buscarPorMatricula(funcionarios, matricula);
        if (funcionario == null) {
            System.out.println("✗ Funcionário não encontrado! Cadastre-o antes de lançar colheita.");
            return;
        }
        System.out.println("  → " + funcionario[0] + " (" + funcionario[2] + ")");

        // ── Talhão ──────────────────────────────
        System.out.print("Código do talhão: ");
        String codigoTalhao = sc.nextLine().trim().toUpperCase();
        String[] talhao = Cadastro.buscarTalhao(talhoes, codigoTalhao);
        if (talhao == null) {
            System.out.println("✗ Talhão não encontrado! Cadastre-o antes de lançar colheita.");
            return;
        }
        System.out.println("  → " + talhao[1] + " (" + talhao[2] + ")");

        // ── Trator ──────────────────────────────
        System.out.print("Placa do trator: ");
        String placa = sc.nextLine().trim().toUpperCase();
        String[] trator = Cadastro.buscarTrator(frota, placa);
        if (trator == null) {
            System.out.println("✗ Trator não encontrado! Cadastre-o antes de lançar colheita.");
            return;
        }
        System.out.println("  → Trator " + trator[0] + " | Capacidade: " + trator[1] + "L");

        // ── Litros ──────────────────────────────
        System.out.print("Volume colhido (litros): ");
        String litrosStr = sc.nextLine().trim();
        int litros;
        try {
            litros = Integer.parseInt(litrosStr);
            if (litros <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("✗ Volume inválido!");
            return;
        }

        // ── Validação de capacidade ──────────────
        int capacidade = Integer.parseInt(trator[1]);
        if (litros > capacidade) {
            System.out.println("✗ Volume excede a capacidade do trator!");
            System.out.println("  Capacidade: " + capacidade + "L  |  Informado: " + litros + "L");
            return;
        }

        // ── Destino ─────────────────────────────
        String destino = "";
        while (!destino.equals("1") && !destino.equals("2")) {
            System.out.print("Destino (1 - Terreiro / 2 - Secador): ");
            destino = sc.nextLine().trim();
        }
        String destinoStr = destino.equals("1") ? "Terreiro" : "Secador";

        // ── Registra ────────────────────────────
        String[] colheita = {data, matricula, codigoTalhao, placa, litrosStr, destinoStr};
        colheitas.add(colheita);

        System.out.println("\n✔ Colheita registrada com sucesso!");
        System.out.println("  " + data + " | " + funcionario[0] + " | " + talhao[1]
                + " | " + litros + "L → " + destinoStr);
    }

    public static void listarColheitas(ArrayList<String[]> colheitas) {
        System.out.println("\n── Colheitas Registradas ──");
        if (colheitas.isEmpty()) {
            System.out.println("Nenhuma colheita registrada.");
            return;
        }
        System.out.printf("%-12s %-8s %-8s %-12s %-8s %-10s%n",
                "Data", "Mat.", "Talhão", "Placa", "Litros", "Destino");
        System.out.println("─".repeat(60));
        for (int i = 0; i < colheitas.size(); i++) {
            String[] c = colheitas.get(i);
            System.out.printf("%-12s %-8s %-8s %-12s %-8s %-10s%n",
                    c[0], c[1], c[2], c[3], c[4], c[5]);
        }
    }
}
