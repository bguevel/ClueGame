package clueGame;

public class Solution {
	private Card room;
	private Card weapon;
	private Card person;
	
	public Solution(Card room, Card person, Card weapon) {
		super();
		this.room = room;
		this.weapon = weapon;
		this.person = person;
	}
	
    public boolean equals(Solution o) {
		if(o.getRoom() == this.room && o.getWeapon() == this.weapon && o.getPerson() == this.person) {
			return true;
		}
		return false;
	}

	public Card getRoom() {
		return room;
	}

	public Card getWeapon() {
		return weapon;
	}

	public Card getPerson() {
		return person;
	}
	
}
