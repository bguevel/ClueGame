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
	public void updateCards(ArrayList<Card> hand, ArrayList<Card> seen) {
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
		ArrayList<Card> seen = new ArrayList<Card>();
		ArrayList<Card> hand = new ArrayList<Card>();
		seen.add(new Card("Prof CPW", CardType.PERSON));
		hand.add(new Card("Flamethrower", CardType.WEAPON));
		CardDisplay panel = new CardDisplay();
		panel.updateCards(hand, seen);
		JFrame frame = new JFrame();
		frame.setContentPane(panel);
		frame.setSize(180, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
	
}