package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	
	public Card(String name, CardType type) {
		this.cardName=name;
		this.type=type;
	}
	
	public CardType getType() {
		return this.type;
	}

	public String getCardName() {
		return cardName;
	}
	
}
