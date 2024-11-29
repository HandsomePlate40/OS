package Cores;
import Memory.Memory;
import Parse.Instruction;
import Process_Related.Process;
import Process_Related.ProcessControlBlock;
import Queue.ReadyQueue;

public class SlaveCore extends Thread {
    private Process currProcess;
    private boolean status;
    private Memory memory;
    private final ReadyQueue readyQueue;

    public SlaveCore(ReadyQueue readyQueue) {
        this.memory = new Memory();
        this.status = false;
        this.readyQueue = readyQueue;
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
                        System.out.println("Quantum expired for Process " + currProcess.getPid() + ". Re-enqueuing.");
                        currProcess = null;
                        status = false;
                    }
                } else {
                    // No current process, yield and sleep to reduce CPU usage
                    Thread.yield();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void executeTask(Instruction currentInstruction) {

        switch(currentInstruction.getOperation()){

            case "assign": 
            if(currentInstruction.getOperand1().equals("input")){
                int val = Integer.parseInt(currentInstruction.getOperand2());
                memory.storeVar(currentInstruction.getVariable(), val);break;
            }else{
                int result = 0;
                switch(currentInstruction.getOperation2()){
                    case "add":
                        result = memory.getVar(currentInstruction.getOperand1()) + memory.getVar(currentInstruction.getOperand2());
                        memory.storeVar(currentInstruction.getVariable(), result);break;
                    case "subtract":
                        result = memory.getVar(currentInstruction.getOperand1()) - memory.getVar(currentInstruction.getOperand2());
                        memory.storeVar(currentInstruction.getVariable(), result);break;
                    case "multiply":
                        result = memory.getVar(currentInstruction.getOperand1()) * memory.getVar(currentInstruction.getOperand2());
                        memory.storeVar(currentInstruction.getVariable(), result);break;
                    case "divide":
                        result = memory.getVar(currentInstruction.getOperand1()) / memory.getVar(currentInstruction.getOperand2());
                        memory.storeVar(currentInstruction.getVariable(), result);break;
                }
                
            }
            break;
            
            case "print": System.out.println(memory.getVar(currentInstruction.getVariable()));break;
        }
    }

    public Process getCurrProcess() {
        return currProcess;
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

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }
    
}
