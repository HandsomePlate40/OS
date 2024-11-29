package Cores;
import Memory.Memory;
import Process_Related.Process;

public class SlaveCore extends Thread {
    private Process currProcess;
    private boolean status;
    Memory memory;

    public SlaveCore() {
        this.memory = new Memory();
        this.status = false;
    }

    public Process getCurrProcess() {
        return currProcess;
    }

    public void setCurrProcess(Process currProcess) {
        this.currProcess = currProcess;
    }

    public boolean isRunning() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }
    
}
