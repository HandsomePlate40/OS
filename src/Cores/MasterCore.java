// src/Cores/MasterCore.java
package Cores;

import Memory.Memory;
import Process_Related.ProcessControlBlock;
import Process_Related.Process;
import Queue.ReadyQueue;

import java.util.LinkedList;
import java.util.Queue;

public class MasterCore {
    private final ReadyQueue readyQueue;
    private final Queue<SlaveCore> slaveCores;
    private volatile boolean running;

    public MasterCore(ReadyQueue readyQueue, Memory memory) {
        this.readyQueue = readyQueue;
        this.slaveCores = new LinkedList<>();
        this.running = true;
        for (int i = 0; i < 2; i++) {
            SlaveCore slaveCore = new SlaveCore(readyQueue, memory);
            slaveCore.setName("SlaveCore-" + i);
            slaveCores.add(slaveCore);
            slaveCore.start();
        }
    }

    public void scheduleTask() {
        while (running) {
            for (SlaveCore core : slaveCores) {
                if (!core.isRunning() && !readyQueue.isEmpty()) {
                    Process currentRunningProcess = readyQueue.peekProcess();
                    if (currentRunningProcess != null) {
                        readyQueue.removeProcess();
                        core.setCurrProcess(currentRunningProcess);
                        currentRunningProcess.getPcb().setState(ProcessControlBlock.ProcessState.RUNNING);
                        core.setStatus(true);
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("MasterCore interrupted and stopping.");
                running = false;
            }
        }
    }
}