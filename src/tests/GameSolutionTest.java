package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Solution;

public class GameSolutionTest {
	private static Board board;
	private static Card marquezCard, ctlmCard, brownCard, kafadarCard, strongCard, cpwCard, kellyCard, swansonCard, bridgemanCard, canCard, catapultCard, flamethrowerCard, legoCard, chairCard;


	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();

		//create card constants to test
		marquezCard = new Card("Marquez", CardType.ROOM);
		ctlmCard = new Card("CTLM", CardType.ROOM);
		brownCard = new Card("Brown", CardType.ROOM);
		kafadarCard = new Card("Kafadar", CardType.ROOM);
		strongCard = new Card("Prof Strong", CardType.PERSON);
		cpwCard = new Card("Prof CPW", CardType.PERSON);
		kellyCard = new Card("Prof Kelly", CardType.PERSON);
		swansonCard = new Card("Prof Swanson", CardType.PERSON);
		bridgemanCard = new Card("Prof Bridgeman", CardType.PERSON);
		canCard = new Card("Prof Can", CardType.PERSON);
		catapultCard = new Card("Catapult", CardType.WEAPON);
		flamethrowerCard = new Card("Flamethrower", CardType.WEAPON);
		legoCard = new Card("Lego", CardType.WEAPON);
		chairCard = new Card("Chair", CardType.WEAPON);
	}

	@Test
	public void testCheckAccusation() {
		board.setTheAnswer(marquezCard, strongCard, legoCard);
		//correct answer
		assertTrue(board.checkAccusation(new Solution(marquezCard, strongCard, legoCard)));
		//wrong room
		assertFalse(board.checkAccusation(new Solution(ctlmCard, strongCard, legoCard)));
		//wrong person
		assertFalse(board.checkAccusation(new Solution(marquezCard, kellyCard, legoCard)));
		//wrong weapon
		assertFalse(board.checkAccusation(new Solution(marquezCard, strongCard, flamethrowerCard)));
	}

	@Test
	public void testDisproveSuggestion() {
		Solution suggestion = new Solution(marquezCard, strongCard, legoCard);

		//player has 1 matching card
		board.getPlayer("Prof Strong").setHand(marquezCard, cpwCard, flamethrowerCard);
		assertEquals(marquezCard, board.getPlayer("Prof Strong").disproveSuggestion(suggestion));

		//player has no matching cards
		board.getPlayer("Prof Strong").setHand(ctlmCard, kellyCard, brownCard);
		assertEquals(null, board.getPlayer("Prof Strong").disproveSuggestion(suggestion));

		//player has more than 1 (2) matching cards
		board.getPlayer("Prof Strong").setHand(marquezCard, strongCard, flamethrowerCard);
		ArrayList<Card> testList = new ArrayList<Card>();
		while(testList.size() != 2) {
			Card returnedCard = board.getPlayer("Prof Strong").disproveSuggestion(suggestion);
			if(!testList.contains(returnedCard)) {
				testList.add(returnedCard);
			}
		}
		assertTrue(testList.contains(marquezCard));
		assertTrue(testList.contains(strongCard));
	}

	@Test
	public void testHandleSuggestion() {
		//setting these three hands to test
		board.getPlayer("Prof Strong").setHand(ctlmCard, strongCard, flamethrowerCard);
		board.getPlayer("Prof CPW").setHand(brownCard, kellyCard, catapultCard);
		board.getPlayer("Prof Kelly").setHand(kafadarCard, swansonCard, chairCard);
		//setting the rest of their hands to be the same so they don't affect the test from deal being called
		board.getPlayer("Prof Swanson").setHand(brownCard, canCard, chairCard);
		board.getPlayer("Prof Bridgeman").setHand(brownCard, canCard, chairCard);
		board.getPlayer("Prof Can").setHand(brownCard, canCard, chairCard);

		//suggestion no one can disprove return null
		Solution suggestion = new Solution(marquezCard, cpwCard, legoCard); 
		assertEquals(null, board.handleSuggestion(suggestion, "Prof Strong"));
		//suggestion only human player can disprove return null
		suggestion = new Solution(ctlmCard, cpwCard, legoCard); //only the human player has ctml
		assertEquals(ctlmCard, board.handleSuggestion(suggestion, "Prof CPW"));
		//suggestion only suggesting player can disprove return card found
		suggestion = new Solution(marquezCard, kellyCard, legoCard); //only cpw has kelly
		assertEquals(kellyCard, board.handleSuggestion(suggestion, "Prof CPW"));
		//suggestion two players can disprove returns first card found
		suggestion = new Solution(brownCard, swansonCard, legoCard); //cpw has brown and kelly has swanson
		assertEquals(brownCard, board.handleSuggestion(suggestion, "Prof Strong"));
	}
}
