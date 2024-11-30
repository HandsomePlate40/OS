
package Cores;
import Memory.Memory;
import Parse.Instruction;
import Process_Related.Process;
import Process_Related.ProcessControlBlock;
import Queue.ReadyQueue;


public class SlaveCore extends Thread {
    private Process currProcess;
    private boolean status;
    private final Memory memory;
    private final ReadyQueue readyQueue;

    public SlaveCore(ReadyQueue readyQueue, Memory memory) {
        this.status = false;
        this.readyQueue = readyQueue;
        this.memory = memory;
    }

    @Override
    public void run() {
        while (true) {
            if (currProcess != null && !currProcess.isComplete()) {
                status = true;
                int burst = 0;

                while (burst < 2 && !currProcess.isComplete()) {
                    Instruction currentInstruction = currProcess.getCurrentInstruction();
                    if (currentInstruction != null) {
                        executeTask(currentInstruction);
                        currProcess.getPcb().updateProgramCounter();
                    }
                    burst++;
                }

                if (currProcess.isComplete()) {
                    currProcess.getPcb().setState(ProcessControlBlock.ProcessState.TERMINATED);
                    System.out.println("Process " + currProcess.getPid() + " completed by SlaveCore " + this.getName());
                    currProcess = null;
                    status = false;
                } else {
                    currProcess.getPcb().setState(ProcessControlBlock.ProcessState.READY);
                    readyQueue.addProcess(currProcess);
                    currProcess = null;
                    status = false;
                }
            } else {
                Thread.yield();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void executeTask(Instruction currentInstruction) {
        memory.lock();
        try {
            switch (currentInstruction.getOperation()) {
                case "assign":
                    if (currentInstruction.getOperand1().equals("input")) {
                        int val = Integer.parseInt(currentInstruction.getOperand2());
                        memory.storeVar(currentInstruction.getVariable(), val);
                    } else {
                        int result;
                        switch (currentInstruction.getOperation2()) {
                            case "add":
                                result = memory.getVar(currentInstruction.getOperand1()) + memory.getVar(currentInstruction.getOperand2());
                                memory.storeVar(currentInstruction.getVariable(), result);
                                break;
                            case "subtract":
                                result = memory.getVar(currentInstruction.getOperand1()) - memory.getVar(currentInstruction.getOperand2());
                                memory.storeVar(currentInstruction.getVariable(), result);
                                break;
                            case "multiply":
                                result = memory.getVar(currentInstruction.getOperand1()) * memory.getVar(currentInstruction.getOperand2());
                                memory.storeVar(currentInstruction.getVariable(), result);
                                break;
                            case "divide":
                                result = memory.getVar(currentInstruction.getOperand1()) / memory.getVar(currentInstruction.getOperand2());
                                memory.storeVar(currentInstruction.getVariable(), result);
                                break;
                        }
                    }
                    break;
                case "print":
                    if (!memory.containsKey(currentInstruction.getVariable())) {
                        System.out.println("Variable does not exist in memory" + " in Process " + currProcess.getPid());
                    } else {
                        System.out.println("Variable: " + currentInstruction.getVariable() + " = " + memory.getVar(currentInstruction.getVariable()) + " in Process " + currProcess.getPid());
                    }
                    break;
            }
        } finally {
            memory.unlock();
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