package Cores;

import Process_Related.ProcessControlBlock;
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

    }
}
