import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.text.DecimalFormat;
import java.lang.Math;

public class ScientificCalculator extends JFrame implements ActionListener {
	private JTextField display;
	private String currentInput = "";
	private boolean newCalculation = true;

	// Buttons for decimal and scientific operations
	private final String[] buttonLabels = {
        	"7", "8", "9", "/", "sin",
        	"4", "5", "6", "*", "cos",
        	"1", "2", "3", "-", "tan",
        	"0", ".", "=", "+", "sqrt",
        	"C", "log", "exp", "pow", "%"
	};

	public ScientificCalculator() {
    	setTitle("Scientific Calculator");
    	setSize(400, 500);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setLayout(new BorderLayout());

    	// Display Field
    	display = new JTextField();
    	display.setEditable(false);
    	display.setFont(new Font("Arial", Font.BOLD, 20));
    	display.setHorizontalAlignment(JTextField.RIGHT);
    	add(display, BorderLayout.NORTH);

    	// Panel for buttons
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(5, 5, 5, 5));

    	// Add buttons
    	for (String label : buttonLabels) {
        	JButton button = new JButton(label);
        	button.setFont(new Font("Arial", Font.BOLD, 18));
        	button.addActionListener(this);
        	panel.add(button);
    	}

    	add(panel, BorderLayout.CENTER);
    	setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
    	String command = e.getActionCommand();

    	try {
        	switch (command) {
            	case "C":
                	currentInput = "";
                	display.setText("");
                	return;
            	case "=":
                	currentInput = evaluateExpression(currentInput);
                	display.setText(currentInput);
                	newCalculation = true;
                	return;
            	case "sin":
                	currentInput = String.valueOf(Math.sin(Math.toRadians(Double.parseDouble(currentInput))));
                	display.setText(currentInput);
                	return;
            	case "cos":
                	currentInput = String.valueOf(Math.cos(Math.toRadians(Double.parseDouble(currentInput))));
                	display.setText(currentInput);
                	return;
            	case "tan":
                	currentInput = String.valueOf(Math.tan(Math.toRadians(Double.parseDouble(currentInput))));
                	display.setText(currentInput);
                	return;
            	case "sqrt":
                	currentInput = String.valueOf(Math.sqrt(Double.parseDouble(currentInput)));
                	display.setText(currentInput);
                	return;
            	case "log":
                	currentInput = String.valueOf(Math.log10(Double.parseDouble(currentInput)));
                	display.setText(currentInput);
                	return;
            	case "exp":
                	currentInput = String.valueOf(Math.exp(Double.parseDouble(currentInput)));
                	display.setText(currentInput);
                	return;
            	case "pow":
                	currentInput += "^";
                	display.setText(currentInput);
                	return;
            	default:
                	if (newCalculation) {
                    	currentInput = "";
                    	newCalculation = false;
                	}
                	currentInput += command;
                	display.setText(currentInput);
        	}
    	} catch (Exception ex) {
        	display.setText("Error");
    	}
	}

	private String evaluateExpression(String expression) {
    	try {
        	return String.valueOf(evaluate(expression));
    	} catch (Exception e) {
        	return "Error";
    	}
	}

	private double evaluate(String expression) {
    	Stack<Double> numbers = new Stack<>();
    	Stack<Character> operations = new Stack<>();
    	int i = 0;
    	while (i < expression.length()) {
        	char c = expression.charAt(i);

        	if (Character.isDigit(c) || c == '.') {
            	StringBuilder num = new StringBuilder();
            	while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                	num.append(expression.charAt(i++));
            	}
            	numbers.push(Double.parseDouble(num.toString()));
            	i--;
        	} else if (c == '(') {
            	operations.push(c);
        	} else if (c == ')') {
            	while (!operations.isEmpty() && operations.peek() != '(') {
                	numbers.push(applyOperation(operations.pop(), numbers.pop(), numbers.pop()));
            	}
            	operations.pop();
        	} else if (isOperator(c)) {
            	while (!operations.isEmpty() && precedence(c) <= precedence(operations.peek())) {
                	numbers.push(applyOperation(operations.pop(), numbers.pop(), numbers.pop()));
            	}
            	operations.push(c);
        	}
        	i++;
    	}

    	while (!operations.isEmpty()) {
        	numbers.push(applyOperation(operations.pop(), numbers.pop(), numbers.pop()));
    	}

    	return numbers.pop();
	}

	private boolean isOperator(char c) {
    	return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '%';
	}

	private int precedence(char op) {
    	return switch (op) {
        	case '+', '-' -> 1;
        	case '*', '/' -> 2;
        	case '^' -> 3;
        	default -> 0;
    	};
	}

	private double applyOperation(char op, double b, double a) {
    	return switch (op) {
        	case '+' -> a + b;
        	case '-' -> a - b;
        	case '*' -> a * b;
        	case '/' -> a / b;
        	case '^' -> Math.pow(a, b);
        	case '%' -> a % b;
        	default -> 0;
    	};
	}

	public static void main(String[] args) {
    	new ScientificCalculator();
	}
}