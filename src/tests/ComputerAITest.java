package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Solution;

public class ComputerAITest {
	private static Board board;
	private static Card marquezCard, ctlmCard, brownCard, strongCard, cpwCard, swansonCard, bridgemanCard, canCard, kellyCard, catapultCard, flamethrowerCard, legoCard, butterknifeCard, textbookCard, chairCard;


	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();

		//create card constants to test, 3 of each card type to start
		marquezCard = new Card("Marquez", CardType.ROOM);
		ctlmCard = new Card("CTLM", CardType.ROOM);
		brownCard = new Card("Brown", CardType.ROOM);
		strongCard = new Card("Prof Strong", CardType.PERSON);
		cpwCard = new Card("Prof CPW", CardType.PERSON);
		kellyCard = new Card("Prof Kelly", CardType.PERSON);
		swansonCard = new Card("Prof Swanson", CardType.PERSON);
		bridgemanCard = new Card("Prof Bridgeman", CardType.PERSON);
		canCard = new Card("Prof Can", CardType.PERSON);
		catapultCard = new Card("Catapult", CardType.WEAPON);
		flamethrowerCard = new Card("Flamethrower", CardType.WEAPON);
		legoCard = new Card("Lego", CardType.WEAPON);
		butterknifeCard = new Card("Butterknife", CardType.WEAPON);
		chairCard = new Card("Chair", CardType.WEAPON);
		textbookCard = new Card("Textbook", CardType.WEAPON);
	}
	
	@Test
	public void testSelectTargets() {
		//test room has not been seen
		board.getPlayer("Prof CPW").setLocation(board.getCell(5,  3));
		board.calcTargets(board.getCell(5, 3), 3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(board.getCell(3, 4), board.getPlayer("Prof CPW").selectTargets(targets));
		
		//test room that has been seen
		board.getPlayer("Prof CPW").updateSeen(ctlmCard);
		board.calcTargets(board.getCell(5, 3), 3);
		targets = board.getTargets();
		
		ArrayList<BoardCell> testList = new ArrayList<BoardCell>();
		while(testList.size() != 7) {
			BoardCell returnedCell = board.getPlayer("Prof CPW").selectTargets(targets);
			if(!testList.contains(returnedCell)) {
				testList.add(returnedCell);
			}
		}
		assertTrue(testList.contains(board.getCell(5, 2)));
		assertTrue(testList.contains(board.getCell(6, 3)));
		assertTrue(testList.contains(board.getCell(5, 6)));
		assertTrue(testList.contains(board.getCell(5, 0)));
		assertTrue(testList.contains(board.getCell(6, 5)));
		assertTrue(testList.contains(board.getCell(5, 4)));
		assertTrue(testList.contains(board.getCell(6, 1)));
		assertFalse(testList.contains(board.getCell(3, 4)));
		
		//test no room in list
		board.getPlayer("Prof CPW").setLocation(board.getCell(0, 0));
		board.calcTargets(board.getCell(5, 3), 3);
		targets = board.getTargets();
		testList.clear();
		while(testList.size() != 2) {
			BoardCell returnedCell = board.getPlayer("Prof CPW").selectTargets(targets);
			if(!testList.contains(returnedCell)) {
				testList.add(returnedCell);
			}
		}
		assertTrue(testList.contains(board.getCell(3, 0)));
		assertTrue(testList.contains(board.getCell(0, 3)));
		
	}
	
	@Test
	public void testCreateSuggestion() {
		board.getPlayer("Prof CPW").updateSeen(strongCard);
		board.getPlayer("Prof CPW").updateSeen(cpwCard);
		board.getPlayer("Prof CPW").updateSeen(kellyCard);
		board.getPlayer("Prof CPW").updateSeen(swansonCard);
		board.getPlayer("Prof CPW").updateSeen(catapultCard);
		board.getPlayer("Prof CPW").updateSeen(flamethrowerCard);
		board.getPlayer("Prof CPW").updateSeen(butterknifeCard);
		board.getPlayer("Prof CPW").updateSeen(legoCard);
		
//		ArrayList<Solution> testList = new ArrayList<Solution>();
//		while(testList.size() != 4) {
//			Solution returnedSol = board.getPlayer("Prof CPW").createSuggestion(ctlmCard);
//			if(!testList.contains(returnedSol)) {
//				testList.add(returnedSol);
//			}
//		}
//		assertTrue(testList.contains(new Solution(ctlmCard, bridgemanCard, textbookCard)));
//		assertTrue(testList.contains(new Solution(ctlmCard, bridgemanCard, chairCard)));
//		assertTrue(testList.contains(new Solution(ctlmCard, canCard, textbookCard)));
//		assertTrue(testList.contains(new Solution(ctlmCard, canCard, chairCard)));
		
		board.getPlayer("Prof CPW").updateSeen(bridgemanCard);
		board.getPlayer("Prof CPW").updateHand(chairCard);
		
		assertEquals(new Solution(ctlmCard, canCard, textbookCard), board.getPlayer("Prof CPW").createSuggestion(ctlmCard));
		
		
	}
}
