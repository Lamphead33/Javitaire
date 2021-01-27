

public class Game {
    public Tableau[] tableaus = new Tableau[7];
    public Foundation[] foundations = new Foundation[4];
    public Card selectedCard;
    public Deck deck;
    public Stock stock;
    public Waste waste;
    public CardPile targetPile;
    public boolean aCardIsSelected;

    public static void main(String[] args) {
        Game g = new Game();
        g.newGame();
    }
    
    public void newGame() {
        this.initializeCards();  
    }
    
    public void initializeCards() {
        // Initializes deck, stock, and all foundations and tableaus
        deck = new Deck();
        stock = new Stock();
        waste = new Waste();
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
        
        this.aCardIsSelected = false;
        
        
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
    
    
    /*
     * The following is to check that a clicked on card is selectable, ie: it is the card 
     * at the bottom of a tableau column, or is the top card of a foundation pile
     */
    
    
    public boolean isSelectable(Card c) {
        // Checks if a card is selected (THIS IS HOW THE GAME KNOWS IF
        // YOU'RE CHOOSING A CARD TO MOVE OR CHOOSING A PLACE TO MOVE A CARD TO)
        
        // This handles selecting a card when one isn't selected
        if (!this.aCardIsSelected) {
        // Iterates through tables
            for (int i = 0; i < 7; i++) {
                if (tableaus[i].cardsInPile.get(tableaus[i].cardsInPile.size() - 1) == c) {
                    return true;
                }
            }
            
            // Iterates through foundations
            for (int i = 0; i < 4; i++) {
                if (foundations[i].cardsInPile.get(foundations[i].cardsInPile.size() - 1) == c) {
                    return true;
                }
            }
            
            // Check waste
            if (waste.cardsInPile.get(waste.cardsInPile.size() - 1) == c) {
                return true;
            }
            
            
            //This handles flipping a card (or three, depending on rules) from stock to Waste
            if (c.getPile() instanceof Stock) {
                this.flipFromStock();
            }
            
            // If we get here, return false
            return false;
        } 
        
        
        // This logic handles MOVING an already selected card
        else {
            // UNDO select card if you click on it again
            if (c == selectedCard) {
                selectedCard = null;
                aCardIsSelected = false;
                return false;
            }
            // This handles moving the card to a specific location.
            else {
                //moveCard(c);
                return false;
            }
            
          }
    }
    
    
    // Make clicked on card become the selected card
    public void selectCard(Card c) {
        if (isSelectable(c)) {
            selectedCard = c;
            
            // Flips selected card if it's face down
            if (selectedCard.isFaceDown()) {
                selectedCard.setFaceUp();
            }
        }
    }
    
    
    public void moveCard(Card c) {
        selectedCard.getPile().moveCard(c.getPile());
    }
    
    // Deals new cards from stock to waste
    public void flipFromStock() {
        stock.moveCard(waste);
        
        // Following for loops ensure waste cards are face up and stock cards are face down,
        // and sets their location appropriately
        for (int i = 0; i < waste.cardsInPile.size(); i++) {
            waste.cardsInPile.get(i).setFaceUp();
            //waste.cardsInPile.get(i).getPile(waste);
        }
        for (int i = 0; i < stock.cardsInPile.size(); i++) {
            stock.cardsInPile.get(i).setFaceDown();
            //stock.cardsInPile.get(i).getPile(stock);
        }
    } 

}
