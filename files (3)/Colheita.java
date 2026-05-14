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

        if (quantidadeColheitas >= datas.length) {
            System.out.println("Limite de colheitas atingido.");
            return;
        }

        if (qtdFuncionarios == 0 || qtdTalhoes == 0 || qtdFrota == 0) {
            System.out.println("Cadastre pelo menos um funcionario, um talhao e um trator antes da colheita.");
            return;
        }

        System.out.print("Data (dd/mm/aaaa): ");
        String data = sc.nextLine().trim();

        if (data.equals("")) {
            System.out.println("Data nao pode ficar vazia.");
            return;
        }

        System.out.print("Matricula do funcionario: ");
        String matricula = sc.nextLine().trim();

        if (matricula.equals("")) {
            System.out.println("Matricula nao pode ficar vazia.");
            return;
        }

        int posFuncionario = Cadastro.buscarPorMatricula(matricula);

        if (posFuncionario == -1) {
            System.out.println("Funcionario nao encontrado!");
            return;
        }

        System.out.println("Funcionario: " + funcionarios[posFuncionario]);

        System.out.print("Codigo do talhao: ");
        String codigoTalhao = sc.nextLine().trim().toUpperCase();

        if (codigoTalhao.equals("")) {
            System.out.println("Codigo do talhao nao pode ficar vazio.");
            return;
        }

        int posTalhao = Cadastro.buscarTalhao(codigoTalhao);

        if (posTalhao == -1) {
            System.out.println("Talhao nao encontrado!");
            return;
        }

        System.out.println("Talhao: " + talhoes[posTalhao]);

        System.out.print("Placa do trator: ");
        String placa = sc.nextLine().trim().toUpperCase();

        if (placa.equals("")) {
            System.out.println("Placa do trator nao pode ficar vazia.");
            return;
        }

        int posTrator = Cadastro.buscarTrator(placa);

        if (posTrator == -1) {
            System.out.println("Trator nao encontrado!");
            return;
        }

        int capacidade;

        try {
            capacidade = Integer.parseInt(Cadastro.capacidades[posTrator]);
        } catch (NumberFormatException e) {
            System.out.println("Capacidade do trator cadastrada de forma invalida.");
            return;
        }

        System.out.println("Trator encontrado: " + frota[posTrator]);
        System.out.println("Capacidade da carreta: " + capacidade + " litros");

        System.out.print("Volume colhido (litros): ");
        String litrosStr = sc.nextLine().trim();

        if (!isNumericoPositivo(litrosStr)) {
            System.out.println("Volume invalido!");
            return;
        }

        int litros;

        try {
            litros = Integer.parseInt(litrosStr);
        } catch (NumberFormatException e) {
            System.out.println("Volume invalido!");
            return;
        }

        if (litros > capacidade) {
            System.out.println("Volume maior que a capacidade da carreta!");
            System.out.println("Capacidade maxima permitida: " + capacidade + " litros");
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

    public static void pesquisarColheita(
            String[] datas,
            String[] matriculas,
            String[] codigosTalhao,
            String[] placas,
            String[] litrosColhidos,
            String[] destinos,
            Scanner sc) {

        System.out.println("\n--- Pesquisar Colheita ---");

        if (quantidadeColheitas == 0) {
            System.out.println("Nenhuma colheita registrada.");
            return;
        }

        System.out.println("1 - Pesquisar por matricula");
        System.out.println("2 - Pesquisar por codigo do talhao");
        System.out.println("3 - Pesquisar por placa do trator");
        System.out.print("Opcao: ");
        String opcao = sc.nextLine().trim();

        if (!opcao.equals("1") && !opcao.equals("2") && !opcao.equals("3")) {
            System.out.println("Opcao de pesquisa invalida.");
            return;
        }

        System.out.print("Digite o valor da pesquisa: ");
        String termo = sc.nextLine().trim();

        if (termo.equals("")) {
            System.out.println("Valor da pesquisa nao pode ficar vazio.");
            return;
        }

        if (opcao.equals("2") || opcao.equals("3")) {
            termo = termo.toUpperCase();
        }

        boolean encontrou = false;

        System.out.println("\nData | Matricula | Talhao | Placa | Litros | Destino");
        System.out.println("-----------------------------------------------------");

        for (int i = 0; i < quantidadeColheitas; i++) {
            boolean combina = false;

            if (opcao.equals("1")) {
                combina = matriculas[i].equalsIgnoreCase(termo);
            } else if (opcao.equals("2")) {
                combina = codigosTalhao[i].equalsIgnoreCase(termo);
            } else if (opcao.equals("3")) {
                combina = placas[i].equalsIgnoreCase(termo);
            }

            if (combina) {
                System.out.println(
                        datas[i] + " | " +
                        matriculas[i] + " | " +
                        codigosTalhao[i] + " | " +
                        placas[i] + " | " +
                        litrosColhidos[i] + " | " +
                        destinos[i]);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhuma colheita encontrada para essa pesquisa.");
        }
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
            return Integer.parseInt(valor) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
