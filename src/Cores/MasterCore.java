package Cores;

import Memory.Memory;
import Process_Related.Process;
import Process_Related.ProcessControlBlock;
import Queue.ReadyQueue;

import java.util.LinkedList;
import java.util.Queue;

public class MasterCore {
    private final ReadyQueue readyQueue;
    private final Queue<SlaveCore> slaveCores;
    private final Memory memory;

    public MasterCore(ReadyQueue readyQueue, Memory memory) {
        this.readyQueue = readyQueue;
        this.slaveCores = new LinkedList<>();
        this.memory = memory;
        for (int i = 0; i < 2; i++) {
            SlaveCore slaveCore = new SlaveCore(readyQueue, memory);
            slaveCore.setName("SlaveCore-" + i);
            slaveCores.add(slaveCore);
            slaveCore.start();
        }
    }

    public void scheduleTask() {
        while (true) {
            for (SlaveCore core : slaveCores) {
                if (!core.isRunning() && !readyQueue.isEmpty()) {
                    Process currentRunningProcess = readyQueue.peekProcess();
                    if (currentRunningProcess != null) {
                        readyQueue.removeProcess();
                        core.setCurrProcess(currentRunningProcess);
                        currentRunningProcess.getPcb().setState(ProcessControlBlock.ProcessState.RUNNING);
                        core.setStatus(true);
                        memory.printMemory();
                    }
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("MasterCore interrupted and stopping.");
            }
        }
    }
}