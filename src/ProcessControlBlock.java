public class ProcessControlBlock{
    private int pid;
    private int programCounter;
    private int[] memoryBounds; //start and end in memory

    public ProcessControlBlock(int pid, int[] memoryBounds){
        this.pid = pid;
        this.programCounter = 0;
        this.memoryBounds = memoryBounds;
    }
}