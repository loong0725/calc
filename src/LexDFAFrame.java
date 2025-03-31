import javafx.util.Pair;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

class LexDFAFrame extends JFrame {
    private String title;
    private JLabel showLabel;
    private JTextField writeField;
    private JButton showButton;
    private JScrollPane scrollPane;

    LexDFAFrame() {
        this("Prefix Expression Lexical Analysis");
    }

    LexDFAFrame(String title) {
        this.title = title;
    }

    public void init() {
        this.setLayout(null);
        writeField = new JTextField(10);
        writeField.setBounds(45, 45, 720, 45);

        showLabel = new JLabel("Enter a prefix expression (e.g., '+ * 2 3 4'), click the button or press Enter for lexical analysis, up to 100 characters.");

        scrollPane = new JScrollPane(showLabel);
        scrollPane.setBounds(45, 135, 810, 720);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        showButton = new JButton("Lexical Analysis");
        showButton.setBounds(765, 45, 90, 45);
        showButton.addActionListener(new ShowListener());

        writeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    showButton.doClick();
                }
            }
        });
        writeField.setDocument(new LimitDocument(100)); // Adjusted to 100 for clarity
        this.add(writeField);
        this.add(scrollPane);
        this.add(showButton);
    }

    public void view() {
        init();
        this.setTitle(title);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        Insets insets = this.getInsets();
        this.setSize(insets.left + insets.right + 900, insets.top + insets.bottom + 900);
        this.setLocationRelativeTo(null);
    }

    class ShowListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = writeField.getText().trim();
            if (input.isEmpty()) {
                showLabel.setText("Input cannot be empty!");
                return;
            }

            LexDFA lex = new LexDFA(input);
            Vector<Pair<String, String>> result = lex.getTokens();

            if (result == null || result.size() == 0) {
                showLabel.setText("No valid tokens, please enter a valid prefix expression!");
                return;
            }

            if (!lex.isRight()) {
                StringBuilder text = new StringBuilder("<html><body>Lexical analysis error! Detected tokens:<br />");
                for (Pair<String, String> token : result) {
                    text.append(token.getKey()).append(": ").append(token.getValue()).append("<br />");
                }
                text.append("</body></html>");
                showLabel.setText(text.toString());
                return;
            }

            StringBuilder text = new StringBuilder("<html><body>Lexical analysis result:<br />");
            for (Pair<String, String> token : result) {
                text.append(token.getKey()).append(": ").append(token.getValue()).append("<br />");
            }
            text.append("</body></html>");
            showLabel.setText(text.toString());
            requestFocus();
        }
    }

    class LimitDocument extends PlainDocument {
        private int maxLength;

        public LimitDocument(int newMaxLength) {
            super();
            maxLength = newMaxLength;
        }

        @Override
        public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
            if (str == null) return;
            // Allow spaces in input (prefix expressions use them as separators)
            if (getLength() + str.length() > maxLength) {
                if (maxLength - getLength() > 0)
                    str = str.substring(0, maxLength - getLength());
                else
                    str = "";
            }
            super.insertString(offset, str, a);
        }
    }
}
