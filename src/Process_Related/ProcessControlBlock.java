package Process_Related;
public class ProcessControlBlock{
    final private int pid;
    private int programCounter; //instruction currently on

    public ProcessControlBlock(int pid){
        this.pid = pid;
        this.programCounter = 0;
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
}