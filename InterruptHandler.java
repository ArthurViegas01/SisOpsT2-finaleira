public class InterruptHandler {

    private CPU cpu;

    public InterruptHandler(CPU cpu) {
        this.cpu = cpu;
    }

    public void handle() {

        InterruptTypes interruptFlag = cpu.getInterruptFlag();

        System.out.println("\nINTERRUPCAO ACIONADA - MOTIVO:");

        switch (interruptFlag) {

            case INVALID_ADDRESS:
                System.out.println(
                        "Endereco invalido: programa do usuario acessando endereço fora de limites permitidos.");
                endProcess();
                break;

            case INVALID_INSTRUCTION:
                System.out.println("Instrucao invalida: a instrucao carregada é invalida.");
                endProcess();
                break;

            case OVERFLOW:
                System.out.println("Overflow de numero inteiro.");
                endProcess();
                break;

            case INVALID_REGISTER:
                System.out.println("Registrador(es) invalido(s) passados como parametro.");
                endProcess();
                break;

            case TRAP_INTERRUPT:
                System.out.println("Chamada de sistema [TRAP].");
                packageForConsole();
                break;

            case CLOCK_INTERRUPT:
                System.out.println("Ciclo maximo de CPU atingido (" + MySystem.MAX_CPU_CYCLES + ").");
                saveProcess();
                break;

            case IO_FINISHED:
                System.out.println("Operação IO de processo concluída.");
                ioFinishedRoutine();
                break;

            case END_OF_PROGRAM:
                System.out.println("Final de programa.\n");
                endProcess();
                break;

            default:
                return;
        }
    }

    private void endProcess() {
        if (ProcessManager.RUNNING != null) {
            ProcessManager.destroyProcess(cpu.getCurrentProcessId(), cpu.getPageTable());
            ProcessManager.RUNNING = null;
        }
        cpu.setInterruptFlag(InterruptTypes.NO_INTERRUPT);
        if (Dispatcher.SEMA_DISPATCHER.availablePermits() == 0) {
            Dispatcher.SEMA_DISPATCHER.release();
        }
    }

    private void saveProcess() {
        ProcessManager.RUNNING = null;
        PCB process = cpu.unloadPCB();
        ProcessManager.READY_LIST.add(process);
        cpu.setInterruptFlag(InterruptTypes.NO_INTERRUPT);
        if (Dispatcher.SEMA_DISPATCHER.availablePermits() == 0 && ProcessManager.RUNNING == null) {
            Dispatcher.SEMA_DISPATCHER.release();
        }
    }

    private void packageForConsole() {
        ProcessManager.RUNNING = null;
        PCB process = cpu.unloadPCB();
        IORequest ioRequest;
        if (cpu.getReg()[7] == 1) {
            ioRequest = new IORequest(process, IORequest.OperationTypes.READ);
        } else {
            ioRequest = new IORequest(process, IORequest.OperationTypes.WRITE);
        }
        ProcessManager.BLOCKED_LIST.add(process);
        Console.IO_REQUESTS.add(ioRequest);
        cpu.setInterruptFlag(InterruptTypes.NO_INTERRUPT);
        Console.SEMA_CONSOLE.release();
        if (Dispatcher.SEMA_DISPATCHER.availablePermits() == 0 && ProcessManager.RUNNING == null) {
            Dispatcher.SEMA_DISPATCHER.release();
        }
    }

    private void ioFinishedRoutine() {
        ProcessManager.RUNNING = null;
        int finishedIOProcessId = Console.getFirstFinishedIOProcessId();
        PCB finishedIOProcess = ProcessManager.findBlockedProcessById(finishedIOProcessId);
        PCB interruptedProcess = cpu.unloadPCB();
        ProcessManager.READY_LIST.add(interruptedProcess);
        cpu.setInterruptFlag(InterruptTypes.NO_INTERRUPT);
        ProcessManager.READY_LIST.addFirst(finishedIOProcess);
        int physicalAddress = MemoryManager.translate(finishedIOProcess.getReg()[8], finishedIOProcess.getPageTable());
        if (finishedIOProcess.getReg()[7] == 1) {
            cpu.m[physicalAddress].opc = Opcode.DATA;
            cpu.m[physicalAddress].p = finishedIOProcess.getIOValue();
        } else {
            System.out.println(
                    "\n[Output from process with ID = " + finishedIOProcess.getId() + " - "
                            + ProcessManager.getProgramNameByProcessId(finishedIOProcess.getId()) + "] "
                            + finishedIOProcess.getIOValue()
                            + "\n");
        }
        // Libera escalonador.
        if (Dispatcher.SEMA_DISPATCHER.availablePermits() == 0 && ProcessManager.RUNNING == null) {
            Dispatcher.SEMA_DISPATCHER.release();
        }
    }

    public void noOtherProcessRunningRoutine() {
        int finishedIOProcessId = Console.getFirstFinishedIOProcessId();
        PCB finishedIOProcess = ProcessManager.findBlockedProcessById(finishedIOProcessId);
        int physicalAddress = MemoryManager.translate(finishedIOProcess.getReg()[8], finishedIOProcess.getPageTable());
        if (finishedIOProcess.getReg()[7] == 1) {
            cpu.m[physicalAddress].opc = Opcode.DATA;
            cpu.m[physicalAddress].p = finishedIOProcess.getIOValue();
        } else {
            System.out.println(
                    "\n[Output from process with ID = " + finishedIOProcess.getId() + " - "
                            + ProcessManager.getProgramNameByProcessId(finishedIOProcess.getId()) + "] "
                            + finishedIOProcess.getIOValue()
                            + "\n");
        }
        ProcessManager.READY_LIST.addFirst(finishedIOProcess);
        if (Dispatcher.SEMA_DISPATCHER.availablePermits() == 0 && ProcessManager.RUNNING == null) {
            Dispatcher.SEMA_DISPATCHER.release();
        }
    }
}
