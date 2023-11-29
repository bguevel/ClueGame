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
	
	public Solution() {
		super();
	}

	@Override
    public boolean equals(Object o) {
		Solution soln =(Solution)o;
		if(soln.getRoom().equals(this.room) && soln.getWeapon().equals(this.weapon) && soln.getPerson().equals(this.person)) {
			return true;
		}
		return false;
	}

	public void setRoom(Card room) {
		this.room = room;
	}

	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}

	public void setPerson(Card person) {
		this.person = person;
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
