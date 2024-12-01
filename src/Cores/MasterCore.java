package Cores;

import Memory.Memory;
import Process_Related.Process;
import Process_Related.ProcessControlBlock;
import Queue.ReadyQueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class MasterCore {
    private final ReadyQueue readyQueue;
    private final Queue<SlaveCore> slaveCores;
    private final ReentrantLock lock;
    private final Queue<Process> processActiveLog; //keep tabs on all processes
    private final Queue<Process> processStaticLog;

    public MasterCore(ReadyQueue readyQueue, Memory memory) {
        this.readyQueue = readyQueue;
        this.slaveCores = new LinkedList<>();
        this.lock = new ReentrantLock();
        this.processActiveLog = new LinkedList<>();
        this.processStaticLog = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            SlaveCore slaveCore = new SlaveCore(readyQueue, memory);
            slaveCore.setName("SlaveCore-" + i);
            slaveCores.add(slaveCore);
            slaveCore.start();
        }

        while (!readyQueue.isEmpty()) {
            Process process = readyQueue.peekProcess();
            processActiveLog.add(process);
            processStaticLog.add(process);
            readyQueue.removeProcess();
        }

        for (Process process : processActiveLog) {
            readyQueue.addProcess(process);
        }

        scheduleTask();
    }

    public void scheduleTask() {
        while (true) {
            lock.lock();
            try {
                updateProcessLog();
                exitIfDone();
                printProcessStats();
                for (SlaveCore core : slaveCores) {
                    if (!core.isRunning() && !readyQueue.isEmpty()) {
                        Process currentRunningProcess = readyQueue.peekProcess();
                        if (currentRunningProcess != null) {
                            readyQueue.removeProcess();
                            currentRunningProcess.getPcb().setState(ProcessControlBlock.ProcessState.RUNNING);
                            core.setCurrProcess(currentRunningProcess);
                            core.setStatus(true);
                        }
                    }
                }
                lock.unlock();
                synchronized (this) {
                    wait(5);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("MasterCore interrupted and stopping.");
                break;
            }
        }
    }

    public void printProcessStats() {
        lock.lock();
        if (!readyQueue.isEmpty()) {
            try {
                System.out.print(" || Current Process stats: ");
                for (Process process : processStaticLog) {
                    System.out.print("|| Process: " + process.getPid() + " State: " + process.getPcb().getState());
                }
                System.out.println(" ||");
            } finally {
                lock.unlock();
            }
        }
    }

    public void updateProcessLog() {
        lock.lock();
        try {
            if (processActiveLog.isEmpty()) {
                exitIfDone();
            }
            Queue<Process> tempQueue = new LinkedList<>();
            while (!processActiveLog.isEmpty()) {
                Process process = processActiveLog.poll();
                if (process.getPcb().getState() != ProcessControlBlock.ProcessState.TERMINATED) {
                    tempQueue.add(process);
                }
            }
            processActiveLog.addAll(tempQueue);
        } finally {
            lock.unlock();
        }
    }

    public void exitIfDone(){
        if(processActiveLog.isEmpty()){
            System.exit(0);
        }
    }
}