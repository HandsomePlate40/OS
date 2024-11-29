package Process_Related;
import java.util.List;
import Parse.Instruction;

public class Process {
    private int pid;
    private int priority;
    private ProcessState state;
    private List<Instruction> instructions;

    public enum ProcessState {
        NEW, READY, RUNNING, WAITING, TERMINATED
    }

    public Process() {
        this.pid = 0;
        this.priority = 0;
        this.state = ProcessState.NEW;
    }

    public int getPid() { return pid; }
    public void setPid(int pid) { this.pid = pid; }
    public ProcessState getState() { return state; }
    public void setState(ProcessState state) { this.state = state; }
    public List<Instruction> getInstructions() { return instructions; }
    public int getPriority() { return priority; }
    public void setInstructions(List<Instruction> instructions) { this.instructions = instructions; }
}