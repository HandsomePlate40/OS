package Memory;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class MemoryBlock {
    private final HashMap<String, Integer> storedVars;
    private int base;
    private final int limit;
    private final ReentrantLock lock;

    public MemoryBlock(int base, int limit) {
        this.storedVars = new HashMap<>();
        this.base = base;
        this.limit = limit;
        this.lock = new ReentrantLock();
    }

    private void updateCurrentlyUtilizedMemory(){
        base++;
    }

    public void storeVar(String varName, int value) {
        lock.lock();
        try {
            if (base >= limit) {
                System.out.println("**************** Memory limit reached ****************");
                return;
            }
            storedVars.put(varName, value);
            updateCurrentlyUtilizedMemory();
        } finally {
            lock.unlock();
        }
    }

    public int getVar(String varName) {
        lock.lock();
        try {
            if (storedVars.containsKey(varName)) {
                return storedVars.get(varName);
            } else {
                throw new IllegalArgumentException("*********Variable " + varName + " does not exist in memory********");
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean containsKey(String varName) {
        return storedVars.containsKey(varName);
    }

    public void memoryLock() {
        lock.lock();
    }

    public void memoryUnlock() {
        lock.unlock();
    }

    public void printMemory(int pid) {
        lock.lock();
        try {
            System.out.println("--- Memory Block for process: "+ pid + " || " + storedVars + " Number of elements : " + storedVars.size());
        } finally {
            lock.unlock();
        }
    }
}