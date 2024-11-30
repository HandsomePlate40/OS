// src/Main.java
import Cores.*;
import Parse.ProgramParser;
import Memory.Memory;

public class Main {
    public static void main(String[] args) {
        ProgramParser parser = new ProgramParser();
        Memory sharedMemory = new Memory();

        // Constructing
        MasterCore master1 = new MasterCore(parser.parseProgram("Programs/Program_1.txt"), sharedMemory);
        MasterCore master2 = new MasterCore(parser.parseProgram("Programs/Program_2.txt"), sharedMemory);

        // Running
        new Thread(master1::scheduleTask).start();
        new Thread(master2::scheduleTask).start();
    }
}