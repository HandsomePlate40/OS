// src/Cores/MasterCore.java
package Cores;

import Memory.Memory;
import Process_Related.ProcessControlBlock;

import java.util.LinkedList;
import java.util.Queue;

import Parse.Instruction;
import Process_Related.Process;
import Queue.ReadyQueue;

public class MasterCore {
    private ReadyQueue readyQueue;
    private final Queue<SlaveCore> slaveCores;
    private Memory memory;

    public MasterCore(ReadyQueue readyQueue, Memory memory) {
        this.readyQueue = readyQueue;
        this.slaveCores = new LinkedList<>();
        this.memory = memory;
        for (int i = 0; i < 2; i++) {
            SlaveCore slaveCore = new SlaveCore(readyQueue, memory);
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
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}