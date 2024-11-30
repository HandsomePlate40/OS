package Queue;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;
import Process_Related.Process;

public class ReadyQueue {
    private final Queue<Process> readyQueue;
    private final ReentrantLock lock;

    public ReadyQueue() {
        this.readyQueue = new LinkedList<>();
        this.lock = new ReentrantLock();
    }

    public void addProcess(Process process) {
        lock.lock();
        try {
            readyQueue.add(process);
        } finally {
            lock.unlock();
        }
    }

    public Process peekProcess() {
        lock.lock();
        try {
            return readyQueue.peek();
        } finally {
            lock.unlock();
        }
    }

    public void removeProcess() {
        lock.lock();
        try {
            if (readyQueue.isEmpty()) {
            } else {
                readyQueue.remove();
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        lock.lock();
        try {
            return readyQueue.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    public Queue<Process> getReadyQueue() {
        lock.lock();
        try {
            return readyQueue;
        } finally {
            lock.unlock();
        }
    }
}