

public class Game {
    public Tableau[] tableaus = new Tableau[7];
    public Foundation[] foundations = new Foundation[4];

    
    
    public void newGame() {
        this.initializeCards();  
    }
    
    public void initializeCards() {
        // Initializes deck, stock, and all foundations and tableaus
        Deck deck = new Deck();
        Stock stock = new Stock();
        Waste waste = new Waste();
        for (int i = 0; i < 7; i++) {
            tableaus[i] = new Tableau();
        }
        for (int i = 0; i < 4; i++) {
            foundations[i] = new Foundation();
        }
        
        // Sets stock to shuffled deck
        stock.cardsInPile = deck.getDeck();
        
        
        // Deal cards from stock into tableau columns
        for (int i = 0; i < tableaus.length; i++) {
            stock.dealCard(tableaus[i], i+1);
        }
        
        // Set top card of tableau columns face up
        for (int i = 0; i < tableaus.length; i++) {
            tableaus[i].cardsInPile.get(tableaus[i].cardsInPile.size() - 1).setFaceUp();
        }
        
        
        // Test below
            int count = 1;
            System.out.println("Stock: ");
        for (Card c: stock.cardsInPile) {
           
            System.out.println(count + ": " + c.getCard());
            count++;
        }
        
        

        
        for (int i  = 0; i < tableaus.length; i++) {
            System.out.println("\nTableau " + (i+1) + ": ");
            for (Card c: tableaus[i].cardsInPile) {
                System.out.println(c.getCard());
            }
        }
        // Test ends
        

    }
    
    public static void main(String[] args) {
        Game g = new Game();
        g.newGame();
    }
    
    
    
    /* Check for a victory -
     * This method will eventually be called every time a card is added to a foundation.
     * It will check that each Foundation is full. If they all are full (ace thru king),  player wins.
     */
    public boolean checkWin() {
        int winCount = 0;
        for (int i = 0; i < foundations.length; i++) {
            if (foundations[i].cardsInPile.size() == 13) {
                winCount++;
            }
        }
        if (winCount == 4) {
            return true;
        } else return false;
    }
    

}
