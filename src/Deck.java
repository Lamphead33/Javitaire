import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<Card>();
    private String[] suits = new String[]{"Hearts", "Diamonds", "Spades", "Clubs"};
    
    // Deck is populated upon creation
    public Deck() {
        // Generates the deck -
        for (String currentSuit : suits) {
            for (int i = 1; i < 14; i++ ) {
                Card c = new Card(i, currentSuit);
                deck.add(c);
            }
        }
        // Shuffle the deck
        Collections.shuffle(deck);
    }
    
    
    // Prints the deck (testing purposes)
    public void printDeck() {
        for (Card c: this.deck) {
            System.out.println(c.getCard());
        }
        
    }
    
    public ArrayList<Card> getDeck(){
        return deck;
    }

}
