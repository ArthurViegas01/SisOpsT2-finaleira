import java.util.LinkedList;

public class InterruptChecker {

    public static boolean isInvalidAddress(int pc, LinkedList<Integer> pageTable) {
        if (pc < 0 || pc >= MySystem.MEMORY_SIZE) {
            return true;
        }
        int pageOfPc = MemoryManager.pageOfPc(pc);
        if (!pageTable.contains(pageOfPc)) {
            return true;
        }
        return false;
    }

    public static boolean isInvalidRegister(int index, int regSize) {
        if (index < 0 || index >= regSize)
            return true;
        return false;
    }

    public static final int SUM = 1;
    public static final int SUBTRACTION = 2;
    public static final int MULTIPLICATION = 3;
    public static final int SOLE_NUMBER = 4;

    public static boolean causesOverflow(int a, int b, int operation) {
        int result;
        switch (operation) {
        case SUM:
            result = a + b;
            break;
        case SUBTRACTION:
            result = a - b;
            break;
        case MULTIPLICATION:
            result = a * b;
            break;
        case SOLE_NUMBER:
            return a > MySystem.MAX_INTEGER_SIZE || a < MySystem.MIN_INTEGER_SIZE;
        default:
            return true;
        }
        return result > MySystem.MAX_INTEGER_SIZE || result < MySystem.MIN_INTEGER_SIZE;
    }
}