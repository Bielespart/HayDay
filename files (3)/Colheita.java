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

        System.out.print("Data (dd/mm/yyyy): ");
        String data = sc.nextLine().trim();

        if (data.equals("")) {
            System.out.println("Data nao pode ficar vazia.");
            return;
        }

        if (!isDataValida(data)) {
            System.out.println("Data invalida! Use o formato dd/mm/yyyy. Exemplo: 14/05/2026");
            return;
        }

        listarOpcoesFuncionarios();
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

        listarOpcoesTalhoes();
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

        listarOpcoesTratores();
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
            int viagens = calcularViagens(litros, capacidade);

            System.out.println("Volume maior que a capacidade da carreta.");
            System.out.println("Para colher todo esse campo, serao necessarias " + viagens + " viagens.");
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

        imprimirCabecalho();

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
                imprimirLinha(i, datas, matriculas, codigosTalhao, placas, litrosColhidos, destinos);
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

        imprimirCabecalho();

        for (int i = 0; i < quantidadeColheitas; i++) {
            imprimirLinha(i, datas, matriculas, codigosTalhao, placas, litrosColhidos, destinos);
        }
    }

    private static void imprimirCabecalho() {
        System.out.printf(
                "%-4s %-10s %-12s %-10s %-12s %-12s %-10s%n",
                "N.",
                "Data",
                "Matricula",
                "Talhao",
                "Placa",
                "Litros",
                "Destino");
        System.out.println("------------------------------------------------------------------------");
    }

    private static void listarOpcoesFuncionarios() {
        System.out.println("\nFuncionarios cadastrados:");
        System.out.printf("%-12s %-25s %-10s%n", "Matricula", "Nome", "Tipo");
        System.out.println("--------------------------------------------------");

        for (int i = 0; i < Cadastro.totalFuncionarios; i++) {
            System.out.printf(
                    "%-12s %-25s %-10s%n",
                    Cadastro.matriculas[i],
                    Cadastro.nomesFuncionarios[i],
                    Cadastro.tiposFuncionarios[i]);
        }

        System.out.println();
    }

    private static void listarOpcoesTalhoes() {
        System.out.println("\nTalhoes cadastrados:");
        System.out.printf("%-10s %-25s %-15s %-12s%n", "Codigo", "Nome", "Variedade", "Estimativa");
        System.out.println("----------------------------------------------------------------");

        for (int i = 0; i < Cadastro.totalTalhoes; i++) {
            System.out.printf(
                    "%-10s %-25s %-15s %-12s%n",
                    Cadastro.codigosTalhoes[i],
                    Cadastro.nomesTalhoes[i],
                    Cadastro.variedadesCafe[i],
                    Cadastro.estimativas[i] + " L");
        }

        System.out.println();
    }

    private static void listarOpcoesTratores() {
        System.out.println("\nTratores cadastrados:");
        System.out.printf("%-12s %-15s%n", "Placa", "Capacidade");
        System.out.println("------------------------------");

        for (int i = 0; i < Cadastro.totalTratores; i++) {
            System.out.printf(
                    "%-12s %-15s%n",
                    Cadastro.placas[i],
                    Cadastro.capacidades[i] + " L");
        }

        System.out.println();
    }

    private static void imprimirLinha(
            int indice,
            String[] datas,
            String[] matriculas,
            String[] codigosTalhao,
            String[] placas,
            String[] litrosColhidos,
            String[] destinos) {

        System.out.printf(
                "%-4d %-10s %-12s %-10s %-12s %-12s %-10s%n",
                indice + 1,
                datas[indice],
                matriculas[indice],
                codigosTalhao[indice],
                placas[indice],
                litrosColhidos[indice] + " L",
                destinos[indice]);
    }

    private static int calcularViagens(int litros, int capacidade) {
        int viagens = litros / capacidade;

        if (litros % capacidade != 0) {
            viagens++;
        }

        return viagens;
    }

    private static boolean isDataValida(String data) {

        if (data.length() != 10) {
            return false;
        }

        if (data.charAt(2) != '/' || data.charAt(5) != '/') {
            return false;
        }

        String diaStr = data.substring(0, 2);
        String mesStr = data.substring(3, 5);
        String anoStr = data.substring(6, 10);

        if (!isNumerico(diaStr) || !isNumerico(mesStr) || !isNumerico(anoStr)) {
            return false;
        }

        int dia = Integer.parseInt(diaStr);
        int mes = Integer.parseInt(mesStr);

        return dia >= 1 && dia <= 31 && mes >= 1 && mes <= 12;
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

    private static boolean isNumerico(String valor) {

        if (valor.length() == 0) {
            return false;
        }

        for (int i = 0; i < valor.length(); i++) {
            char c = valor.charAt(i);

            if (c < '0' || c > '9') {
                return false;
            }
        }

        return true;
    }
}
