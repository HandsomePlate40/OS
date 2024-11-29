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
        for (SlaveCore core : slaveCores) {
            if (!core.isRunning() && !readyQueue.isEmpty()) {
                Process currentRunningProcess = readyQueue.peekProcess();
                System.out.println(currentRunningProcess.getCurrentInstruction().getOperand2());
                readyQueue.removeProcess();
                core.setCurrProcess(currentRunningProcess);
                currentRunningProcess.getPcb().setState(ProcessControlBlock.ProcessState.RUNNING);
                core.setStatus(true); 
            }
        }
    }
}
