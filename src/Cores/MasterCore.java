package Cores;
import java.util.Map;

import Process_Related.ProcessControlBlock;
import Queue.ReadyQueue;

public class MasterCore {
    private ReadyQueue readyQueue;
    private Map<Integer, ProcessControlBlock> processControlBlocks; //PID and PCB


    public MasterCore(ReadyQueue readyQueue, Map<Integer, ProcessControlBlock> processControlBlocks) {
        this.readyQueue = readyQueue;
        this.processControlBlocks = processControlBlocks;
    }

    public void scheduleTask(){

    }
}
