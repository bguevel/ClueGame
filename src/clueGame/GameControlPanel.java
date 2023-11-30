//Makes the panel at the bottom that controls the flow of the game

package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel{
	private String turn = "None";
	private Integer roll = 0;
	private static JTextField turnText;
	private static JTextField guessResText;
	private static JTextField guessText;
	private JButton nextB;
	private JButton accusationB;
	private static JTextField rollText;
	private Board board;
	private JPanel turnP;
	private JButton makeAccusationn;
	private JComboBox<String> players;
	private JComboBox<String> weapons;
	private JComboBox<String> rooms;
	private JDialog accuse;


	public GameControlPanel(Board board) {
		this.board=board;
		//create jpanel 2x0
		//		JPanel outside = new JPanel();
		setLayout(new GridLayout(2, 0));

		//create a jpanel 1x4
		JPanel north = new JPanel();
		north.setLayout(new GridLayout(1, 4));
		north.add(turnPanel());
		north.add(rollPanel());
		accusationB = new JButton("Make Accusation");
		nextB = new JButton("NEXT!");
		north.add(accusationB);
		north.add(nextB);
		ButtonListener button = new ButtonListener();
		nextB.addActionListener(button);
		accusationB.addActionListener(button);
		add(north, BorderLayout.NORTH);
		add(guessPanel(), BorderLayout.SOUTH);	
	}

	private class AccuseButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == makeAccusationn) {
				accuse.dispose();
				String susRoom = rooms.getSelectedItem().toString();
				String susPlayer = players.getSelectedItem().toString();
				String susWeapon = weapons.getSelectedItem().toString();

				Solution accusation = new Solution();

				for(Card c: Board.getDeck()){
					if(c.getCardName() == susRoom) {
						accusation.setRoom(c);
					} else if(c.getCardName() == susPlayer) {
						accusation.setPerson(c);
					} else if (c.getCardName() == susWeapon) {
						accusation.setWeapon(c);
					}
				}

				if(Board.checkAccusation(accusation)) {
					JOptionPane.showMessageDialog(null, "That is correct! Good job");
				} else {
					JOptionPane.showMessageDialog(null, "That was not correct. You lose");
				}
				System.exit(0);

			}

		}

	}

	private class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==nextB) {
				board.nextPlayer();
			}
			if(e.getSource()==accusationB) {
				if(board.getPlayerList().get(board.getTurn()%6).getIsHuman()) {
					// display a selection screen
					players = new JComboBox<String>();
					weapons = new JComboBox<String>();
					rooms = new JComboBox<String>();

					for(Player p: board.getPlayerList()) {
						players.addItem(p.getName());
					}

					for(Card w: Board.getDeck()) {
						if(w.getType() == CardType.WEAPON) {
							weapons.addItem(w.getCardName());
						}
					}

					for(Card r: Board.getDeck()) {
						if(r.getType() == CardType.ROOM) {
							rooms.addItem(r.getCardName());
						}
					}

					boolean done = false;
					accuse = new JDialog(board.getGame());
					accuse.setLayout(new GridLayout(0, 1));
					accuse.setSize(200, 300);
					accuse.add(rooms);
					accuse.add(players);
					accuse.add(weapons);


					AccuseButton listen = new AccuseButton();
					makeAccusationn = new JButton("Accuse them");
					makeAccusationn.addActionListener(listen);
					accuse.add(makeAccusationn);
					accuse.setVisible(true);

					//when button pressed, done = true, accuse.dispose();

					String susRoom = rooms.getSelectedItem().toString();
					String susPlayer = players.getSelectedItem().toString();
					String susWeapon = weapons.getSelectedItem().toString();

					Solution accusation = new Solution();

					for(Card c: Board.getDeck()){
						if(c.getCardName() == susRoom) {
							accusation.setRoom(c);
						} else if(c.getCardName() == susPlayer) {
							accusation.setPerson(c);
						} else if (c.getCardName() == susWeapon) {
							accusation.setWeapon(c);
						}
					}

					if(done == true) {
						if(Board.checkAccusation(accusation)) {
							JOptionPane.showMessageDialog(null, "That is correct! Good job");
						} else {
							JOptionPane.showMessageDialog(null, "That was not correct. You lose");
						}
						System.exit(0);
					}
				}
			}
		}

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
		this.turnP = new JPanel();
		JLabel turnL = new JLabel("Whose turn?");
		this.turnText = new JTextField(turn, 15);
		this.turnP.add(turnL);
		this.turnP.add(turnText);
		return this.turnP;
	}

	public JPanel rollPanel() {
		JPanel rollP = new JPanel();
		JLabel rollL = new JLabel("Roll:");
		rollText = new JTextField(roll.toString());
		rollP.add(rollL);
		rollP.add(rollText);
		return rollP;
	}

	public void setTurn(String name, String color) {
		//		this.turn = turn.getName();
		this.turnText.removeAll();
		this.turnText.setText(name);
		switch (color) {
		case "Blue":
			turnText.setBackground(Color.BLUE);
			turnText.setForeground(Color.WHITE);
			break;
		case "Yellow":
			turnText.setBackground(Color.YELLOW);
			turnText.setForeground(Color.BLACK);
			break;
		case "Green":
			turnText.setBackground(Color.GREEN);
			turnText.setForeground(Color.BLACK);
			break;
		case "Red":
			turnText.setBackground(Color.RED);
			turnText.setForeground(Color.BLACK);
			break;
		case "Pink":
			turnText.setBackground(Color.PINK);
			turnText.setForeground(Color.BLACK);
			break;
		case "Orange":
			turnText.setBackground(Color.ORANGE);
			turnText.setForeground(Color.BLACK);
			break;
		}

	}

	public static void setGuess(String guess) {
		GameControlPanel.guessText.setText(guess);
	}

	public static void setGuessResult(String guessResult) {
		guessResText.setText(guessResult);
	}
	public static void setRoll(int roll) {
		Integer i = roll;
		rollText.setText(i.toString());

	}

	public static void main(String[] args) {
		//GameControlPanel panel = new GameControlPanel(Board b);
		JFrame frame = new JFrame();
		Player testP = new ComputerPlayer("Prof CPW", "Blue", 0, 15, false);
		//panel.setTurn(testP);
		//frame.setContentPane(panel);
		frame.setSize(750, 180);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		System.out.print(testP.getName());
		//panel.setGuess("I have no guess");
		//panel.setGuessResult("So you have nothing?");
	}


}
