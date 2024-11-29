package Queue;
import java.util.Queue;

import Process_Related.Process;

public class ReadyQueue {
    private Queue<Process> readyQueue;

    public ReadyQueue() {
        // Initialize the readyQueue and add process from ProgramParser
    }

    public void addProcess(Process process) {
        readyQueue.add(process);
    }

    public Process peekProcess() {
        return readyQueue.peek();
    }

    public Process removeProcess() {
        return readyQueue.remove();
    }

    public boolean isEmpty() {
        return readyQueue.isEmpty();
    }

    public Queue<Process> getReadyQueue() {
        return readyQueue;
    }
}
