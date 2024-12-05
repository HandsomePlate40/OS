package Process_Related;

public class Process implements Comparable<Process> {
    private int pid;
    private ProcessControlBlock pcb;

    public Process() {
        this.pid = 0;
    }

    public int getPid() { return pid; }
    public void setPid(int pid) { this.pid = pid; }

    public ProcessControlBlock getPcb() { return pcb; }
    public void setPcb(ProcessControlBlock pcb) { this.pcb = pcb; }

    @Override
    public int compareTo(Process other) {
        return Integer.compare(this.pcb.getMemoryBlock().getInstructionsCount(), other.pcb.getMemoryBlock().getInstructionsCount());
    }
}