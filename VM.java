
public class VM {

    public Word[] m;

    private Shell shell;
    public CPU cpu;
    private Dispatcher dispatcher;
    private Console console;

    public VM() {

        this.m = new Word[MySystem.MEMORY_SIZE];
        for (int i = 0; i < MySystem.MEMORY_SIZE; i++) {
            m[i] = new Word(Opcode.___, -1, -1, -1);
        }

        this.shell = new Shell(new ProcessManager(m));

        cpu = new CPU(m);

        this.dispatcher = new Dispatcher(cpu);

        this.console = new Console(cpu);

    }

    public void startThreads() {
        shell.start();
        cpu.start();
        dispatcher.start();
        console.start();
    }

}