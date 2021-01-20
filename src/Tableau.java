
public class Tableau extends CardPile {
    
    @Override
    public boolean canAccept(Card c) {
        // Checks if passed card has opposite colour and lower rank than target
        if (!this.cardsInPile.get(this.cardsInPile.size() - 1).getColour().equals(c.getColour()) && this.cardsInPile.get(this.cardsInPile.size() - 1).getRank() == (c.getRank() + 1)) {
            return true;
        }
        else return false;
    }
    
    
}
