package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameControlPanel extends JPanel{
	private String turn;
	private String guess;
	private String guessResult;
	private Integer roll = 0;
	

	public GameControlPanel() {
		//create jpanel 2x0
		JPanel outside = new JPanel();
		outside.setLayout(new GridLayout(2, 0));
		
		//create a jpanel 1x4
		JPanel north = new JPanel();
		north.setLayout(new GridLayout(1, 4));
		north.add(turnPanel());
		north.add(rollPanel());
		JButton accusationB = new JButton("Make Accusation");
		JButton nextB = new JButton("NEXT!");
		north.add(accusationB);
		north.add(nextB);
		
		outside.add(north, BorderLayout.NORTH);
		outside.add(guessPanel(), BorderLayout.SOUTH);	
	}
	
	public JPanel guessPanel(){
		//jpanel 0x2
		JPanel overall = new JPanel();
		overall.setLayout(new GridLayout(0, 2));
		
		//jpanel 1x0 for guess
		JPanel guessP = new JPanel();
		JTextField guessText = new JTextField(guess);
		guessP.add(guessText);
		
		//jpanel 1x0
		JPanel guessResP = new JPanel();
		JTextField guessResText = new JTextField(guessResult);
		guessP.add(guessResText);
		
		overall.add(guessP);
		overall.add(guessResP);
		return overall;
	}
	
	public JPanel turnPanel() {
		JPanel turnP = new JPanel();
		JLabel turnL = new JLabel("Whose turn?");
		JTextField turnText = new JTextField(turn);
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
		this.turn = turn.getName();
	}
	
	public void setGuess(String guess) {
		this.guess = guess;
	}
	
	public void setGuessResult(String guessResult) {
		this.guessResult = guessResult;
	}
	
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();
		JFrame frame = new JFrame();
		frame.setContentPane(panel);
		frame.setSize(750, 180);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		panel.setTurn(new ComputerPlayer("Prof CPW", "Blue", 0, 15, false));
		panel.setGuess("I have no guess");
		panel.setGuessResult("So you have nothing?");
	}
	
}
