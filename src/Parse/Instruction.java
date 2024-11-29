package Parse;

public class Instruction {
    String operation; 
    String variable;  
    String operand1;  
    String operand2; 

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

    public String getOperation() {
        return operation;
    }

    public String getVariable() {
        return variable;
    }

    public String getOperand1() {
        return operand1;
    }

    public String getOperand2() {
        return operand2;
    }
}

