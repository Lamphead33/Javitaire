public class Game {
    
    //public boolean aCardIsSelected; // Used to track if a click is selecting a card or moving it
    public Card selectedCard; // Tracks which card is selected
    
    public void moveCard(Card c, CardPile p) {
        Card t = p.cardsInPile.get(0);

        // Handles move for tableaus
        if (p.isTableau()) {
            if (c.getRank() == (t.getRank() - 1) && (c.getColour() != t.getColour())  ) {
                if (c == c.getCurrentPile().cardsInPile.get(0)) {
                    c.getCurrentPile().removeCard();
                    p.putFirst(c);
                    selectedCard = null;
                    System.out.println("No card is selected.");
                }
            }
            else {
                selectedCard = null;
                System.out.println("No card is selected.");
            }
        }
        // Handles moves for foundations
        else if (p.isFoundation()) {
            if (c.getRank() == (t.getRank() + 1) && c.getSuit() == t.getSuit()) {
                c.getCurrentPile().removeCard();
                p.addCard(c);
                c.setCurrentPile(p);
                selectedCard = null;
                System.out.println("No card is selected.");
            }
            else {
                selectedCard = null;
                System.out.println("No card is selected.");
            }
        }
        
    }
    
    public void moveToFoundation(Card c, Foundation f) {
        if (f.cardsInPile.isEmpty() && selectedCard != null && c.getRank() == 0) {
        c.getCurrentPile().removeCard();
        f.putFirst(selectedCard);
        // c.getCurrentPile().cardsInPile.remove(0);
        c.setCurrentPile(f);
        selectedCard = null;
        System.out.println("No card is selected");
        } else {
            selectedCard = null;
            System.out.println("No card is selected");
        }
    }
    
    
    public void moveKing(Card c, CardPile t) {
        if (c.getValue() == Value.KING) {
            c.getCurrentPile().removeCard();
            t.putFirst(selectedCard);
            selectedCard.setCurrentPile(t);
            selectedCard = null;
            System.out.println("No card is selected.");
        } 
    } 
}


