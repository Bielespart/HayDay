import java.util.Scanner;

public class Main {

    static String[] funcionarios = new String[100];
    static String[] talhoes = new String[100];
    static String[] frota = new String[100];

    static String[] datas = new String[100];
    static String[] matriculas = new String[100];
    static String[] codigosTalhao = new String[100];
    static String[] placas = new String[100];
    static String[] litrosColhidos = new String[100];
    static String[] destinos = new String[100];

    static int qtdFuncionarios = 0;
    static int qtdTalhoes = 0;
    static int qtdFrota = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();

            System.out.print("Opcao: ");
            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1:
                    System.out.println("Cadastrar funcionario");
                    break;

                case 2:
                    System.out.println("Cadastrar talhao");
                    break;

                case 3:
                    System.out.println("Cadastrar trator");
                    break;

                case 4:
                    System.out.println("Listar funcionarios");
                    break;

                case 5:
                    System.out.println("Listar talhoes");
                    break;

                case 6:
                    System.out.println("Listar tratores");
                    break;

                case 7:
                    Colheita.registrarColheita(
                            funcionarios,
                            qtdFuncionarios,
                            talhoes,
                            qtdTalhoes,
                            frota,
                            qtdFrota,
                            datas,
                            matriculas,
                            codigosTalhao,
                            placas,
                            litrosColhidos,
                            destinos,
                            sc);
                    break;

                case 8:
                    Colheita.listarColheitas(
                            datas,
                            matriculas,
                            codigosTalhao,
                            placas,
                            litrosColhidos,
                            destinos);
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
        System.out.println("0 - Sair");
    }
}