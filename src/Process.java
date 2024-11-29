// Process.java
public class Process {
    private int pid;
    private int burstTime;
    private int arrivalTime;
    private int priority;
    private ProcessState state;
    private Integer remainingTime;

    public enum ProcessState {
        NEW, READY, RUNNING, WAITING, TERMINATED
    }

    public Process(int pid, int burstTime, int arrivalTime) {
        this.pid = pid;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = 0;
        this.state = ProcessState.NEW;
        this.remainingTime = burstTime;
    }

    // Getters and setters
    public int getPid() { return pid; }
    public int getBurstTime() { return burstTime; }
    public int getArrivalTime() { return arrivalTime; }
    public ProcessState getState() { return state; }
    public void setState(ProcessState state) { this.state = state; }
    public Integer getRemainingTime() { return remainingTime; }
    public void setRemainingTime(Integer remainingTime) { this.remainingTime = remainingTime; }
}