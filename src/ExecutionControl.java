import Cores.MasterCore;
import Parse.ProgramParser;
import Queue.ReadyQueue;

public class ExecutionControl {
    public static void main(String[] args) {

        ProgramParser parser = new ProgramParser();
        ReadyQueue readyQueue = new ReadyQueue();

        System.out.println();
        System.out.println("******************Welcome to the Simulator******************");
        System.out.println();
        System.out.println("******************Programs loaded into ReadyQueue******************");
        System.out.println();

        readyQueue.addProcess(parser.parseProgram("Programs/Program_1.txt"));
        readyQueue.addProcess(parser.parseProgram("Programs/Program_2.txt"));
        readyQueue.addProcess(parser.parseProgram("Programs/Program_3.txt"));

        new MasterCore(readyQueue, parser.getSharedMemory()); //starter
    }
}