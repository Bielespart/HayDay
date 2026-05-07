import java.util.Scanner;

public class Colheita {

    public static int quantidadeColheitas = 0;

    public static void registrarColheita(
            String[] funcionarios,
            int qtdFuncionarios,

            String[] talhoes,
            int qtdTalhoes,

            String[] frota,
            int qtdFrota,

            String[] datas,
            String[] matriculas,
            String[] codigosTalhao,
            String[] placas,
            String[] litrosColhidos,
            String[] destinos,

            Scanner sc) {

        System.out.println("\n--- Registrar Colheita ---");

        System.out.print("Data (dd/mm/aaaa): ");
        String data = sc.nextLine();

        System.out.print("Matricula do funcionario: ");
        String matricula = sc.nextLine();

        int posFuncionario = Cadastro.buscarPorMatricula(matricula);

        if (posFuncionario == -1) {
            System.out.println("Funcionario nao encontrado!");
            return;
        }

        System.out.println("Funcionario: " + funcionarios[posFuncionario]);

        System.out.print("Codigo do talhao: ");
        String codigoTalhao = sc.nextLine();

        int posTalhao = Cadastro.buscarTalhao(codigoTalhao);

        if (posTalhao == -1) {
            System.out.println("Talhao nao encontrado!");
            return;
        }

        System.out.println("Talhao: " + talhoes[posTalhao]);

        System.out.print("Placa do trator: ");
        String placa = sc.nextLine();

        int posTrator = Cadastro.buscarTrator(placa);

        if (posTrator == -1) {
            System.out.println("Trator nao encontrado!");
            return;
        }

        System.out.println("Trator: " + frota[posTrator]);

        System.out.print("Volume colhido (litros): ");
        String litrosStr = sc.nextLine();

        int litros = Integer.parseInt(litrosStr);

        if (litros <= 0) {
            System.out.println("Volume invalido!");
            return;
        }

        String destino = "";

        while (!destino.equals("1") && !destino.equals("2")) {
            System.out.print("Destino (1-Terreiro / 2-Secador): ");
            destino = sc.nextLine();

            if (!destino.equals("1") && !destino.equals("2")) {
                System.out.println("Opcao invalida!");
            }
        }

        String destinoStr;

        if (destino.equals("1")) {
            destinoStr = "Terreiro";
        } else {
            destinoStr = "Secador";
        }

        datas[quantidadeColheitas] = data;
        matriculas[quantidadeColheitas] = matricula;
        codigosTalhao[quantidadeColheitas] = codigoTalhao;
        placas[quantidadeColheitas] = placa;
        litrosColhidos[quantidadeColheitas] = litrosStr;
        destinos[quantidadeColheitas] = destinoStr;

        quantidadeColheitas++;

        System.out.println("\nColheita registrada com sucesso!");
    }

    public static void listarColheitas(
            String[] datas,
            String[] matriculas,
            String[] codigosTalhao,
            String[] placas,
            String[] litrosColhidos,
            String[] destinos) {

        System.out.println("\n--- Colheitas Registradas ---");

        if (quantidadeColheitas == 0) {
            System.out.println("Nenhuma colheita registrada.");
            return;
        }

        System.out.println("--------------------------------------------");

        for (int i = 0; i < quantidadeColheitas; i++) {
            System.out.println(
                    datas[i] + " | " +
                    matriculas[i] + " | " +
                    codigosTalhao[i] + " | " +
                    placas[i] + " | " +
                    litrosColhidos[i] + " | " +
                    destinos[i]);
        }
    }
}