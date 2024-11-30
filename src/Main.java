import Cores.MasterCore;
import Memory.Memory;
import Parse.ProgramParser;
import Queue.ReadyQueue;

public class Main {
    public static void main(String[] args) {
        ProgramParser parser = new ProgramParser();
        Memory sharedMemory = new Memory();
        ReadyQueue readyQueue = new ReadyQueue();

        readyQueue.addProcess(parser.parseProgram("Programs/Program_1.txt"));
        readyQueue.addProcess(parser.parseProgram("Programs/Program_2.txt"));
        readyQueue.addProcess(parser.parseProgram("Programs/Program_3.txt"));

        MasterCore master = new MasterCore(readyQueue, sharedMemory);

        master.scheduleTask();

        //new Thread(master::scheduleTask).start();
    }
}