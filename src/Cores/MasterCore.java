package Cores;

import Process_Related.ProcessControlBlock;

import java.util.Queue;

import Parse.Instruction;
import Process_Related.Process;
import Queue.ReadyQueue;

public class MasterCore {
    private ReadyQueue readyQueue;
    private Queue<SlaveCore> slaveCores;

    public MasterCore(ReadyQueue readyQueue) {
        this.readyQueue = readyQueue;
        this.slaveCores.add(new SlaveCore());
        this.slaveCores.add(new SlaveCore());
    }

    public ReadyQueue getReadyQueue(){
        return readyQueue;
    }

    public void scheduleTask(){
        for(SlaveCore core: slaveCores){

            if(!core.isRunning() && !readyQueue.isEmpty()){

                Process currentRunningProcess = readyQueue.peekProcess();
                readyQueue.removeProcess();
                core.setCurrProcess(currentRunningProcess);
                currentRunningProcess.getPcb().setState(ProcessControlBlock.ProcessState.RUNNING);
                int burst = 0;

                try {
                    while (burst < 2 && !currentRunningProcess.isComplete()) {

                        Instruction currentInstruction = currentRunningProcess.getCurrentInstruction();

                        if (currentInstruction == null) {
                            throw new NullPointerException("Instruction is null");
                        }

                        core.executeTask(currentInstruction);
                        currentRunningProcess.getPcb().updateProgramCounter();
                        burst++;
                    }
                } catch (NullPointerException e) {
                    System.err.println("Error: " + e.getMessage());
                }

                if(!currentRunningProcess.isComplete()){
                    readyQueue.addProcess(currentRunningProcess);
                    currentRunningProcess.getPcb().setState(ProcessControlBlock.ProcessState.READY);
                } else{
                    currentRunningProcess.getPcb().setState(ProcessControlBlock.ProcessState.TERMINATED);
                    currentRunningProcess.setComplete(true);
                }
            }
        }
    }
}
