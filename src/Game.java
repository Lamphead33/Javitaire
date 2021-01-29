import java.util.ArrayList;

public class Game {
    
    //public boolean aCardIsSelected; // Used to track if a click is selecting a card or moving it
    public Card selectedCard; // Tracks which card is selected
    private ArrayList<Card> floatingCards = new ArrayList<>();
    
    public void moveCard(Card c, CardPile p) {
        Card t = p.cardsInPile.get(0);

        // Tableau logic
        if (p.isTableau()) {
            

            if (t.getCurrentPile().isWaste()) {
                System.out.println(":(");
            }
            
            if (c.getRank() == (t.getRank() - 1) && (c.getColour() != t.getColour())  ) {
                
                if (c == c.getCurrentPile().cardsInPile.get(0)) {
                p.addCard(c);
                c.getCurrentPile().removeCard();
                selectedCard = null;
                System.out.println("No card is selected.");
                }
                
                else {
                    //Logic to move a whole stack if necessary
                    for (int i = 0; i == c.getCurrentPile().cardsInPile.indexOf(c); i++) {
                        floatingCards.add(c.getCurrentPile().cardsInPile.get(i)); // move necessary cards to temporary stack
                    }
                    boolean isDone = false;
                    while (isDone = false) {
                        if (c.getCurrentPile().cardsInPile.get(0) != c) {
                            c.getCurrentPile().removeCard();
                        }
                        if (c.getCurrentPile().cardsInPile.get(0) == c) {
                            c.getCurrentPile().removeCard();
                            isDone = true;
                        }
                    }
                    for (int i = floatingCards.size() - 1; i == 0; i--) {
                        p.putFirst(floatingCards.get(i));
                    }
                    
                    floatingCards.removeAll(floatingCards);
                    selectedCard = null;
                    System.out.println("No card is selected.");
                }
                
            }
            
            else {
                selectedCard = null;
                System.out.println("No card is selected.");
            }
        }
    }
    
    
    public void moveToFoundation(Card c, Foundation f) {
        //if (f.cardsInPile.isEmpty()){
            if (c != null && f.cardsInPile.isEmpty()) {
                if (c.getRank() == 0) {
                    f.addCard(c);
                    c.getCurrentPile().removeCard();
                    selectedCard = null;
                    System.out.println("No card is selected.");
                }
                else {
                    selectedCard = null;
                    System.out.println("No card is selected.");
                }
            }
            else if (c != null && !f.cardsInPile.isEmpty()) {
                f.addCard(c);
                c.getCurrentPile().removeCard();
                selectedCard = null;
                System.out.println("No card is selected.");
            }
                else {
                    selectedCard = null;
                    System.out.println("No card is selected.");
                }
            }
        //}
    
    
    public void moveToTableau(Card c, CardPile t) {
        if (t.cardsInPile.isEmpty() && c.getRank() == 13) {
            c.getCurrentPile().removeCard();
            t.putFirst(selectedCard);
            selectedCard = null;
            System.out.println("No card is selected.");
            
            
        }
    }

        
        
        
    }


