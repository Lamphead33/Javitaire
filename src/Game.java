import java.util.Collections;
import java.util.Comparator;


public class Game {
    private String status;
    
    
    //public boolean aCardIsSelected; // Used to track if a click is selecting a card or moving it
    public Card selectedCard; // Tracks which card is selected
    
    public void moveCard(Card c, CardPile p) {
        Card t = p.cardsInPile.get(0);

        // Handles move for tableaus
        if (p.isTableau())
            try {
                {
                    if (c.getRank() == (t.getRank() - 1) && (c.getColour() != t.getColour())  ) {
                        if (c == c.getCurrentPile().cardsInPile.get(0)) {
                            c.getCurrentPile().removeCard();
                            p.putFirst(c);
                            selectedCard = null;
                            setStatus("No card is selected.");
                        }
                        
                        
                        
                        //MOVE MULTIPLE CARDS
                        else if (c != c.getCurrentPile().cardsInPile.get(0) && c.getCurrentPile().isTableau()) {
                            int origRank = c.getRank();
                            
                            // Move cards to target pile
                            for (int i = c.getCurrentPile().cardsInPile.size() -1; i >= 0; i--) {
                                Card card = c.getCurrentPile().cardsInPile.get(0);
                                if (card.getFaceStatus() && card.getRank() <= origRank) {
                                    card.getCurrentPile().removeCard();
                                    p.putFirst(card);
                                }
                            }
                            
                            // Sort to correct order
                            Collections.sort(p.cardsInPile, new Comparator<Card>() {
                                @Override
                                public int compare(final Card card1, final Card card2) {
                                    int x = 100;
                                    if (card1.getFaceStatus() && card2.getFaceStatus()) {
                                        return Integer.compare(card1.getRank(), card2.getRank());
                                    }
                                    return x;
                                    
                                }
                            });
                            selectedCard = null;
                            setStatus("No card is selected.");
                        }
                        
                        
                        // MOVE CARDS FROM FOUNDATION
                        else if (c != c.getCurrentPile().cardsInPile.get(0) && c.getCurrentPile().isFoundation()) {
                            c.getCurrentPile().removeLastCard();
                            p.putFirst(c);
                            selectedCard = null;
                            setStatus("No card is selected.");
                        }
                        
                    }
                    else {
                        selectedCard = null;
                        setStatus("Invalid move. No card is selected.");
                    }
                }
            } catch (Exception e) {
                setStatus("An error has occured: \n" + e);
            }
        
        
        else if (p.isFoundation()) {
            t = p.cardsInPile.get(p.cardsInPile.size() - 1);
            try {
                if (c.getRank() == (t.getRank() + 1) && c.getSuit() == t.getSuit()) {
                    c.getCurrentPile().removeCard();
                    p.addCard(c);
                    c.setCurrentPile(p);
                    selectedCard = null;
                    setStatus("No card is selected.");
                    

                }
                else {
                    selectedCard = null;
                    setStatus("Invalid move. No card is selected.");
                }
            } catch (Exception e) {
                setStatus("An error has occured: \n" + e);
            }
        }
        
    }
    
    public void moveToFoundation(Card c, Foundation f) {
        try {
            if (f.cardsInPile.isEmpty() && selectedCard != null && c.getRank() == 0) {
            c.getCurrentPile().removeCard();
            f.putFirst(selectedCard);
            // c.getCurrentPile().cardsInPile.remove(0);
            c.setCurrentPile(f);
            selectedCard = null;
            setStatus("No card is selected");
            } else {
                selectedCard = null;
                setStatus("Invalid move. No card is selected.");;
            }
        } catch (Exception e) {
            setStatus("An error has occured: \n" + e);
        }
    }
    
    
    public void moveKing(Card c, CardPile t) {
        try {
            if (c.getValue() == Value.KING) {
                
                if (c == c.getCurrentPile().cardsInPile.get(0)) {
                    c.getCurrentPile().removeCard();
                    t.putFirst(c);
                    selectedCard = null;
                    setStatus("No card is selected.");
                }
                
                
                
                //MOVE MULTIPLE CARDS
                else if (c != c.getCurrentPile().cardsInPile.get(0)) {
                    int origRank = c.getRank();
                    
                    // Move cards to target pile
                    for (int i = c.getCurrentPile().cardsInPile.size() -1; i >= 0; i--) {
                        Card card = c.getCurrentPile().cardsInPile.get(0);
                        if (card.getFaceStatus() && card.getRank() <= origRank) {
                            card.getCurrentPile().removeCard();
                            t.putFirst(card);
                        }
                    }
                }
             // Sort to correct order
                Collections.sort(t.cardsInPile, new Comparator<Card>() {
                    @Override
                    public int compare(final Card card1, final Card card2) {
                        int x = 100;
                        if (card1.getFaceStatus() && card2.getFaceStatus()) {
                            return Integer.compare(card1.getRank(), card2.getRank());
                        }
                        return x;
                        
                    }
                    
                });
                selectedCard = null;
                setStatus("No card is selected.");
                
                /* OLD CODE
                c.getCurrentPile().removeCard();
                t.putFirst(selectedCard);
                selectedCard.setCurrentPile(t);
                selectedCard = null;
                setStatus("No card is selected.");
                */ 
                
            }
        } catch (Exception e) {
            setStatus("An error has occured: \n" + e);
        } 
    } 
    
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }
}


