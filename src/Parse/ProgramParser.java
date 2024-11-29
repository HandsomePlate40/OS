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

    public void parseProgram(String fileName) {
        List<Instruction> instructions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(" ");

                switch (parts[0]) {
                    case "assign":
                        // Variable assignment: assign x input OR assign z add a b
                        if (parts[2].equals("input")) {
                            instructions.add(new Instruction("assign", parts[1], "input", parts[3]));
                        } else {
                                switch(parts[2]){
                                    case "add":
                                        instructions.add(new Instruction("assign", parts[1], "add", parts[3], parts[4]));
                                        break;

                                    case "subtract":
                                        instructions.add(new Instruction("assign", parts[1], "subtract", parts[3], parts[4]));
                                        break;

                                    case "multiply":
                                        instructions.add(new Instruction("assign", parts[1], "multiply", parts[3], parts[4]));
                                        break;
                                    
                                    case "divide": 
                                        instructions.add(new Instruction("assign", parts[1], "divide", parts[3], parts[4]));
                                        break;
                                }
                            
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
        createProcess(instructions);
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

        int maxPID = -1; 

        for(Process process : readyQueue.getReadyQueue()) {
            if(process.getPid() > maxPID) {
                maxPID = process.getPid();
            }
        }
        return maxPID + 1;
    }
}

