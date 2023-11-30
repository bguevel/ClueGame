package clueGame;

import java.awt.Color;
import java.util.Set;
import java.util.function.BooleanSupplier;

public class HumanPlayer extends Player {
	
	public HumanPlayer(String name, String c, int row, int column, boolean isHuman) {
		super(name, c, row, column, isHuman);
	}

	@Override
	public BoardCell selectTargets(Set<BoardCell> targets) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Solution createSuggestion(Card ctlmCard) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void makeMove() {
		
	}

	@Override
	protected void makeAccusation() {
		// TODO Auto-generated method stub
		
	}

}
