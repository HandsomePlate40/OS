package Cores;

import Memory.Memory;
import Memory.MemoryBlock;
import Parse.Instruction;
import Process_Related.Process;
import Process_Related.ProcessControlBlock;
import Queue.ReadyQueue;

import java.util.concurrent.locks.ReentrantLock;

public class SlaveCore extends Thread {
    private Process currProcess;
    private boolean status;
    private final Memory sharedMemory;
    private final ReadyQueue readyQueue;
    private final ReentrantLock lock;

    public SlaveCore(ReadyQueue readyQueue, Memory memory) {
        this.status = false;
        this.readyQueue = readyQueue;
        this.sharedMemory = memory;
        this.lock = new ReentrantLock();
    }

    @Override
    public void run() {
        while (true) {
            if (currProcess != null && currProcess.getPcb().getState() != ProcessControlBlock.ProcessState.TERMINATED) {
                status = true;
                int burst = 0;
                // round-robin
                while (burst < 2 && currProcess.getPcb().getState() != ProcessControlBlock.ProcessState.TERMINATED) {
                    Instruction currentInstruction = sharedMemory.getMemoryBlock(currProcess.getPid()).getCurrentInstruction(currProcess);
                    if (currentInstruction != null) {
                        executeTask(currentInstruction);
                        currProcess.getPcb().updateProgramCounter();
                    } else {
                        currProcess.getPcb().setState(ProcessControlBlock.ProcessState.TERMINATED);
                        break;
                    }
                    burst++;
                }

                lock.lock();
                try {
                    sharedMemory.getMemoryBlock(currProcess.getPid()).printMemory(currProcess.getPid());
                } finally {
                    lock.unlock();
                }

                synchronized (System.out) {
                    if (currProcess.getPcb().getState() == ProcessControlBlock.ProcessState.TERMINATED ||
                            sharedMemory.getMemoryBlock(currProcess.getPid()).getCurrentInstruction(currProcess) == null) {

                        sharedMemory.deallocateMemory(currProcess.getPid());
                        lock.lock();
                        try {
                            System.out.println("**Process " + currProcess.getPid() + " completed by " + this.getName() + "**");
                        } finally {
                            lock.unlock();
                        }
                    } else {
                        currProcess.getPcb().setState(ProcessControlBlock.ProcessState.READY);
                        readyQueue.addProcess(currProcess);
                        lock.lock();
                        try {
                            System.out.println("**Process " + currProcess.getPid() + " added back to ReadyQueue by " + this.getName() + "**");
                        } finally {
                            lock.unlock();
                        }
                    }
                }
                System.out.println();
                currProcess = null;
                status = false;
            } else {
                synchronized (this) {
                    try {
                        wait(5);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println(this.getName() + " interrupted and stopping.");
                        break;
                    }
                }
            }
        }
    }

    public void executeTask(Instruction currentInstruction) {
        MemoryBlock memoryBlock = sharedMemory.getMemoryBlock(currProcess.getPid());
        memoryBlock.memoryLock();
        try {
            switch (currentInstruction.getOperation()) {
                case "assign":
                    if (currentInstruction.getOperand1().equals("input")) {
                        int val = Integer.parseInt(currentInstruction.getOperand2());
                        memoryBlock.storeVar(currentInstruction.getVariable(), val);
                    } else {
                        int result;
                        try {
                            switch (currentInstruction.getOperation2()) {
                                case "add":
                                    result = memoryBlock.getVar(currentInstruction.getOperand1()) + memoryBlock.getVar(currentInstruction.getOperand2());
                                    memoryBlock.storeVar(currentInstruction.getVariable(), result);
                                    break;
                                case "subtract":
                                    result = memoryBlock.getVar(currentInstruction.getOperand1()) - memoryBlock.getVar(currentInstruction.getOperand2());
                                    memoryBlock.storeVar(currentInstruction.getVariable(), result);
                                    break;
                                case "multiply":
                                    result = memoryBlock.getVar(currentInstruction.getOperand1()) * memoryBlock.getVar(currentInstruction.getOperand2());
                                    memoryBlock.storeVar(currentInstruction.getVariable(), result);
                                    break;
                                case "divide":
                                    result = memoryBlock.getVar(currentInstruction.getOperand1()) / memoryBlock.getVar(currentInstruction.getOperand2());
                                    memoryBlock.storeVar(currentInstruction.getVariable(), result);
                                    break;
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage() + " in Process " + currProcess.getPid());
                        }
                    }
                    break;
                case "print":
                    synchronized (System.out) {
                        if (!memoryBlock.containsKey(currentInstruction.getVariable())) {
                            System.out.println("**************** Variable does not exist in memory" + " in Process " + currProcess.getPid() + " ****************");
                        } else {
                            System.out.println("Variable: " + currentInstruction.getVariable() + " = " + memoryBlock.getVar(currentInstruction.getVariable()) + " from Process " + currProcess.getPid());
                            System.out.println();
                        }
                    }
                    break;
            }
        } finally {
            memoryBlock.memoryUnlock();
        }
    }

    public void setCurrProcess(Process currProcess) {
        this.currProcess = currProcess;
    }

    public boolean isRunning() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}