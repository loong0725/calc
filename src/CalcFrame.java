import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;
import java.math.BigInteger;
import javafx.util.Pair;

class CalcFrame extends JFrame {

    private String title;
    private static String[] VALS = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0"};
    private static String[] OPTS = {"+", "*", "="};
    private static String[] CABS = {"Clear", "Back", "Space"};
    private JPanel btnPanel;
    private JLabel inputLabel, resultLabel, infixLabel;
    private JButton[] btnVals, btnOpts, btnCabs;
    private static final Color MAIN_BG = new Color(245, 245, 245);
    private static final Color DISPLAY_BG = new Color(255, 255, 255);
    private static final Color BUTTON_NUM_BG = new Color(240, 240, 240);
    private static final Color BUTTON_OP_BG = new Color(255, 165, 0);
    private static final Color BUTTON_FUNC_BG = new Color(200, 200, 200);
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 60;
    private static final int BORDER_THICKNESS = 2;

    CalcFrame() {
        this("Calculator");
    }

    CalcFrame(String title) {
        this.title = title;
    }

    public void init() {
        btnVals = new JButton[VALS.length];
        btnOpts = new JButton[OPTS.length];
        btnCabs = new JButton[CABS.length];
        for (int i = 0; i < VALS.length; ++i)
            btnVals[i] = new JButton(VALS[i]);
        for (int i = 0; i < OPTS.length; ++i)
            btnOpts[i] = new JButton(OPTS[i]);
        for (int i = 0; i < CABS.length; ++i)
            btnCabs[i] = new JButton(CABS[i]);

        // Attach listeners
        KeyListener keyListener = new CalcKeyListener();
        this.addKeyListener(keyListener);

        CalcValsListener calcValsListener = new CalcValsListener();
        for (int i = 0; i < VALS.length; ++i)
            btnVals[i].addActionListener(calcValsListener);

        CalcOptsListener calcOptsListener = new CalcOptsListener();
        for (int i = 0; i < OPTS.length; ++i)
            btnOpts[i].addActionListener(calcOptsListener);

        CalcCabsListener calcCabsListener = new CalcCabsListener();
        for (int i = 0; i < CABS.length; ++i)
            btnCabs[i].addActionListener(calcCabsListener);

        // Initialize display labels
        inputLabel = new JLabel("", JLabel.RIGHT);
        resultLabel = new JLabel("result: ", JLabel.RIGHT);
        infixLabel = new JLabel("Infix Expression: ", JLabel.RIGHT);

        this.setLayout(null);
        btnPanel = new JPanel();
        btnPanel.setLayout(null);

        inputLabel.setBounds(30, 30, 360, 30);
        inputLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(inputLabel);

        resultLabel.setBounds(30, 70, 360, 30);
        resultLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        this.add(resultLabel);

        infixLabel.setBounds(30, 110, 360, 30);
        infixLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        this.add(infixLabel);

        // Arrange buttons in a grid
        btnPanel.setBounds(30, 150, 360, 320);
        // Row 1: Clear, Back, Space
        btnCabs[0].setBounds(0, 0, 110, BUTTON_HEIGHT);  // Clear
        btnCabs[1].setBounds(120, 0, 110, BUTTON_HEIGHT); // Back
        btnCabs[2].setBounds(240, 0, 110, BUTTON_HEIGHT); // Space
        btnPanel.add(btnCabs[0]);
        btnPanel.add(btnCabs[1]);
        btnPanel.add(btnCabs[2]);
        // Row 2: 7, 8, 9, +
        btnVals[0].setBounds(0, 70, BUTTON_WIDTH, BUTTON_HEIGHT);  // 7
        btnVals[1].setBounds(90, 70, BUTTON_WIDTH, BUTTON_HEIGHT);  // 8
        btnVals[2].setBounds(180, 70, BUTTON_WIDTH, BUTTON_HEIGHT); // 9
        btnOpts[0].setBounds(270, 70, BUTTON_WIDTH, BUTTON_HEIGHT); // +
        btnPanel.add(btnVals[0]);
        btnPanel.add(btnVals[1]);
        btnPanel.add(btnVals[2]);
        btnPanel.add(btnOpts[0]);
        // Row 3: 4, 5, 6, *
        btnVals[3].setBounds(0, 140, BUTTON_WIDTH, BUTTON_HEIGHT);  // 4
        btnVals[4].setBounds(90, 140, BUTTON_WIDTH, BUTTON_HEIGHT);  // 5
        btnVals[5].setBounds(180, 140, BUTTON_WIDTH, BUTTON_HEIGHT); // 6
        btnOpts[1].setBounds(270, 140, BUTTON_WIDTH, BUTTON_HEIGHT); // *
        btnPanel.add(btnVals[3]);
        btnPanel.add(btnVals[4]);
        btnPanel.add(btnVals[5]);
        btnPanel.add(btnOpts[1]);
        // Row 4: 1, 2, 3, =
        btnVals[6].setBounds(0, 210, BUTTON_WIDTH, BUTTON_HEIGHT);  // 1
        btnVals[7].setBounds(90, 210, BUTTON_WIDTH, BUTTON_HEIGHT);  // 2
        btnVals[8].setBounds(180, 210, BUTTON_WIDTH, BUTTON_HEIGHT); // 3
        btnOpts[2].setBounds(270, 210, BUTTON_WIDTH, BUTTON_HEIGHT); // =
        btnPanel.add(btnVals[6]);
        btnPanel.add(btnVals[7]);
        btnPanel.add(btnVals[8]);
        btnPanel.add(btnOpts[2]);
        // Row 5: 0 (centered)
        btnVals[9].setBounds(90, 280, BUTTON_WIDTH, BUTTON_HEIGHT);  // 0
        btnPanel.add(btnVals[9]);

        this.add(btnPanel);

        this.getContentPane().setBackground(MAIN_BG);

        Font displayFont = new Font("SansSerif", Font.BOLD, 18);
        styleLabel(inputLabel, DISPLAY_BG, new Color(100, 100, 100), displayFont, BORDER_THICKNESS);
        styleLabel(resultLabel, DISPLAY_BG, Color.BLUE, displayFont, BORDER_THICKNESS);
        styleLabel(infixLabel, DISPLAY_BG, new Color(0, 150, 0), displayFont, BORDER_THICKNESS);

        Font btnFont = new Font("SansSerif", Font.BOLD, 16);
        int btnRadius = 8;
        styleButtons(btnVals, BUTTON_NUM_BG, Color.BLACK, btnFont, btnRadius);
        styleButtons(btnOpts, BUTTON_OP_BG, Color.WHITE, btnFont, btnRadius);
        styleButtons(btnCabs, BUTTON_FUNC_BG, Color.BLACK, btnFont, btnRadius);

        addButtonHoverEffects(btnVals, BUTTON_NUM_BG.brighter());
        addButtonHoverEffects(btnOpts, BUTTON_OP_BG.brighter());
        addButtonHoverEffects(btnCabs, BUTTON_FUNC_BG.brighter());
    }

    public void view() {
        init();
        this.setTitle(title);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setFocusable(true);
        this.setVisible(true);
        Insets insets = this.getInsets();
        this.setSize(insets.left + insets.right + 420, insets.top + insets.bottom + 500);
        this.setLocationRelativeTo(null);
    }

    public String getInputText() {
        return inputLabel.getText(); // Remove .trim() to preserve spaces
    }

    public void setInputText(String text) {
        inputLabel.setText(text);
    }

    public void setResultLabel(String text) {
        resultLabel.setText("result: " + text);
    }

    public void setInfixLabel(String text) {
        infixLabel.setText("Infix Expression: " + text);
    }

    private void clearAll() {
        setInputText("");
        setResultLabel("");
        setInfixLabel("");
    }

    private void styleLabel(JLabel label, Color bg, Color borderColor, Font font, int borderThickness) {
        label.setOpaque(true);
        label.setBackground(bg);
        label.setBorder(new CompoundBorder(
                new LineBorder(borderColor, borderThickness),
                new EmptyBorder(5, 10, 5, 10)
        ));
        label.setFont(font);
        label.setForeground(borderColor.darker());
    }

    private void styleButtons(JButton[] buttons, Color bg, Color fg, Font font, int radius) {
        for (JButton btn : buttons) {
            btn.setBackground(bg);
            btn.setForeground(fg);
            btn.setFont(font);
            btn.setFocusPainted(false);
            btn.setBorder(new RoundedBorder(radius, 1, bg.darker()));
        }
    }

    private void addButtonHoverEffects(JButton[] buttons, Color hoverColor) {
        for (JButton btn : buttons) {
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setBackground(hoverColor);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setBackground(((RoundedBorder)btn.getBorder()).getBaseColor());
                }
            });
        }
    }

    class CalcValsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            String text = getInputText();
            // Check if the last character (ignoring trailing spaces) is a digit
            String trimmedText = text.trim();
            if (!trimmedText.isEmpty() && Character.isDigit(trimmedText.charAt(trimmedText.length() - 1))) {
                setInputText(text + command);
            } else {
                setInputText(text + (trimmedText.isEmpty() ? "" : " ") + command);
            }
            requestFocusInWindow();
        }
    }

    class CalcOptsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("=")) {
                String input = getInputText().trim(); // Trim only when computing
                Calc calc = new Calc(input);
                Pair<BigInteger, String> res = calc.getResult();
                if (res.getValue().equals("Syntax error")) {
                    setResultLabel("Syntax error");
                    setInfixLabel("");
                } else {
                    setResultLabel(res.getKey().toString());
                    setInfixLabel(res.getValue());
                }
            } else {
                String text = getInputText();
                String trimmedText = text.trim();
                setInputText(text + (trimmedText.isEmpty() ? "" : " ") + command + " ");
            }
            requestFocusInWindow();
        }
    }

    class CalcCabsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            String text = getInputText();
            if (command.equals("Clear")) {
                clearAll();
            } else if (command.equals("Back")) {
                if (!text.trim().isEmpty()) {
                    String[] tokens = text.trim().split("\\s+");
                    if (tokens.length > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < tokens.length - 1; i++) {
                            sb.append(tokens[i]).append(" ");
                        }
                        setInputText(sb.toString());
                    }
                }
            } else if (command.equals("Space")) {
                setInputText(text + " ");
            }
            requestFocusInWindow();
        }
    }

    class CalcKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            char keyChar = e.getKeyChar();
            if (Character.isDigit(keyChar)) {
                for (JButton btn : btnVals) {
                    if (btn.getText().equals(String.valueOf(keyChar))) {
                        btn.doClick();
                        break;
                    }
                }
            } else if (keyChar == '+' || keyChar == '*') {
                for (JButton btn : btnOpts) {
                    if (btn.getText().equals(String.valueOf(keyChar))) {
                        btn.doClick();
                        break;
                    }
                }
            } else if (keyChar == '=' || e.getKeyCode() == KeyEvent.VK_ENTER) {
                for (JButton btn : btnOpts) {
                    if (btn.getText().equals("=")) {
                        btn.doClick();
                        break;
                    }
                }
            } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                for (JButton btn : btnCabs) {
                    if (btn.getText().equals("Back")) {
                        btn.doClick();
                        break;
                    }
                }
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                for (JButton btn : btnCabs) {
                    if (btn.getText().equals("Space")) {
                        btn.doClick();
                        break;
                    }
                }
            }
        }
    }

    static class RoundedBorder extends LineBorder {
        private final int radius;
        private final Color baseColor;

        public RoundedBorder(int radius, int thickness, Color color) {
            super(color, thickness, true);
            this.radius = radius;
            this.baseColor = color;
        }

        public Color getBaseColor() {
            return baseColor;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(lineColor);
            g2d.drawRoundRect(x, y, width-1, height-1, radius, radius);
            g2d.dispose();
        }
    }
}