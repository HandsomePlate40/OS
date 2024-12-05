package Process_Related;

import Memory.MemoryBlock;

public class ProcessControlBlock{
    private int programCounter;
    private ProcessState state;
    private final int limit;
    private MemoryBlock memoryBlock;

    public ProcessState getState() {
        return state;
    }

    public enum ProcessState {
        NEW, READY, RUNNING, TERMINATED
    }

    public ProcessControlBlock(int limit){
        this.programCounter = 0;
        this.state = ProcessState.NEW;
        this.limit = limit;
    }

    public void updateProgramCounter(){
        programCounter++;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public void assignSharedMemoryBlock(MemoryBlock memory){
        memoryBlock = memory;
    }

    public MemoryBlock getMemoryBlock(){
        return memoryBlock;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public int getLimit() {
        return limit;
    }
}