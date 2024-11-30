package Process_Related;
public class ProcessControlBlock{
    private int programCounter;
    private ProcessState state;
    private final int limit;

    public enum ProcessState {
        NEW, READY, RUNNING, WAITING, TERMINATED
    }

    public ProcessControlBlock(int pid, int limit){
        this.programCounter = 0;
        this.state = ProcessState.NEW;
        int base = 0;
        this.limit = limit;
    }

    public void updateProgramCounter(){
        programCounter++;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public int getLimit() {
        return limit;
    }
}