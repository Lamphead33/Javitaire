
public class Foundation extends CardPile {
    
    // Acceptance criteria for Foundation piles
    public boolean canAccept(Card c) {
        // If card is first card in pile and is an Ace, return true
        if (this.cardsInPile.isEmpty() && (c.getRankId() == 1)){
            return true;
        }
        // If card is same suit as card beneath it and one rank higher, return true
        else if (this.cardsInPile.get(this.cardsInPile.size() - 1).getColour().equals(c.getSuit()) && (this.cardsInPile.get(this.cardsInPile.size() - 1).getRankId() == c.getRankId() - 1))  {
            return true;
        }
        else return false;
    }

}
