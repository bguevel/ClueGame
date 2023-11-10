package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel{
	private String turn ;
	private String guess;
	private String guessResult;
	private Integer roll = 0;
	JTextField turnText;
	JTextField guessResText;
	JTextField guessText;

	public GameControlPanel() {
		//create jpanel 2x0
//		JPanel outside = new JPanel();
		setLayout(new GridLayout(2, 0));
		
		//create a jpanel 1x4
		JPanel north = new JPanel();
		north.setLayout(new GridLayout(1, 4));
		north.add(turnPanel());
		north.add(rollPanel());
		JButton accusationB = new JButton("Make Accusation");
		JButton nextB = new JButton("NEXT!");
		north.add(accusationB);
		north.add(nextB);
		
		add(north, BorderLayout.NORTH);
		add(guessPanel(), BorderLayout.SOUTH);	
	}
	
	public JPanel guessPanel(){
		//jpanel 0x2
		JPanel overall = new JPanel();
		overall.setLayout(new GridLayout(0, 2));
		
		//jpanel 1x0 for guess
		JPanel guessP = new JPanel();
		guessP.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		guessText = new JTextField();
		guessP.add(guessText);
		
		//jpanel 1x0
		JPanel guessResP = new JPanel();
		guessResP.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		guessResText = new JTextField();
		guessResP.add(guessResText);
		
		overall.add(guessP);
		overall.add(guessResP);
		return overall;
	}
	
	public JPanel turnPanel() {
		JPanel turnP = new JPanel();
		JLabel turnL = new JLabel("Whose turn?");
		turnText = new JTextField(turn);
		turnP.add(turnL);
		turnP.add(turnText);
		
		return turnP;
	}
	
	public JPanel rollPanel() {
		JPanel rollP = new JPanel();
		JLabel rollL = new JLabel("Roll:");
		JTextField rollText = new JTextField(roll.toString());
		rollP.add(rollL);
		rollP.add(rollText);
		return rollP;
	}
	
	public void setTurn(Player turn) {
//		this.turn = turn.getName();
		turnText.setText(turn.getName());
		switch (turn.getColor()) {
		case "Blue":
			turnText.setBackground(Color.BLUE);
			turnText.setForeground(Color.WHITE);
			break;
		case "Yellow":
			turnText.setBackground(Color.YELLOW);
			break;
		case "Green":
			turnText.setBackground(Color.GREEN);
			break;
		case "Red":
			turnText.setBackground(Color.RED);
			break;
		case "Pink":
			turnText.setBackground(Color.PINK);
			break;
		case "Orange":
			turnText.setBackground(Color.ORANGE);
			break;
		}
			
	}
	
	public void setGuess(String guess) {
		this.guessText.setText(guess);
	}
	
	public void setGuessResult(String guessResult) {
		this.guessResText.setText(guessResult);
	}
	
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();
		JFrame frame = new JFrame();
		Player testP = new ComputerPlayer("Prof CPW", "Blue", 0, 15, false);
		panel.setTurn(testP);
		frame.setContentPane(panel);
		frame.setSize(750, 180);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		System.out.print(testP.getName());
		panel.setGuess("I have no guess");
		panel.setGuessResult("So you have nothing?");
	}
	
}
