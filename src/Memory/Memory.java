package Memory;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Memory {
    private final HashMap<String, Integer> storedVars;
    private final ReentrantLock lock;

    public Memory() {
        this.storedVars = new HashMap<>();
        this.lock = new ReentrantLock();
    }

    public void storeVar(String varName, int value) {
        lock.lock();
        try {
            storedVars.put(varName, value);
        } finally {
            lock.unlock();
        }
    }

    public int getVar(String varName) {
        lock.lock();
        try {
            return storedVars.get(varName);
        } finally {
            lock.unlock();
        }
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    public void printMemory(){
        lock.lock();
        try {
            int count = 0;
            for(int i = 1; i <= storedVars.size(); i++){
                count++;
            }
            System.out.println("Memory: " + storedVars + " Number of elements: " + count);
        } finally {
            lock.unlock();
        }
    }

    public boolean containsKey(String varName){
        lock.lock();
        try {
            return storedVars.containsKey(varName);
        } finally {
            lock.unlock();
        }
    }
}