package Parse;

import Process_Related.Process;
import Process_Related.ProcessControlBlock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgramParser {
    int PIDCounter = 0;

    public Process parseProgram(String fileName) {

        List<Instruction> instructions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.trim().split("\\s+");

                if (parts.length < 2) {
                    System.out.println("Invalid instruction: " + line + " in file: " + fileName);
                    System.exit(1);
                }

                switch (parts[0].toLowerCase()) {
                    case "assign":
                        if (parts.length < 4) {
                            System.out.println("Invalid assign instruction: " + line + " in file: " + fileName);
                            System.exit(1);
                        }
                        if (parts[2].equals("input")) {
                            instructions.add(new Instruction("assign", parts[1], "input", parts[3]));
                        } else {
                            if (parts.length < 5) {
                                System.out.println("Invalid assign operation: " + line + " in file: " + fileName);
                                System.exit(1);
                            }
                            switch(parts[2]){
                                case "add":
                                case "subtract":
                                case "multiply":
                                case "divide":
                                    instructions.add(new Instruction("assign", parts[1], parts[2], parts[3], parts[4]));
                                    break;
                                default:
                                    System.out.println("Unknown operation: " + parts[2] + " in file: " + fileName);
                                    System.exit(1);
                            }
                        }
                        break;

                    case "print":
                        if (parts.length < 2) {
                            System.out.println("Invalid print instruction: " + line + " in file: " + fileName);
                            System.exit(1);
                        }
                        instructions.add(new Instruction("print", parts[1], null, null));
                        break;

                    default:
                        System.out.println("Unknown instruction type: " + parts[0] + " in file: " + fileName);
                        System.exit(1);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + fileName);
            System.exit(1);
        }
        return createProcess(instructions);
    }

    public Process createProcess(List<Instruction> instructions) {

        Process newProcess = new Process();
        newProcess.setPid(PIDAssigner());

        ProcessControlBlock newPCB = new ProcessControlBlock(newProcess.getPid());
        newProcess.setPcb(newPCB);

        newProcess.setInstructions(instructions);
        newProcess.getPcb().setState(ProcessControlBlock.ProcessState.READY);
        return newProcess;
    }

    public int PIDAssigner() {
        return PIDCounter++;
    }
}