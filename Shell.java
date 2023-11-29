import java.util.Scanner;

public class Shell extends Thread {

    private Scanner scanner;
    private ProcessManager processManager;

    public Shell(ProcessManager processManager) {
        super("Shell");
        this.scanner = new Scanner(System.in);
        this.processManager = processManager;
    }

    @Override
    public void run() {
        String menu = "===== Bem-vindo ao Menu do Sistema =====\n" +
                
                "\nSelecione uma ação:\n" +
                
                "\n1 - Iniciar Processo Fibonacci" +
                "\n2 - Iniciar Processo Fatorial" +
                "\n3 - Iniciar Processo PA (Fibonacci)" +
                "\n4 - Iniciar Processo PB (Fatorial)" +
                "\n5 - Iniciar Processo PC (Bubblesort)" +
                "\n6 - Iniciar Processo Test In" +
                "\n7 - Iniciar Processo Test Out" +
                
                "\n\n= Opções extras =\n" +
                "\nDump da memória - dump" +
                "\n\n = Múltiplos comandos =\n\n" +
                "Você pode executar vários comandos de uma vez usando o separador ';'" +
                "\n\nExemplo: Digite '1;dump;2' para criar um processo Fibonacci, realizar um despejo de memória e, em seguida, criar um processo Fatorial.";
        System.out.println(menu);
        while (true) {
            System.out.println("\n[AVISO: Esperando input do usuário]\n");
            String option = scanner.nextLine();
            System.out.println("\nRecebeu o input do usuário [OK]\n");
            String[] processes = option.toLowerCase().split(";");
            for (String process : processes) {
                switch (process) {
                    case "1":
                        processManager.createProcess(Programs.fibonacci10);
                        break;
                    case "2":
                        processManager.createProcess(Programs.fatorial);
                        break;
                    case "3":
                        processManager.createProcess(Programs.pa);
                        break;
                    case "4":
                        processManager.createProcess(Programs.pb);
                        break;
                    case "5":
                        processManager.createProcess(Programs.pc);
                        break;
                    case "6":
                        processManager.createProcess(Programs.testIn);
                        break;
                    case "7":
                        processManager.createProcess(Programs.testOut);
                        break;
                    case "dump":
                        System.out.println(Auxiliary.dump(processManager.getMemory(), 0, MySystem.MEMORY_SIZE));
                        break;
                    default:
                        System.out.println("\n[ERRO: Opção inválida de input para o Schell]\n");
                }
            }
        }
    }
}
