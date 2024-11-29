package Cores;
import Memory.Memory;
import Parse.Instruction;
import Process_Related.Process;

public class SlaveCore extends Thread {
    private Process currProcess;
    private boolean status;
    private Memory memory;

    public SlaveCore() {
        this.memory = new Memory();
        this.status = false;
    }

    public void executeTask(Instruction currentInstruction) {
        
        switch(currentInstruction.getOperation()){

            case "assign": 
            if(currentInstruction.getOperation2() == null){
                memory.storeVar(currentInstruction.getVariable(), Integer.parseInt(currentInstruction.getOperand1()));break;
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
