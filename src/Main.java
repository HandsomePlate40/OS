import Cores.*;
import Parse.ProgramParser;

public class Main {
    public static void main(String[] args) {
        ProgramParser parser = new ProgramParser();
        
        MasterCore master = new MasterCore(parser.parseProgram("Program_1.txt"));
        master.scheduleTask();
    }
}