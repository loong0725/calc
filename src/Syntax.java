import javafx.util.Pair;
import java.math.BigInteger;
import java.util.Vector;

public class Syntax {
    private String input;
    private Vector<Pair<String, String>> tokens;
    private int currentIndex; // 用于跟踪当前 token 的位置

    public Syntax(String input) {
        this.input = input.trim();
        LexDFA lex = new LexDFA(input);
        this.tokens = lex.getTokens();
        this.currentIndex = 0;
    }

    public boolean isRight() {
        if (!new LexDFA(input).isRight() || tokens.isEmpty()) return false;
        try {
            parseExpr(); // 尝试解析整个表达式
            return currentIndex == tokens.size(); // 检查是否所有 token 都被消耗
        } catch (Exception e) {
            return false;
        }
    }

    public Pair<BigInteger, String> getResult() {
        currentIndex = 0; // 重置索引
        try {
            return parseExpr(); // 解析并返回结果
        } catch (Exception e) {
            return new Pair<>(null, "Syntax error: " + e.getMessage());
        }
    }

    // LL(1) 递归下降解析器：解析 <Expr>
    private Pair<BigInteger, String> parseExpr() throws Exception {
        if (currentIndex >= tokens.size()) {
            throw new Exception("Unexpected end of input");
        }

        Pair<String, String> currentToken = tokens.get(currentIndex);
        String type = currentToken.getKey();
        String value = currentToken.getValue();

        // 根据当前 token 类型（前瞻符号）决定推导
        if (type.equals("OP")) { // <Expr> ::= <Op> <Expr> <Expr>
            currentIndex++; // 消耗操作符
            Pair<BigInteger, String> expr1 = parseExpr(); // 递归解析第一个子表达式
            Pair<BigInteger, String> expr2 = parseExpr(); // 递归解析第二个子表达式

            BigInteger result;
            String infixExpr;
            if (value.equals("+")) {
                result = expr1.getKey().add(expr2.getKey());
                infixExpr = "(" + expr1.getValue() + " + " + expr2.getValue() + ")";
            } else { // "*"
                result = expr1.getKey().multiply(expr2.getKey());
                infixExpr = expr1.getValue().contains(" ") || expr2.getValue().contains(" ") ?
                        "(" + expr1.getValue() + " * " + expr2.getValue() + ")" :
                        expr1.getValue() + " * " + expr2.getValue();
            }
            return new Pair<>(result, infixExpr);
        } else if (type.equals("NUM")) { // <Expr> ::= <Num>
            currentIndex++; // 消耗数字
            return new Pair<>(new BigInteger(value), value);
        } else {
            throw new Exception("Invalid token: " + value);
        }
    }
}
