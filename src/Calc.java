import javafx.util.Pair;
import java.math.BigInteger;

class Calc {
    private String sentence;

    Calc() {
        this("");
    }

    Calc(String sentence) {
        this.sentence = sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public Pair<BigInteger, String> getResult() {
        Syntax parser = new Syntax(sentence);
        if (!parser.isRight()) {
            return new Pair<>(null, "Syntax error");
        }
        return parser.getResult();
    }
}