import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DecimalCalculator extends JFrame implements ActionListener {
	private JTextField display;
	private String operator;
	private double num1, num2, result;

	public DecimalCalculator() {
    	// Set up JFrame
    	setTitle("Decimal Calculator");
    	setSize(350, 400);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setLayout(new BorderLayout());

    	// Display field
    	display = new JTextField();
    	display.setEditable(false);
    	add(display, BorderLayout.NORTH);

    	// Button panel
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(4, 4));

    	String[] buttons = {"7", "8", "9", "/", "4", "5", "6", "*",
                        	"1", "2", "3", "-", "0", ".", "=", "+"};

    	for (String text : buttons) {
        	JButton button = new JButton(text);
        	button.addActionListener(this);
        	panel.add(button);
    	}

    	add(panel, BorderLayout.CENTER);
    	setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
    	String command = e.getActionCommand();

    	if ("0123456789.".contains(command)) {
        	display.setText(display.getText() + command);
    	} else if ("+-*/".contains(command)) {
        	num1 = Double.parseDouble(display.getText());
        	operator = command;
        	display.setText("");
    	} else if (command.equals("=")) {
        	num2 = Double.parseDouble(display.getText());
        	switch (operator) {
            	case "+": result = num1 + num2; break;
            	case "-": result = num1 - num2; break;
            	case "*": result = num1 * num2; break;
            	case "/": result = (num2 != 0) ? num1 / num2 : 0; break;
        	}
        	display.setText(String.valueOf(result));
    	}
	}

	public static void main(String[] args) {
    	new DecimalCalculator();
	}
}
