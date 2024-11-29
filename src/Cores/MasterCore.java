package Cores;

import Process_Related.ProcessControlBlock;
import Process_Related.Process;
import Queue.ReadyQueue;

public class MasterCore {
    private ReadyQueue readyQueue;

    public MasterCore(ReadyQueue readyQueue) {
        this.readyQueue = readyQueue;
    }

    public ReadyQueue getReadyQueue(){
        return readyQueue;
    }

    public void scheduleTask(){

        if(!readyQueue.isEmpty()){
            Process currentRunningProcess = readyQueue.removeProcess();
        }
        else{
            System.out.println("No process in the ready queue");
        }
        
    }
}
