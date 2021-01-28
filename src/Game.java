
public class Game {
    
    public boolean aCardIsSelected; // Used to track if a click is selecting a card or moving it
    public Card selectedCard; // Tracks which card is selected
    
    public void moveCard(Card c, CardPile p) {
        Card t = p.cardsInPile.get(0);
        
        if (c.getRank() == (t.getRank() - 1) && (c.getColour() != t.getColour())) {
            p.putFirst(c);
            c.getCurrentPile().removeCard();
            selectedCard = null;
        }
        
        else {
            selectedCard = null;
        }
    }
    

}
