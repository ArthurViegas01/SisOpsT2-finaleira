import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class CPU extends Thread {

	public Semaphore SEMA_CPU = new Semaphore(0);

	private int pc;
	private Word ir;
	private int[] reg;

	public Word[] m;

	private int currentProcessId;
	private LinkedList<Integer> pageTable;

	private InterruptTypes interruptFlag;
	private InterruptHandler interruptHandler;

	public CPU(Word[] m) {
		super("CPU");
		this.pc = 0;
		this.ir = null;
		this.reg = new int[9];
		this.m = m;
		this.currentProcessId = -1;
		this.pageTable = null;
		this.interruptFlag = InterruptTypes.NO_INTERRUPT;
		this.interruptHandler = new InterruptHandler(this);
	}

	public int getPC() {
		return pc;
	}

	public Word getIr() {
		return ir;
	}

	public int[] getReg() {
		return reg;
	}

	public int getCurrentProcessId() {
		return currentProcessId;
	}

	public LinkedList<Integer> getPageTable() {
		return pageTable;
	}

	public InterruptTypes getInterruptFlag() {
		return interruptFlag;
	}

	public InterruptHandler getInterruptHandler() {
		return interruptHandler;
	}

	public void setInterruptFlag(InterruptTypes interruptFlag) {
		this.interruptFlag = interruptFlag;
	}

	public void setContext(int pc) {
		this.pc = pc;
	}

	public void showState() {
		System.out.println("       " + pc);
		System.out.print("           ");
		for (int i = 0; i < 9; i++) {
			System.out.print("r" + i);
			System.out.print(": " + reg[i] + "     ");
		}
		System.out.println("");
		System.out.print("           ");
		System.out.println(Auxiliary.dump(ir));
	}

	public void loadPCB(PCB pcb) {
		this.currentProcessId = pcb.getId();
		this.pc = pcb.getPc();
		this.reg = pcb.getReg().clone();
		this.pageTable = new LinkedList<Integer>(pcb.getPageTable());
	}

	public PCB unloadPCB() {
		return new PCB(currentProcessId, pc, reg.clone(), new LinkedList<Integer>(pageTable));
	}

	@Override
	public void run() {
		while (true) {
			try {
				SEMA_CPU.acquire();

				int instructionsCounter = 0;

				while (true) {

					instructionsCounter++;

					ir = m[MemoryManager.translate(pc, pageTable)];
					int physicalAddress;

					showState();

					switch (ir.opc) {

						case LDI:
							if (InterruptChecker.isInvalidRegister(ir.r1, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else {
								reg[ir.r1] = ir.p;
							}
							pc++;
							break;

						case LDD:
							physicalAddress = MemoryManager.translate(ir.p, pageTable);
							if (InterruptChecker.isInvalidRegister(ir.r1, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (InterruptChecker.isInvalidAddress(physicalAddress, pageTable)) {
								interruptFlag = InterruptTypes.INVALID_ADDRESS;
							} else {
								reg[ir.r1] = m[physicalAddress].p;
							}
							pc++;
							break;

						case LDX:
							physicalAddress = MemoryManager.translate(reg[ir.r2], pageTable);
							if (InterruptChecker.isInvalidRegister(ir.r1, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (InterruptChecker.isInvalidRegister(ir.r2, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (InterruptChecker.isInvalidAddress(physicalAddress, pageTable)) {
								interruptFlag = InterruptTypes.INVALID_ADDRESS;
							} else {
								reg[ir.r1] = m[physicalAddress].p;
							}
							pc++;
							break;

						case STD:
							physicalAddress = MemoryManager.translate(ir.p, pageTable);
							if (InterruptChecker.isInvalidAddress(physicalAddress, pageTable)) {
								interruptFlag = InterruptTypes.INVALID_ADDRESS;
							} else if (InterruptChecker.isInvalidRegister(ir.r1, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else {
								m[physicalAddress].opc = Opcode.DATA;
								m[physicalAddress].p = reg[ir.r1];
							}
							pc++;
							break;

						case ADD:
							if (InterruptChecker.isInvalidRegister(ir.r1, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (InterruptChecker.isInvalidRegister(ir.r2, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (InterruptChecker.causesOverflow(reg[ir.r1], reg[ir.r2], InterruptChecker.SUM)) {
								interruptFlag = InterruptTypes.OVERFLOW;
							} else {
								reg[ir.r1] = reg[ir.r1] + reg[ir.r2];
							}
							pc++;
							break;

						case MULT:
							if (InterruptChecker.isInvalidRegister(ir.r1, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (InterruptChecker.isInvalidRegister(ir.r2, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (InterruptChecker.causesOverflow(reg[ir.r1], reg[ir.r2],
									InterruptChecker.MULTIPLICATION)) {
								interruptFlag = InterruptTypes.OVERFLOW;
							} else {
								reg[ir.r1] = reg[ir.r1] * reg[ir.r2];
							}
							pc++;
							break;

						case ADDI:
							if (InterruptChecker.isInvalidRegister(ir.r1, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (InterruptChecker.causesOverflow(reg[ir.r1], ir.p, InterruptChecker.SUM)) {
								interruptFlag = InterruptTypes.OVERFLOW;
							} else {
								reg[ir.r1] = reg[ir.r1] + ir.p;
							}
							pc++;
							break;

						case STX:
							physicalAddress = MemoryManager.translate(reg[ir.r1], pageTable);
							if (InterruptChecker.isInvalidRegister(ir.r1, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (InterruptChecker.isInvalidAddress(physicalAddress, pageTable)) {
								interruptFlag = InterruptTypes.INVALID_ADDRESS;
							} else if (InterruptChecker.isInvalidRegister(ir.r2, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else {
								m[physicalAddress].opc = Opcode.DATA;
								m[physicalAddress].p = reg[ir.r2];
							}
							pc++;
							break;

						case SUB:
							if (InterruptChecker.isInvalidRegister(ir.r1, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (InterruptChecker.isInvalidRegister(ir.r2, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (InterruptChecker.causesOverflow(reg[ir.r1], reg[ir.r2],
									InterruptChecker.SUBTRACTION)) {
								interruptFlag = InterruptTypes.OVERFLOW;
							} else {
								reg[ir.r1] = reg[ir.r1] - reg[ir.r2];
							}
							pc++;
							break;

						case SUBI:
							if (InterruptChecker.isInvalidRegister(ir.r1, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (InterruptChecker.causesOverflow(reg[ir.r1], ir.p,
									InterruptChecker.SUBTRACTION)) {
								interruptFlag = InterruptTypes.OVERFLOW;
							} else {
								reg[ir.r1] = reg[ir.r1] - ir.p;
							}
							pc++;
							break;

						case JMP:
							pc = ir.p;
							break;
						case JMPI:
							if (InterruptChecker.isInvalidRegister(ir.r1, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else {
								pc = reg[ir.r1];
							}
							break;

						case JMPIG:
							if (InterruptChecker.isInvalidRegister(ir.r1, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (InterruptChecker.isInvalidRegister(ir.r2, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (reg[ir.r2] > 0) {
								pc = reg[ir.r1];
							} else {
								pc++;
							}
							break;

						case JMPIL:
							if (InterruptChecker.isInvalidRegister(ir.r1, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (InterruptChecker.isInvalidRegister(ir.r2, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (reg[ir.r2] < 0) {
								pc = reg[ir.r1];
							} else {
								pc++;
							}
							break;

						case JMPIE:
							if (InterruptChecker.isInvalidRegister(ir.r1, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (InterruptChecker.isInvalidRegister(ir.r2, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (reg[ir.r2] == 0) {
								pc = reg[ir.r1];
							} else {
								pc++;
							}
							break;

						case JMPIM:
							physicalAddress = MemoryManager.translate(ir.p, pageTable);
							if (InterruptChecker.isInvalidAddress(physicalAddress, pageTable)) {
								interruptFlag = InterruptTypes.INVALID_ADDRESS;
							} else {
								pc = m[physicalAddress].p;
							}
							break;

						case JMPILM:
							physicalAddress = MemoryManager.translate(ir.p, pageTable);
							if (InterruptChecker.isInvalidRegister(ir.r2, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (InterruptChecker.isInvalidAddress(physicalAddress, pageTable)) {
								interruptFlag = InterruptTypes.INVALID_ADDRESS;
							} else if (reg[ir.r2] < 0) {
								pc = m[physicalAddress].p;
							} else {
								pc++;
							}
							break;

						case JMPIGM:
							physicalAddress = MemoryManager.translate(ir.p, pageTable);
							if (InterruptChecker.isInvalidRegister(ir.r2, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (InterruptChecker.isInvalidAddress(physicalAddress, pageTable)) {
								interruptFlag = InterruptTypes.INVALID_ADDRESS;
							} else if (reg[ir.r2] > 0) {
								pc = m[physicalAddress].p;
							} else {
								pc++;
							}
							break;

						case JMPIEM:
							physicalAddress = MemoryManager.translate(ir.p, pageTable);
							if (InterruptChecker.isInvalidRegister(ir.r2, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (InterruptChecker.isInvalidAddress(physicalAddress, pageTable)) {
								interruptFlag = InterruptTypes.INVALID_ADDRESS;
							} else if (reg[ir.r2] == 0) {
								pc = m[physicalAddress].p;
							} else {
								pc++;
							}
							break;

						case SWAP:
							if (InterruptChecker.isInvalidRegister(ir.r1, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else if (InterruptChecker.isInvalidRegister(ir.r2, reg.length)) {
								interruptFlag = InterruptTypes.INVALID_REGISTER;
							} else {
								int t = reg[ir.r1];
								reg[ir.r1] = reg[ir.r2];
								reg[ir.r2] = t;
							}
							pc++;
							break;

						case TRAP:
							physicalAddress = MemoryManager.translate(reg[8], pageTable);
							if (InterruptChecker.isInvalidAddress(physicalAddress, pageTable)) {
								interruptFlag = InterruptTypes.INVALID_ADDRESS;
							} else {
								interruptFlag = InterruptTypes.TRAP_INTERRUPT;
							}
							pc++;
							break;

						case STOP:
							interruptFlag = InterruptTypes.END_OF_PROGRAM;
							break;

						case DATA:
						case ___:
							break;

						default:
							interruptFlag = InterruptTypes.INVALID_INSTRUCTION;
							break;
					}

					if (interruptFlag == InterruptTypes.NO_INTERRUPT
							&& instructionsCounter == MySystem.MAX_CPU_CYCLES) {
						interruptFlag = InterruptTypes.CLOCK_INTERRUPT;
						break;
					}

					if (interruptFlag == InterruptTypes.NO_INTERRUPT) {
						if (Console.FINISHED_IO_PROCESS_IDS.size() > 0) {
							interruptFlag = InterruptTypes.IO_FINISHED;
							break;
						}
						continue;
					}

					break;
				}
				interruptHandler.handle();
			} catch (InterruptedException error) {
				error.printStackTrace();
			}
		}
	}
}