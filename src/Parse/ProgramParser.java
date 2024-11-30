package Parse;

import Process_Related.Process;
import Process_Related.ProcessControlBlock;
import Queue.ReadyQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgramParser {

    ReadyQueue readyQueue = new ReadyQueue();
    int PIDCounter = 0;

    public ReadyQueue parseProgram(String fileName) {

        List<Instruction> instructions = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Debug print
                //System.out.println("Processing line: " + line);
                
                String[] parts = line.trim().split("\\s+");
                
                // Debug print
                //System.out.println("Parts length: " + parts.length);
                //System.out.println("First part: " + parts[3]);
    
                switch (parts[0].toLowerCase()) {  // Make case-insensitive
                    case "assign":
                        if (parts.length < 3) {
                            System.out.println("Invalid assign instruction: " + line);
                            continue;
                        }
                        if (parts[2].equals("input")) {
                            instructions.add(new Instruction("assign", parts[1], "input", parts[3]));
                            
                        } else {
                            switch(parts[2]){
                                case "add":
                                case "subtract":
                                case "multiply":
                                case "divide":
                                    instructions.add(new Instruction("assign", parts[1], parts[2], parts[3], parts[4]));
                                    break;
                                default:
                                    System.out.println("Unknown operation: " + parts[2]);
                            }
                        }
                        break;
    
                    case "print":
                        if (parts.length < 2) {
                            System.out.println("Invalid print instruction: " + line);
                            continue;
                        }
                        instructions.add(new Instruction("print", parts[1], null, null));
                        break;
    
                    default:
                        System.out.println("Unknown instruction type: " + parts[0]);
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + fileName);
        }
        return createProcess(instructions);
    }

    public ReadyQueue createProcess(List<Instruction> instructions) {

        Process newProcess = new Process();
        newProcess.setPid(PIDAssigner());

        ProcessControlBlock newPCB = new ProcessControlBlock(newProcess.getPid());
        newProcess.setPcb(newPCB);

        newProcess.setInstructions(instructions);
        newProcess.getPcb().setState(ProcessControlBlock.ProcessState.READY);
        readyQueue.addProcess(newProcess);
        return readyQueue;
    }

    public int PIDAssigner() {
        return PIDCounter++;
    }
}

