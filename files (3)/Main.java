import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // ── ArrayLists globais ──────────────────────────────
    static ArrayList<String[]> funcionarios = new ArrayList<String[]>();
    static ArrayList<String[]> talhoes      = new ArrayList<String[]>();
    static ArrayList<String[]> frota        = new ArrayList<String[]>();
    static ArrayList<String[]> colheitas    = new ArrayList<String[]>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GESTÃO — Fazenda Esperança  ║");
        System.out.println("╚══════════════════════════════════════════╝");

        // Carrega dados salvos anteriormente
        System.out.println("\nCarregando dados...");
        Persistencia.carregarDados(funcionarios, talhoes, frota, colheitas);

        int opcao = -1;
        while (opcao != 0) {
            exibirMenu();
            System.out.print("Opção: ");
            String entrada = sc.nextLine().trim();
            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {

                // ── Cadastros ────────────────────────────────
                case 1:
                    Cadastro.cadastrarFuncionario(funcionarios, sc);
                    break;
                case 2:
                    Cadastro.cadastrarTalhao(talhoes, sc);
                    break;
                case 3:
                    Cadastro.cadastrarTrator(frota, sc);
                    break;

                // ── Listagens ────────────────────────────────
                case 4:
                    Cadastro.listarFuncionarios(funcionarios);
                    break;
                case 5:
                    Cadastro.listarTalhoes(talhoes);
                    break;
                case 6:
                    Cadastro.listarTratores(frota);
                    break;

                // ── Colheita ─────────────────────────────────
                case 7:
                    Colheita.registrarColheita(funcionarios, talhoes, frota, colheitas, sc);
                    break;
                case 8:
                    Colheita.listarColheitas(colheitas);
                    break;

                // ── Relatórios ───────────────────────────────
                case 9:
                    menuRelatorios(sc);
                    break;

                // ── Salvar ───────────────────────────────────
                case 10:
                    Persistencia.salvarDados(funcionarios, talhoes, frota, colheitas);
                    break;

                // ── Sair ─────────────────────────────────────
                case 0:
                    System.out.println("\nSalvando dados antes de sair...");
                    Persistencia.salvarDados(funcionarios, talhoes, frota, colheitas);
                    System.out.println("\nAté logo! Sistema encerrado.\n");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        sc.close();
    }

    // ─────────────────────────────────────────────────────────────
    private static void exibirMenu() {
        System.out.println("\n┌─────────────────────────────────────────┐");
        System.out.println("│             MENU PRINCIPAL               │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("│  CADASTROS                               │");
        System.out.println("│   1 - Cadastrar Funcionário              │");
        System.out.println("│   2 - Cadastrar Talhão                   │");
        System.out.println("│   3 - Cadastrar Trator                   │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("│  LISTAGENS                               │");
        System.out.println("│   4 - Listar Funcionários                │");
        System.out.println("│   5 - Listar Talhões                     │");
        System.out.println("│   6 - Listar Tratores                    │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("│  COLHEITA                                │");
        System.out.println("│   7 - Registrar Colheita                 │");
        System.out.println("│   8 - Listar Colheitas                   │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("│   9 - Relatórios                         │");
        System.out.println("│  10 - Salvar Dados                       │");
        System.out.println("│   0 - Sair                               │");
        System.out.println("└─────────────────────────────────────────┘");
    }

    // ─────────────────────────────────────────────────────────────
    private static void menuRelatorios(Scanner sc) {
        System.out.println("\n┌──────────────────────────────────┐");
        System.out.println("│          RELATÓRIOS               │");
        System.out.println("├──────────────────────────────────┤");
        System.out.println("│  A - Acerto da Quinzena           │");
        System.out.println("│  B - Fechamento do Talhão         │");
        System.out.println("│  C - Destino da Produção          │");
        System.out.println("│  D - ★ Alerta de Meta (80%)       │");
        System.out.println("│  V - Voltar                       │");
        System.out.println("└──────────────────────────────────┘");
        System.out.print("Opção: ");
        String op = sc.nextLine().trim().toUpperCase();

        switch (op) {
            case "A":
                Relatorios.acertoQuinzena(funcionarios, colheitas);
                break;
            case "B":
                Relatorios.fechamentoTalhao(talhoes, colheitas);
                break;
            case "C":
                Relatorios.relatorioSecagem(colheitas);
                break;
            case "D":
                Relatorios.alertaEstimativa(talhoes, colheitas);
                break;
            case "V":
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }
}
