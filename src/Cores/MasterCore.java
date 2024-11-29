package Cores;

import Process_Related.ProcessControlBlock;

import java.util.LinkedList;
import java.util.Queue;

import Parse.Instruction;
import Process_Related.Process;
import Queue.ReadyQueue;

public class MasterCore {
    private ReadyQueue readyQueue;
    private Queue<SlaveCore> slaveCores;

    public MasterCore(ReadyQueue readyQueue) {
        this.readyQueue = readyQueue;
        this.slaveCores = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            SlaveCore slaveCore = new SlaveCore(readyQueue);
            slaveCores.add(slaveCore);
            slaveCore.start(); 
        }
    }

    public ReadyQueue getReadyQueue(){
        return readyQueue;
    }

    public void scheduleTask() {
        while (true) {
            for (SlaveCore core : slaveCores) {
                if (!core.isRunning() && !readyQueue.isEmpty()) {
                    Process currentRunningProcess = readyQueue.peekProcess();
                    readyQueue.removeProcess();
                    core.setCurrProcess(currentRunningProcess);
                    currentRunningProcess.getPcb().setState(ProcessControlBlock.ProcessState.RUNNING);
                    core.setStatus(true); 
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
