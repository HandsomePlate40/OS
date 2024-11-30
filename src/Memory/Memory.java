package Memory;
import java.util.HashMap;

public class Memory {
    private final HashMap<Integer, MemoryBlock> memoryMap;

    public Memory() {
        this.memoryMap = new HashMap<>();
    }

    public void allocateMemoryForProcess(int pid, int limit) {

        memoryMap.put(pid, new MemoryBlock(0, limit));
    }

    public MemoryBlock getMemoryBlock(int pid) {
        return memoryMap.get(pid);
    }

    public void deallocateMemory(int pid) {
        memoryMap.remove(pid);
    }
}