
package Memory;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Memory {
    private HashMap<String, Integer> storedVars;
    private final ReentrantLock lock;

    public Memory() {
        this.storedVars = new HashMap<>();
        this.lock = new ReentrantLock();
    }


    public void storeVar(String varName, int value) {
        lock.lock();
        try {
            if (!storedVars.containsKey(varName)) {
                storedVars.put(varName, value);
            } else {
                System.out.println("Variable " + varName + " exists in memory");
            }
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
            System.out.println("Memory: " + storedVars);
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