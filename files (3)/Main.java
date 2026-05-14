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

        Persistencia.carregarTudo(datas, matriculas, codigosTalhao, placas, litrosColhidos, destinos);
        Armazenamento.carregarResumo();

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
                    do {
                        int totalFuncionariosAntes = Cadastro.totalFuncionarios;
                        Cadastro.cadastrarFuncionario(sc);
                        if (Cadastro.totalFuncionarios > totalFuncionariosAntes) {
                            salvarDados();
                        }
                    } while (desejaNovoCadastro(sc));
                    break;

                case 2:
                    do {
                        int totalTalhoesAntes = Cadastro.totalTalhoes;
                        Cadastro.cadastrarTalhao(sc);
                        if (Cadastro.totalTalhoes > totalTalhoesAntes) {
                            salvarDados();
                        }
                    } while (desejaNovoCadastro(sc));
                    break;

                case 3:
                    do {
                        int totalTratoresAntes = Cadastro.totalTratores;
                        Cadastro.cadastrarTrator(sc);
                        if (Cadastro.totalTratores > totalTratoresAntes) {
                            salvarDados();
                        }
                    } while (desejaNovoCadastro(sc));
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
                    int totalColheitasAntes = Colheita.quantidadeColheitas;
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
                    if (Colheita.quantidadeColheitas > totalColheitasAntes) {
                        salvarDados();
                    }
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

                case 10:
                    listarModulos();
                    aguardarTecla(sc);
                    break;

                case 11:
                    Armazenamento.listarResumo(datas, litrosColhidos, destinos, Colheita.quantidadeColheitas);
                    aguardarTecla(sc);
                    break;

                case 12:
                    Relatorios.exportarRelatorioQuinzenal(
                            datas,
                            matriculas,
                            codigosTalhao,
                            placas,
                            litrosColhidos,
                            destinos,
                            Colheita.quantidadeColheitas,
                            sc);
                    aguardarTecla(sc);
                    break;

                case 0:
                    salvarDados();
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
        System.out.println("10 - Listar em ordem alfabetica");
        System.out.println("11 - Listar Armazenamento");
        System.out.println("12 - Exportar Relatorio Quinzenal");
        System.out.println("0 - Sair");
    }

    public static void listarModulos() {
        String[] modulos = {
                "Cadastro",
                "Colheita",
                "Main",
                "Armazenamento",
                "Persistencia",
                "Relatorios"
        };

        System.out.println("\n--- Modulos do Sistema ---");

        for (int i = 0; i < modulos.length - 1; i++) {
            for (int j = i + 1; j < modulos.length; j++) {
                if (modulos[i].compareToIgnoreCase(modulos[j]) > 0) {
                    String auxiliar = modulos[i];
                    modulos[i] = modulos[j];
                    modulos[j] = auxiliar;
                }
            }
        }

        for (int i = 0; i < modulos.length; i++) {
            System.out.println((i + 1) + " - " + modulos[i]);
        }
    }

    public static void aguardarTecla(Scanner sc) {
        System.out.print("\nPressione Enter para voltar ao menu principal...");
        sc.nextLine();
    }

    public static boolean desejaNovoCadastro(Scanner sc) {
        System.out.print("\nDigite 1 para realizar um novo cadastro ou qualquer outra tecla para voltar ao menu principal: ");
        String opcao = sc.nextLine().trim();

        return opcao.equals("1");
    }

    public static void salvarDados() {
        Persistencia.salvarTudo(datas, matriculas, codigosTalhao, placas, litrosColhidos, destinos);
        Armazenamento.salvarResumo(litrosColhidos, destinos, Colheita.quantidadeColheitas);
    }
}
