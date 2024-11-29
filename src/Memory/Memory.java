package Memory;
import java.util.HashMap;

public class Memory {
    private HashMap<String, Integer> storedVars;

    public Memory() {
        this.storedVars = new HashMap<>();
    }

    public void storeVar(String varName, int value) {
        storedVars.put(varName, value);
    }

    public int getVar(String varName) {
        return storedVars.get(varName);
    }

    public void printMemory() {
        System.out.println(storedVars);
    }
}
