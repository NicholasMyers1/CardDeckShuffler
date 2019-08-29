
import java.util.Arrays;
import java.util.LinkedList;

public class MainMethod {
	
	public static void main(String[] args) {

		// standard deck: 
		Deck standard = new Deck(Deck.Standard);
		standard.shuffle();
		standard.printDeck();
		
//		// standard deck with jokers: 
//		Deck standardJokers = new Deck(Deck.StandardJokers);
//		standardJokers.shuffle();
//		standardJokers.printDeck();
//		
//		// pinochle double deck: 
//		Deck pinochleDoubleDeck = new Deck(Deck.PinochleDoubleDeck);
//		pinochleDoubleDeck.shuffle();
//		pinochleDoubleDeck.printDeck();

		// custom deck: 
		Deck custom = new Deck("CustomDeckTest", new LinkedList<String>( Arrays.asList( "JK", "JK", "AC", "AD", "AH", "AS" ) ) );
		custom.shuffle();
		custom.printDeck();
		while( custom.getDeck().size()>0 )
			System.out.println( "\tGet next card in " + custom.getDeckType() + ": [" + custom.getNextCard() + "]; Cards left in this deck: " + custom.getDeck().size() );
		custom.resetDeck();
		custom.printDeck();
	}
}