import Cores.MasterCore;
import Parse.ProgramParser;
import Queue.ReadyQueue;

public class ExecutionControl {
    public static void main(String[] args) {

        ProgramParser parser = new ProgramParser();
        ReadyQueue readyQueue = new ReadyQueue();

        readyQueue.addProcess(parser.parseProgram("Programs/Program_1.txt"));
        readyQueue.addProcess(parser.parseProgram("Programs/Program_2.txt"));
        readyQueue.addProcess(parser.parseProgram("Programs/Program_3.txt"));

        System.out.println();
        System.out.println("******************Welcome to the flawed Simulator******************");
        System.out.println();
        System.out.println("******************Programs loaded into ReadyQueue******************");
        System.out.println();

        new MasterCore(readyQueue, parser.getSharedMemory()); //starter

        //Program parser creates all processes, sets their PCB and shared memory and transfers it to mastercore
        //Mastercore schedules the processes to slave cores
        //Slave cores execute the processes and update the PCB and shared memory
        //Process checks are done in slave core and if process is complete, it is removed from memory and deallocated
        //If process is not complete, it is added back to ready queue
        //Memory assigned to each process with the limit being their number of assign instructions

    }
}