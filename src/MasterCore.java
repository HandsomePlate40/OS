import java.util.Map;
import java.util.Queue;

public class MasterCore {
    private ReadyQueue readyQueue;
    private Map<Integer, ProcessControlBlock> processControlBlocks; //PID and PCB


    public MasterCore(ReadyQueue readyQueue, Map<Integer, ProcessControlBlock> processControlBlocks) {
        this.readyQueue = readyQueue;
        this.processControlBlocks = processControlBlocks;
    }
}
