import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Deck {
	
	public static final String PinochleDoubleDeck = "PinochleDoubleDeck";
	public static final String Standard = "Standard";
	public static final String StandardJokers = "StandardJokers";
	
	private LinkedList<String> deck;
	private LinkedList<String> originalDeck;
	private String deckType;
	public String getDeckType() {
		return deckType;
	}
	public LinkedList<String> getDeck(){
		return deck;
	}
	
	// constructors: 
	public Deck( String deckType ){
		this.deckType = deckType;
		setDeck(this.deckType);
		originalDeck = deck;
	}
	public Deck( String deckType, LinkedList<String> deck) {
		this.deckType = deckType;
		this.deck = deck;
		originalDeck = this.deck;
	}

	// public methods: 
	public void resetDeck() {
		if( originalDeck != null ) {
			deck = originalDeck;
			return;
		}
	}

	public void shuffle() { // This function is standardized for any deck with any number of cards > 1;
		if ( deck.size() <= 1 )return;
		Random randNum = new Random();
		int deckLength = deck.size();
		//shuffles can be shuffled over-handed(true) and bridged(false);
		for(int x=0;x<1000000;x++) { // shuffle 1000000 times;
			String deckTemp[] = new String[deckLength];
			boolean shuffleType = randNum.nextBoolean();
			if(shuffleType) { // over-hand shuffle;
				//pick two random numbers between 0 and deckLength;
				int rands[] = {randNum.nextInt(deckLength-1), randNum.nextInt(deckLength-1)};
				//if a number is 0, pick a new number; if the numbers are equal, pick a new number:
				do { 
					rands[1] = randNum.nextInt(deckLength-1);
					if(rands[0] == 0) rands[0] = randNum.nextInt(deckLength-1);
					if(rands[1] == 0) rands[1] = randNum.nextInt(deckLength-1);
				}while(rands[0] == rands[1] || rands[0] == 0 || rands[1] == 0);
				//sort low to high: 
				if(rands[0] > rands[1]) {
					int temp = rands[0];
					rands[0] = rands[1];
					rands[1] = temp;
				}
				//shuffle the cards together: top: range, top, bottom; bottom: top, bottom, range;
				boolean isTop = randNum.nextBoolean();
				int pos = 0;
				if(isTop) {
					for(int range = rands[0]; range < rands[1]; range++) {
						deckTemp[pos] = deck.get(range);
						pos+=1;
					}
					for(int top = 0;top < rands[0];top++) {
						deckTemp[pos] = deck.get(top);
						pos+=1;
					}
					for(int bottom = rands[1];bottom <deckLength;bottom++) {
						deckTemp[pos] = deck.get(bottom);
						pos+=1;
					}
				}else {
					for(int top = 0;top < rands[0];top++) {
						deckTemp[pos] = deck.get(top);
						pos+=1;
					}
					for(int bottom = rands[1];bottom <deckLength;bottom++) {
						deckTemp[pos] = deck.get(bottom);
						pos+=1;
					}
					for(int range = rands[0]; range < rands[1]; range++) {
						deckTemp[pos] = deck.get(range);
						pos+=1;
					}
				}
			}else { // bridge shuffle;
				//start left or right and get the starting position;
				boolean isLeft = randNum.nextBoolean();
				int randomPos = 0;
				do { 
					randomPos = randNum.nextInt(deckLength-1);
				}while(randomPos == 0);
				//initialize the string arrays for the split deck, and set the position: 
				String left[] = new String[deckLength];
				String right[] = new String[deckLength];
				int pos = deckLength-1;
				//set left and right side of split deck:
				for(int y=randomPos;y>=0;y--) {
					left[pos] = deck.get(y);
					pos-=1;
				}
				pos = deckLength-1;
				for(int y=deckLength-1;y>randomPos;y--) {
					right[pos] = deck.get(y);
					pos-=1;
				}
				//bridge shuffle the deck:
				pos = deckLength-1;
				for(int y=deckLength-1;y>=0;y-=1) {
					if(left[y] == null || right[y] == null) {
						boolean isLeftNull = (left[y] == null?true:false);
						int newPos = y;
						for(int z=pos;z>=0;z--) {
							deckTemp[z] = (isLeftNull?right[newPos]:left[newPos]);
							newPos-=1;
						}
						break;
					}
					deckTemp[pos] = (isLeft?left[y]:right[y]);
					deckTemp[pos-1] = (isLeft?right[y]:left[y]);
					pos-=2;
				}
			} // update the deck after a shuffle:
			deck = new LinkedList<String>(Arrays.asList(deckTemp));
		}
	}
	
	public String getNextCard() {
		if( deck.size() > 0 ) {
			String card = deck.get(0);
			deck.remove(0);
			return card;
		} return "";
	}
	
	public void printDeck() {
		System.out.println( "Deck " + deckType + " deck:\n\t" + Arrays.deepToString(deck.toArray()) );
	}
	
	// private methods: 
	private void setDeck(String deckType) {
		char Suit[];
		char Card[];
		switch(deckType) {
			case PinochleDoubleDeck:{
				deck = new LinkedList<String>();
				// Jack, Queen, King Ten, Ace of each suit: 
				Suit = new char[] {'C','D','H','S'};
				Card = new char[] {'J','Q','K','T','A'};
				for(int x=0;x<4;x++) // c,d,h,s;
					for(int y=0;y<20;y++)
						deck.add( String.valueOf(Card[y%5])+Suit[x] ); // j,q,k,t,a;
			}break;
			case StandardJokers:{
				deck = new LinkedList<String>();
				Suit = new char[] { 'C','D','H','S' };
				Card = new char[] { 'A','2','3','4','5','6','7','8','9','T','J','Q','K' };
				for(int x=0;x<4;x++) // c,d,h,s;
					for(int y=0;y<13;y++)
						deck.add( String.valueOf(Card[y%13])+Suit[x] ); // j,q,k,t,a;
				deck.add( "JK" );
				deck.add( "JK" );
			}break;
			case Standard: default:{
				deck = new LinkedList<String>();
				Suit = new char[] { 'C','D','H','S' };
				Card = new char[] { 'A','2','3','4','5','6','7','8','9','T','J','Q','K' };
				for(int x=0;x<4;x++) // c,d,h,s;
					for(int y=0;y<13;y++)
						deck.add( String.valueOf(Card[y%13])+Suit[x] ); // j,q,k,t,a;
				if( !deckType.equals(Standard) ) this.deckType = Standard;
			}break;
		}
	}
}