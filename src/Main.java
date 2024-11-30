import Cores.MasterCore;
import Memory.Memory;
import Parse.ProgramParser;

public class Main {
    public static void main(String[] args) {
        ProgramParser parser = new ProgramParser();
        Memory sharedMemory = new Memory();

        MasterCore master1 = new MasterCore(parser.parseProgram("Programs/Program_1.txt"), sharedMemory);
        MasterCore master2 = new MasterCore(parser.parseProgram("Programs/Program_2.txt"), sharedMemory);

        new Thread(master1::scheduleTask).start();
        new Thread(master2::scheduleTask).start();
    }
    //If you do any modifications please tell me, so I can implement them!
    //If you get files not found change the inserted file names above to the correct path
}