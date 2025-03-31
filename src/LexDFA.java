import javafx.util.Pair;
import java.util.Vector;

public class LexDFA {
    private String sentence;
    private Vector<Pair<String, String>> tokens;
    private boolean right;

    LexDFA(String sentence) {
        this.sentence = sentence.trim();
        tokens = new Vector<>();
        right = true;
        analyze();
    }

    private void analyze() {
        int i = 0;
        while (i < sentence.length()) {
            char c = sentence.charAt(i);
            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }
            if (c == '+' || c == '*') {
                tokens.add(new Pair<>("OP", String.valueOf(c)));
                i++;
            } else if (Character.isDigit(c)) {
                StringBuilder num = new StringBuilder();
                while (i < sentence.length() && Character.isDigit(sentence.charAt(i))) {
                    num.append(sentence.charAt(i));
                    i++;
                }
                tokens.add(new Pair<>("NUM", num.toString()));
            } else {
                tokens.add(new Pair<>("ERR", String.valueOf(c)));
                right = false;
                i++;
            }
        }
    }

    public Vector<Pair<String, String>> getTokens() {
        return tokens;
    }

    public boolean isRight() {
        return right;
    }
}