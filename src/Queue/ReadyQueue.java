package Queue;

import Process_Related.Process;

import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ReadyQueue {
    private final PriorityQueue<Process> readyQueue;
    private final ReentrantLock lock;

    public ReadyQueue() {
        this.readyQueue = new PriorityQueue<>();
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
            readyQueue.poll();
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

    public PriorityQueue<Process> getReadyQueue() {
        lock.lock();
        try {
            return readyQueue;
        } finally {
            lock.unlock();
        }
    }

    public int getLength() {
        lock.lock();
        try {
            return readyQueue.size();
        } finally {
            lock.unlock();
        }
    }
}