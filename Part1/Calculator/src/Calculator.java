import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class Calculator extends JFrame implements KeyListener, ActionListener {
	private JTextField num1; // the first num
	private JTextField num2; // the second num
	private JTextField operator; // the operator
	private JTextField result; // the result
	private JButton[][] buttons; // all the buttons
	private JMenuItem help; // help menu
	
	public Calculator()
	{
		Container container=getContentPane();
		container.setLayout(new GridLayout(3, 1)); // set 3*1 big grid
		
		//set menu
		JMenuBar menuBar = new JMenuBar(); 
		JMenu menu = new JMenu("Menu");
		menu.setMnemonic('M');
		help = new JMenuItem("Help");
		help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
		help.addActionListener(this);
		menuBar.add(menu);
		menu.add(help);
		container.add(menuBar);
		
		// set first line
		JPanel firstLine = new JPanel();
		firstLine.setLayout(new GridLayout(1, 5, 5, 5));
		container.add(firstLine);

		// set second line
		JPanel secondLine = new JPanel();
		secondLine.setLayout(new GridLayout(1, 5, 5, 5));
		container.add(secondLine);
		
		// set num1
		num1 = new JTextField("12");
		num1.setEditable(true);
		num1.setHorizontalAlignment(JTextField.CENTER);
		firstLine.add(num1);			
		
		// set operator
		operator = new JTextField("");
		operator.setEditable(false);
		operator.setHorizontalAlignment(JTextField.CENTER);
		firstLine.add(operator);
		
		// set num2
		num2 = new JTextField("2");
		num2.setEditable(true);
		num2.setHorizontalAlignment(JTextField.CENTER);
		firstLine.add(num2);
		
		// set equal
		JTextField equal = new JTextField("=");
		equal.setEditable(false);
		equal.setHorizontalAlignment(JTextField.CENTER);
		firstLine.add(equal);
		
		// set result
		result = new JTextField("");
		result.setEditable(false);
		result.setHorizontalAlignment(JTextField.CENTER);
		firstLine.add(result);
				
		// set buttons
		buttons = new JButton[1][5];
		buttons[0][0] = new JButton("+");
		buttons[0][1] = new JButton("-");
		buttons[0][2] = new JButton("*");
		buttons[0][3] = new JButton("/");
		buttons[0][4] = new JButton("OK");

		for(int j = 0; j < 5; j++)
		{
			secondLine.add(buttons[0][j]);
			buttons[0][j].addActionListener(this);
			buttons[0][j].addKeyListener(this);
		}			

		this.setFocusable(true);
		this.addKeyListener(this);
	}

	public static void main(String[] args)
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		Calculator mycal = new Calculator();
		mycal.setTitle("Calculator");
		mycal.setVisible(true);
		mycal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mycal.pack();
	}
	
	// set text of the operator
	public void funcOperator(String op)
	{
		operator.setText(op);
	}
	
	// compute and set text
	private void funcOk()
	{	
		String res = "";
		
		String op = operator.getText();
		if (op.equals("")) {
			return;
		}
		int n1 = Integer.parseInt(num1.getText());
		int n2 = Integer.parseInt(num2.getText());
		int ans = 0;
		if (op.equals("+")) {
			ans = n1 + n2;
		}
		else if (op.equals("-")) {
			ans = n1 - n2;
		}
		else if (op.equals("*")) {
			ans = n1 * n2;
		}
		else if (n2 != 0) {
			ans = n1 / n2;
		}
		else {
			res = "Invalid";
		}
		if (!res.equals("Invalid")) {			
			res = String.valueOf(ans);
		}			
		result.setText(res);
	}
	
	// set content of the help menu
	private void funcHelp()
	{
		JOptionPane.showMessageDialog(this,
				"can use both mouse or keyboard\n" + "add: a or +\n" + "minus: s or -\n" +
		"multiply: d or *\n" + "divide: f or /\n" + "ok: g or =\n" + "help: Ctrl+h", "Tips", JOptionPane.INFORMATION_MESSAGE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// respond to GUI click
		if (e.getSource() == buttons[0][0]) {
			funcOperator("+");	
		}
		else if (e.getSource() == buttons[0][1]) {
			funcOperator("-");	
		}
		else if (e.getSource() == buttons[0][2]) {
			funcOperator("*");		
		}
		else if (e.getSource() == buttons[0][3]) {
			funcOperator("/");		
		}
		else if (e.getSource() == buttons[0][4]) {
			funcOk();
		}
		else if (e.getSource() == help) {
			funcHelp();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// respond to keyboard press
		if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_EQUALS) {
			funcOperator("+");
		}
		else if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_MINUS) {
			funcOperator("-");		
		}
		else if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_MULTIPLY) {
			funcOperator("*");		
		}
		else if(e.getKeyCode() == KeyEvent.VK_F || e.getKeyCode() == KeyEvent.VK_SLASH) {
			funcOperator("/");		
		}
		else if(e.getKeyCode() == KeyEvent.VK_G || e.getKeyCode() == KeyEvent.VK_ENTER) {
			funcOk();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

