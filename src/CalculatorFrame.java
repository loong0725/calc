import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CalculatorFrame extends JFrame {

    private String title;
    private JButton[] buttons;
    private LexDFAFrame lexDFAFrame;
    private SyntaxFrame syntaxFrame;
    private CalcFrame calcFrame;

    CalculatorFrame() {
        this("Compiler Principle Experiment");
    }

    CalculatorFrame(String title) {
        this.title = title;
    }

    public void init() {
        this.setLayout(null);
        buttons = new JButton[3];
        buttons[0] = new JButton("Lexical Analyzer");
        buttons[1] = new JButton("Syntax Analyzer");
        buttons[2] = new JButton("Calculator");
        ButtonListener buttonListener = new ButtonListener();
        addButton(buttons[0], 20, 35, 107, 80, buttonListener);
        addButton(buttons[1], 147, 35, 107, 80, buttonListener);
        addButton(buttons[2], 274, 35, 107, 80, buttonListener);
        this.add(buttons[0]);
        this.add(buttons[1]);
        this.add(buttons[2]);
    }

    private void addButton(JButton button, int x, int y, int w, int h, ActionListener ac) {
        button.setBounds(x, y, w, h);
        button.addActionListener(ac);
    }

    public void view() {
        init();
        this.setTitle(title);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        Insets insets = this.getInsets();
        this.setSize(insets.left + insets.right + 401, insets.top + insets.bottom + 150);
        this.setLocationRelativeTo(null);
    }

    class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("Lexical Analyzer")) {
                if (lexDFAFrame == null || !lexDFAFrame.isVisible()) {
                    lexDFAFrame = new LexDFAFrame();
                    lexDFAFrame.view();
                } else {
                    lexDFAFrame.setLocationRelativeTo(null);
                    lexDFAFrame.setState(Frame.NORMAL);
                }
            } else if (command.equals("Syntax Analyzer")) {
                if (syntaxFrame == null || !syntaxFrame.isVisible()) {
                    syntaxFrame = new SyntaxFrame();
                    syntaxFrame.view();
                } else {
                    syntaxFrame.setLocationRelativeTo(null);
                    syntaxFrame.setState(Frame.NORMAL);
                }
            } else if (command.equals("Calculator")) {
                if (calcFrame == null || !calcFrame.isVisible()) {
                    calcFrame = new CalcFrame();
                    calcFrame.view();
                } else {
                    calcFrame.setLocationRelativeTo(null);
                    calcFrame.setState(Frame.NORMAL);
                }
            }
        }

    }

}
