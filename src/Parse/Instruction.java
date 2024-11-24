package Parse;

public class Instruction {
    String operation; // e.g., assign, add, print
    String variable;  // Target variable (if applicable)
    String operand1;  // First operand (if applicable)
    String operand2;  // Second operand (if applicable)

    public Instruction(String operation, String variable, String operand1, String operand2) {
        this.operation = operation;
        this.variable = variable;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    @Override
    public String toString() {
        return operation + " " + variable + " " + operand1 + " " + operand2;
    }
}

