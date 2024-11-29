package Process_Related;
public class ProcessControlBlock{
    final private int pid;
    private int programCounter; //instruction currently on
    private ProcessState state;

    public enum ProcessState {
        NEW, READY, RUNNING, WAITING, TERMINATED
    }

    public ProcessControlBlock(int pid){
        this.pid = pid;
        this.programCounter = 0;
        this.state = ProcessState.NEW;
    }

    public void updateProgramCounter(){
        programCounter++;
    }

    public int getPid() {
        return pid;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public ProcessState getState() {
        return state;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }
}