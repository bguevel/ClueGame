package clueGame;

import java.awt.Color;
import java.util.function.BooleanSupplier;

public class HumanPlayer extends Player {
	
	public HumanPlayer(String name, String c, int row, int column, boolean isHuman) {
		super(name, c, row, column, isHuman);
	}

	@Override
	protected BoardCell selectTargets() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
