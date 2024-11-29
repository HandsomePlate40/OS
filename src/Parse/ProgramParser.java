package Parse;
import Process_Related.Process;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ProgramParser {
    public static List<Instruction> parseProgram(String fileName) {
        List<Instruction> instructions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(" ");

                switch (parts[0]) {
                    case "assign":
                        // Variable assignment: assign x input OR assign z add a b
                        if (parts[2].equals("input")) {
                            instructions.add(new Instruction("assign", parts[1], "input", null));
                        } else {
                            instructions.add(new Instruction("assign", parts[1], parts[2], parts[3]));
                        }
                        break;

                    case "print":
                        // Print command: print x
                        instructions.add(new Instruction("print", parts[1], null, null));
                        break;

                    default:
                        System.out.println("Unknown instruction: " + line);
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return instructions;
    }

 public static List<Process> createProcess(List<Instruction> instructions) {

    List<Process> process = new ArrayList<>();

    Process newProcess = new Process();
    newProcess.setPid(0); //MAKE ASSIGNER LATER 
    List<Instruction> instructionsList = new ArrayList<>();

    for (Instruction instruction : instructions) {
        instructionsList.add(instruction);
    }
    newProcess.setInstructions(instructionsList);
    process.add(newProcess);
    return process;
}




    public static void main(String[] args) {
        List<Instruction> program = parseProgram("Program_1.txt");

        // Debug: Print parsed instructions
        for (Instruction instr : program) {
            System.out.println(instr);
        }
    }
}

