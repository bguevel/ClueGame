package clueGame;

import java.awt.Color;
import java.util.function.BooleanSupplier;

public class HumanPlayer extends Player {
	
	public HumanPlayer(String name, Color c, int row, int column) {
		super(name, c, row, column);
	}

	@Override
	public BooleanSupplier isHuman() {
		// TODO Auto-generated method stub
		return null;
	}
}