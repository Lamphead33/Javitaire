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
    
    
    public void moveCard(CardPile pile) {
        // If destination is a Tableau - 
        if (pile instanceof Tableau) {
            // Get top card of selected pile - only card that can be moved
            Card c = this.cardsInPile.get(this.cardsInPile.size() - 1);
            // Check if card meets criteria to be allowed to move to target tableau
            if (pile.canAccept(c)) {
                this.cardsInPile.remove(this.cardsInPile.size() - 1);
                pile.addCard(c);
                c.setLocation(pile);
                this.pileSize--;
            }
            else {
                // CARD CANNOT BE MOVED TO TARGET
            }
            
            if (this.cardsInPile.isEmpty()) {
                this.isEmpty = true;
            }
        }
        
        // If destination is a foundation - 
        else if (pile instanceof Foundation) {
         // Get top card of selected pile - it's the only one that can be moved
            Card c = this.cardsInPile.get(this.cardsInPile.size() - 1);
            // Check if card meets criteria to be allowed to move to target foundation
            if (pile.canAccept(c)) {
                this.cardsInPile.remove(this.cardsInPile.size() - 1);
                pile.addCard(c);
                c.setLocation(pile);
                this.pileSize--;
            }
            else {
                // CARD CANNOT BE MOVED TO TARGET
            }
            if (this.cardsInPile.isEmpty()) {
                this.isEmpty = true;
            }
        }
        
    }
    
    
    
    
    /*
     *  Might not need below methods, but I'm gonna leave them just in case lol
     */
    
    // Move a card to a Tableau
    public void moveCard(Tableau t) {
        // Get top card of selected pile - only card that can be moved
        Card c = this.cardsInPile.get(this.cardsInPile.size() - 1);
        // Check if card meets criteria to be allowed to move to target tableau
        if (t.canAccept(c)) {
            this.cardsInPile.remove(this.cardsInPile.size() - 1);
            t.addCard(c);
            c.setLocation(t);
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
            c.setLocation(f);
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
