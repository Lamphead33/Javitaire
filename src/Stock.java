import java.util.ArrayList;

public class Stock extends CardPile {
	
    public Stock() {
        this.cardsInPile = new ArrayList<Card>();
    }
    
    // Overload - Stock can only move cards to Waste. When it does, cards in Waste are moved to bottom of Stock.
    public void moveCard(Waste w) {
        // Create a temporary Stock array and add all current cards in stock to it
        ArrayList<Card> tempStock = new ArrayList<>();
        for (int i = 0; i < cardsInPile.size(); i++) {
            tempStock.add(cardsInPile.get(i));
        }
        // Erase original Stock pile
        for (int i = 0; i < cardsInPile.size(); i++) {
            cardsInPile.remove(cardsInPile.get(i));
        }
        // Move cards from Waste to bottom of Stock
        for (int i = 0; i < w.cardsInPile.size(); i++) {
            this.cardsInPile.add(w.cardsInPile.get(i));
            this.cardsInPile.get(i).setFaceDown();
        }
        // Erase original Waste pile
        for (int i = 0; i < w.cardsInPile.size(); i++) {
            w.cardsInPile.remove(i);
        }
        // Move temp stock to active stock, on top of waste cards
        for (int i = 0; i < tempStock.size(); i++) {
            this.cardsInPile.add(tempStock.get(i));
        }
        
        // Finally, add card to waste
        Card c = this.cardsInPile.get(this.cardsInPile.size() - 1);
        w.addCard(c);
        c.setFaceUp();
        
        /* 
         *      We need to eventually implement an option to implement either 1 or 3 card Wastes.
         *      For this, we'd just create a global boolean, isThree or something. Then, right here
         *      we'd just add three cards to the waste instead of one.
         */
	
    } 
    
    // Following method is used to deal initial cards from Stock to Tableau
    public void dealCard(Tableau t, int num) {
        
        for (int i = 0; i < num; i++) {
            // Get top card of stock
            Card c = this.cardsInPile.get(this.cardsInPile.size() - 1);
            // Add card to tableau and remove from stock
            t.addCard(c);
            c.setLocation(t);
            this.cardsInPile.remove(this.cardsInPile.size() - 1);

        }
        
        if (this.cardsInPile.isEmpty()) {
            this.isEmpty = true;
        }
    } 
    
    /*
     *       This shouldn't come up, but if we make a mistake, the following overrides
     *       will prevent the Stock from moving cards to anywhere except for the Waste.       
     */
    
	
    @Override
    public void moveCard(Tableau t) {
    }
    
    @Override
    public void moveCard(Foundation f) {
    } 
} 
