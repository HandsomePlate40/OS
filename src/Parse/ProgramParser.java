package Parse;

import Process_Related.Process;
import Process_Related.ProcessControlBlock;
import Queue.ReadyQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Cores.MasterCore;

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
        createProcess(instructions);
    }

    public ReadyQueue createProcess(List<Instruction> instructions) {

        Process newProcess = new Process();
        newProcess.setPid(PIDAssigner());

        ProcessControlBlock newPCB = new ProcessControlBlock(newProcess.getPid());
        newProcess.setPcb(newPCB);
        List<Instruction> instructionsList = new ArrayList<>();

        for (Instruction instruction : instructions) {
            instructionsList.add(instruction);
        }
        newProcess.setInstructions(instructionsList);
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

