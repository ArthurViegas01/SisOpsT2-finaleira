public class MySystem {

	public VM vm;

	public static final int MAX_INTEGER_SIZE = 32767;
	public static final int MIN_INTEGER_SIZE = -32768;

	public static final int MEMORY_SIZE = 50;
	public static final int PAGE_SIZE = 10;

	public static final int MAX_CPU_CYCLES = 10;

	public MySystem() {
		vm = new VM();
	}

	public static void main(String args[]) {

		MySystem system = new MySystem();
		system.vm.startThreads();

	}

}