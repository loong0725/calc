import javafx.util.Pair;
import java.math.BigInteger;
import java.util.Stack;
import java.util.Vector;

public class Syntax {
    private String input;

    public Syntax(String input) {
        this.input = input.trim();
    }

    public boolean isRight() {
        LexDFA lex = new LexDFA(input);
        Vector<Pair<String, String>> tokens = lex.getTokens();
        if (!lex.isRight() || tokens.isEmpty()) return false;

        int operandCount = 0, operatorCount = 0;
        for (Pair<String, String> token : tokens) {
            if (token.getKey().equals("NUM")) operandCount++;
            else if (token.getKey().equals("OP")) operatorCount++;
        }
        return operandCount == operatorCount + 1;
    }

    public Pair<BigInteger, String> getResult() {
        LexDFA lex = new LexDFA(input);
        Vector<Pair<String, String>> tokens = lex.getTokens();

        Stack<BigInteger> valueStack = new Stack<>();
        Stack<String> infixStack = new Stack<>();

        for (int i = tokens.size() - 1; i >= 0; i--) {
            Pair<String, String> token = tokens.get(i);
            String type = token.getKey();
            String value = token.getValue();

            if (type.equals("NUM")) {
                valueStack.push(new BigInteger(value));
                infixStack.push(value);
            } else if (type.equals("OP")) {
                // Check if there are enough operands
                if (valueStack.size() < 2 || infixStack.size() < 2) {
                    return new Pair<>(null, "Syntax error: Not enough operands for operator " + value);
                }
                BigInteger operand1 = valueStack.pop();
                BigInteger operand2 = valueStack.pop();
                String infix1 = infixStack.pop();
                String infix2 = infixStack.pop();

                BigInteger result;
                String infixExpr;
                if (value.equals("+")) {
                    result = operand1.add(operand2);
                    infixExpr = "(" + infix1 + " + " + infix2 + ")";
                } else { // "*"
                    result = operand1.multiply(operand2);
                    infixExpr = infix1.contains(" ") || infix2.contains(" ") ?
                            "(" + infix1 + " * " + infix2 + ")" : infix1 + " * " + infix2;
                }
                valueStack.push(result);
                infixStack.push(infixExpr);
            }
        }

        // Ensure the expression is fully evaluated
        if (valueStack.size() != 1 || infixStack.size() != 1) {
            return new Pair<>(null, "Syntax error: Invalid prefix expression structure");
        }

        return new Pair<>(valueStack.pop(), infixStack.pop());
    }
}