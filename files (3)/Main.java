import java.util.Scanner;

public class Main {

    static String[] datas = new String[100];
    static String[] matriculas = new String[100];
    static String[] codigosTalhao = new String[100];
    static String[] placas = new String[100];
    static String[] litrosColhidos = new String[100];
    static String[] destinos = new String[100];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();

            System.out.print("Opcao: ");
            try {
                opcao = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opcao invalida. Digite apenas numeros.");
                continue;
            }

            switch (opcao) {
                case 1:
                    Cadastro.cadastrarFuncionario(sc);
                    break;

                case 2:
                    Cadastro.cadastrarTalhao(sc);
                    break;

                case 3:
                    Cadastro.cadastrarTrator(sc);
                    break;

                case 4:
                    Cadastro.listarFuncionarios();
                    aguardarTecla(sc);
                    break;

                case 5:
                    Cadastro.listarTalhoes();
                    aguardarTecla(sc);
                    break;

                case 6:
                    Cadastro.listarTratores();
                    aguardarTecla(sc);
                    break;

                case 7:
                    Colheita.registrarColheita(
                            Cadastro.nomesFuncionarios,
                            Cadastro.totalFuncionarios,
                            Cadastro.nomesTalhoes,
                            Cadastro.totalTalhoes,
                            Cadastro.placas,
                            Cadastro.totalTratores,
                            datas,
                            matriculas,
                            codigosTalhao,
                            placas,
                            litrosColhidos,
                            destinos,
                            sc);
                    aguardarTecla(sc);
                    break;

                case 8:
                    Colheita.listarColheitas(
                            datas,
                            matriculas,
                            codigosTalhao,
                            placas,
                            litrosColhidos,
                            destinos);
                    aguardarTecla(sc);
                    break;

                case 9:
                    Colheita.pesquisarColheita(
                            datas,
                            matriculas,
                            codigosTalhao,
                            placas,
                            litrosColhidos,
                            destinos,
                            sc);
                    aguardarTecla(sc);
                    break;

                case 0:
                    System.out.println("Sistema encerrado.");
                    break;

                default:
                    System.out.println("Opcao invalida.");
            }
        }

        sc.close();
    }

    public static void exibirMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1 - Cadastrar Funcionario");
        System.out.println("2 - Cadastrar Talhao");
        System.out.println("3 - Cadastrar Trator");
        System.out.println("4 - Listar Funcionarios");
        System.out.println("5 - Listar Talhoes");
        System.out.println("6 - Listar Tratores");
        System.out.println("7 - Registrar Colheita");
        System.out.println("8 - Listar Colheitas");
        System.out.println("9 - Pesquisar Colheita");
        System.out.println("0 - Sair");
    }

    public static void aguardarTecla(Scanner sc) {
        System.out.print("\nPressione Enter para voltar ao menu principal...");
        sc.nextLine();
    }
}
