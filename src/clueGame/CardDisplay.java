package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
public class CardDisplay extends JPanel{
	private JPanel people = new JPanel();
	private JPanel rooms = new JPanel();
	private JPanel weapons = new JPanel();
	public CardDisplay() {
		setLayout(new GridLayout(3, 0));
		setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));
		people.setLayout(new GridLayout(0, 1));
		rooms.setLayout(new GridLayout(0, 1));
		weapons.setLayout(new GridLayout(0, 1));
		add(people);
		add(rooms);
		add(weapons);
		people.add(new JLabel("in hand:"));
		rooms.add(new JLabel("in hand:"));
		weapons.add(new JLabel("in hand:"));
		people.add(new JTextField("None"));
		rooms.add(new JTextField("None"));
		weapons.add(new JTextField("None"));
		people.add(new JLabel("in seen:"));
		rooms.add(new JLabel("in seen:"));
		weapons.add(new JLabel("in seen:"));
		people.add(new JTextField("None"));
		rooms.add(new JTextField("None"));
		weapons.add(new JTextField("None"));
		
	}
	public void updateCards() {
		Board.getPlayerList();
		ArrayList<Card> hand = new ArrayList<Card>();
		ArrayList<Card> seen = new ArrayList<Card>();
		for(Player p:Board.getPlayerList()) {
			if(p.getIsHuman()) {
				hand = p.getHand();
				seen = p.getSeen();
			}
		}
		int personCt=0;
		int roomCt =0;
		int weaponCt =0;
		if(hand.size()<1 && seen.size()<1) {
			people.removeAll();
			rooms.removeAll();
			weapons.removeAll();
			people.add(new JLabel("in hand:"));
			rooms.add(new JLabel("in hand:"));
			weapons.add(new JLabel("in hand:"));
			people.add(new JTextField("None"));
			rooms.add(new JTextField("None"));
			weapons.add(new JTextField("None"));
			people.add(new JLabel("in seen:"));
			rooms.add(new JLabel("in seen:"));
			weapons.add(new JLabel("in seen:"));
			people.add(new JTextField("None"));
			rooms.add(new JTextField("None"));
			weapons.add(new JTextField("None"));
		}else if(hand.size()<1 && seen.size()>=1) {
			JTextField card;
			people.removeAll();
			rooms.removeAll();
			weapons.removeAll();
			people.add(new JLabel("in hand:"));
			rooms.add(new JLabel("in hand:"));
			weapons.add(new JLabel("in hand:"));
			people.add(new JTextField("None"));
			rooms.add(new JTextField("None"));
			weapons.add(new JTextField("None"));
			for(Card c: seen) {
				card = new JTextField(c.getCardName());
				if(c.getType()==CardType.PERSON) {
					personCt++;
					people.add(card);
				}
				if(c.getType()==CardType.ROOM) {
					roomCt++;
					rooms.add(card);
				}
				if(c.getType()==CardType.WEAPON) {
					weaponCt++;
					weapons.add(card);
				}
			}
			if(personCt == 0) {
				people.add(new JTextField("None"));
			}
			if(roomCt == 0) {
				rooms.add(new JTextField("None"));
			}
			if(weaponCt ==0) {
				weapons.add(new JTextField("None"));
			}
		}else if(hand.size()>=1 && seen.size()<1) {
			JTextField card;
			people.removeAll();
			rooms.removeAll();
			weapons.removeAll();
			people.add(new JLabel("in hand:"));
			rooms.add(new JLabel("in hand:"));
			weapons.add(new JLabel("in hand:"));
			
			for(Card c: hand) {
				card = new JTextField(c.getCardName());
				if(c.getType()==CardType.PERSON) {
					personCt++;
					people.add(card);
				}
				if(c.getType()==CardType.ROOM) {
					rooms.add(card);
					roomCt++;
				}
				if(c.getType()==CardType.WEAPON) {
					weaponCt++;
					weapons.add(card);
				}
			}
			if(personCt == 0) {
				people.add(new JTextField("None"));
			}
			if(roomCt == 0) {
				rooms.add(new JTextField("None"));
			}
			if(weaponCt ==0) {
				weapons.add(new JTextField("None"));
			}
			people.add(new JLabel("in seen:"));
			rooms.add(new JLabel("in seen:"));
			weapons.add(new JLabel("in seen:"));
			people.add(new JTextField("None"));
			rooms.add(new JTextField("None"));
			weapons.add(new JTextField("None"));
			
		}else {
			JTextField card;
			people.removeAll();
			rooms.removeAll();
			weapons.removeAll();
			people.add(new JLabel("in hand:"));
			rooms.add(new JLabel("in hand:"));
			weapons.add(new JLabel("in hand:"));
			
			for(Card c: hand) {
				card = new JTextField(c.getCardName());
				if(c.getType()==CardType.PERSON) {
					personCt++;
					people.add(card);
				}
				if(c.getType()==CardType.ROOM) {
					roomCt++;
					rooms.add(card);
				}
				if(c.getType()==CardType.WEAPON) {
					weaponCt++;
					weapons.add(card);
				}
			}
			if(personCt == 0) {
				people.add(new JTextField("None"));
			}
			if(roomCt == 0) {
				rooms.add(new JTextField("None"));
			}
			if(weaponCt ==0) {
				weapons.add(new JTextField("None"));
			}
			personCt =0;
			roomCt =0;
			weaponCt=0;
			people.add(new JLabel("in seen:"));
			rooms.add(new JLabel("in seen:"));
			weapons.add(new JLabel("in seen:"));
			for(Card c: seen) {
				card = new JTextField(c.getCardName());
				if(c.getType()==CardType.PERSON) {
					personCt++;
					people.add(card);
				}
				if(c.getType()==CardType.ROOM) {
					roomCt++;
					rooms.add(card);
				}
				if(c.getType()==CardType.WEAPON) {
					weaponCt++;
					weapons.add(card);
				}
			}
			if(personCt == 0) {
				people.add(new JTextField("None"));
			}
			if(roomCt == 0) {
				rooms.add(new JTextField("None"));
			}
			if(weaponCt ==0) {
				weapons.add(new JTextField("None"));
			}
		}
		

	}
	public static void main(String[] args) {
		// Board is singleton, get the only instance
		Board board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();

		//create card constants to test, 3 of each card type to start
		Card marquezCard = new Card("Marquez", CardType.ROOM);
		Card ctlmCard = new Card("CTLM", CardType.ROOM);
		Card brownCard = new Card("Brown", CardType.ROOM);
		Card strongCard = new Card("Prof Strong", CardType.PERSON);
		Card cpwCard = new Card("Prof CPW", CardType.PERSON);
		Card kellyCard = new Card("Prof Kelly", CardType.PERSON);
		Card swansonCard = new Card("Prof Swanson", CardType.PERSON);
		Card bridgemanCard = new Card("Prof Bridgeman", CardType.PERSON);
		Card canCard = new Card("Prof Can", CardType.PERSON);
		Card catapultCard = new Card("Catapult", CardType.WEAPON);
		Card flamethrowerCard = new Card("Flamethrower", CardType.WEAPON);
		Card legoCard = new Card("Lego", CardType.WEAPON);
		Card butterknifeCard = new Card("Butterknife", CardType.WEAPON);
		Card chairCard = new Card("Chair", CardType.WEAPON);
		Card textbookCard = new Card("Textbook", CardType.WEAPON);
		CardDisplay panel = new CardDisplay();
		panel.updateCards();
		Player human = null;
		for(Player p :Board.getPlayerList()) {
			if(p.getIsHuman()) {
				human=p;
				break;
			}
		}
		human.updateSeen(textbookCard);
		human.updateHand(chairCard);
		human.updateHand(kellyCard);
		panel.updateCards();
		JFrame frame = new JFrame();
		frame.setContentPane(panel);
		frame.setSize(180, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
	
}