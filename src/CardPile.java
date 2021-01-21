import java.util.ArrayList;

public abstract class CardPile {
    protected int pileSize = 0;
    protected ArrayList<Card> cardsInPile;
    protected boolean isEmpty = true;
    
    public void addCard(Card c) {
        this.cardsInPile.add(c);
        this.pileSize++;
        this.isEmpty = false;
    }
    
    // Move a card to a Tableau
    public void moveCard(Tableau t) {
        // Get top card of selected pile - only card that can be moved
        Card c = this.cardsInPile.get(this.cardsInPile.size() - 1);
        // Check if card meets criteria to be allowed to move to target tableau
        if (t.canAccept(c)) {
            this.cardsInPile.remove(this.cardsInPile.size() - 1);
            t.addCard(c);
            this.pileSize--;
        }
        else {
            // CARD CANNOT BE MOVED TO TARGET
        }
        
        if (this.cardsInPile.isEmpty()) {
            this.isEmpty = true;
        }
    }
    
    public void moveCard(Foundation f) {
        // Get top card of selected pile - it's the only one that can be moved
        Card c = this.cardsInPile.get(this.cardsInPile.size() - 1);
        // Check if card meets criteria to be allowed to move to target foundation
        if (f.canAccept(c)) {
            this.cardsInPile.remove(this.cardsInPile.size() - 1);
            f.addCard(c);
            this.pileSize--;
        }
        else {
            // CARD CANNOT BE MOVED TO TARGET
        }
        if (this.cardsInPile.isEmpty()) {
            this.isEmpty = true;
        }
    }
    
    
    public boolean canAccept(Card c) {
        return false;
    }

}
