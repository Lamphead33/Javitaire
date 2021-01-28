
public class Game {
    
    public boolean aCardIsSelected; // Used to track if a click is selecting a card or moving it
    public Card selectedCard; // Tracks which card is selected
    
    public void moveCard(Card c, CardPile p) {
        p.putFirst(c);
        c.getCurrentPile().removeCard();
        //selectedCard = null;
    }
    

}
