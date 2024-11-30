package Process_Related;

import Parse.Instruction;

import java.util.List;

public class Process {
    private int pid;
    private List<Instruction> instructions;
    private ProcessControlBlock pcb;
    private boolean isComplete;

    public Process() {
        this.pid = 0;
        this.isComplete = false;
    }

    public int getPid() { return pid; }
    public void setPid(int pid) { this.pid = pid; }
    public List<Instruction> getInstructions() { return instructions; }
    public void setInstructions(List<Instruction> instructions) { this.instructions = instructions; }
    public ProcessControlBlock getPcb() {return pcb;}
    public void setPcb(ProcessControlBlock pcb) {this.pcb = pcb;}
    public boolean isComplete() { return isComplete; }
    public void setComplete(boolean complete) { isComplete = complete; }

    public Instruction getCurrentInstruction() { 
        int programCounter = pcb.getProgramCounter();
        if (programCounter >= instructions.size()) {
            isComplete = true;
            return null;
        }
        return instructions.get(programCounter); 
    }

}