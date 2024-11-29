import Cores.*;
import Memory.Memory;
import Parse.*;
import Parse.ProgramParser;
import Process_Related.Process;
import Process_Related.ProcessControlBlock;
import Queue.ReadyQueue;

public class Main {
    public static void main(String[] args) {
        ProgramParser parser = new ProgramParser();
        
        MasterCore master = new MasterCore(parser.parseProgram("Program_1.txt"));
        master.scheduleTask();
    }
}