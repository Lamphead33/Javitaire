import java.util.ArrayList;

public abstract class CardPile {
    protected int pileSize;
    protected ArrayList<Card> cardsInPile;
    protected boolean isEmpty = true;
    
    public void addCard(Card c) {
        cardsInPile.add(c);
        this.pileSize++;
        this.isEmpty = false;
    }
    
    public void moveCard(Tableau t) {
        Card c = this.cardsInPile.get(this.cardsInPile.size() - 1);
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
        Card c = this.cardsInPile.get(this.cardsInPile.size() - 1);
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
