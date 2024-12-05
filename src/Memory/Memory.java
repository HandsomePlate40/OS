package Memory;

import Parse.Instruction;

import java.util.HashMap;
import java.util.List;

public class Memory {
    private final HashMap<Integer, MemoryBlock> memoryMap;

    public Memory() {
        this.memoryMap = new HashMap<>();
    }

    public MemoryBlock allocateMemoryForProcess(int pid, int limit, List<Instruction> instructions) {
        MemoryBlock memoryBlock = new MemoryBlock(0, limit, instructions);
        memoryMap.put(pid, memoryBlock);
        return memoryBlock;
    }

    public MemoryBlock getMemoryBlock(int pid) {
        return memoryMap.get(pid);
    }

    public void deallocateMemory(int pid) {
        memoryMap.remove(pid);
    }
}