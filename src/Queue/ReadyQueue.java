package Queue;
import java.util.Queue;

import Process_Related.Process;

public class ReadyQueue {
    private Queue<Process> readyQueue;

    public ReadyQueue(Queue<Process> queue) {
        this.readyQueue = queue;
    }

    public void addProcess(Process process) {
        readyQueue.add(process);
    }

    public Process removeProcess() {
        return readyQueue.remove();
    }

    public boolean isEmpty() {
        return readyQueue.isEmpty();
    }
}
