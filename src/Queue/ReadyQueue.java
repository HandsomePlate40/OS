package Queue;
import java.util.LinkedList;
import java.util.Queue;


import Process_Related.Process;

public class ReadyQueue {
    private Queue<Process> readyQueue;

    public ReadyQueue() {
        this.readyQueue = new LinkedList<>();
    }

    public void addProcess(Process process) {
        readyQueue.add(process);
    }

    public Process peekProcess() {
        return readyQueue.peek();
    }

    public Process removeProcess() {
        if (readyQueue.isEmpty()) {
            return null;
        } else {
            return readyQueue.remove();
        }
    }

    public boolean isEmpty() {
        return readyQueue.isEmpty();
    }

    public Queue<Process> getReadyQueue() {
        return readyQueue;
    }
}
