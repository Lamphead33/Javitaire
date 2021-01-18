
public class Card {
    private String rank;
    private String suit;
    private int rankId;
    
    
    // Constructor - upon creation, a rank and suit are assigned
    public Card(int rankId, String suit) {
        this.rankId = rankId;
        this.suit = suit;
        
        switch (rankId) {
            case 1:
                this.rank = "Ace of ";
                break;
            case 2:
                this.rank = "Two of ";
                break;
            case 3:
                this.rank = "Three of ";
                break;
            case 4:
                this.rank = "Four of ";
                break;
            case 5:
                this.rank = "Five of ";
                break;
            case 6:
                this.rank = "Six of ";
                break;
            case 7:
                this.rank = "Seven of ";
                break;
            case 8:
                this.rank = "Eight of ";
                break;
            case 9:
                this.rank = "Nine of ";
                break;
            case 10:
                this.rank = "Ten of ";
                break;
            case 11:
                this.rank = "Jack of ";
                break;
            case 12:
                this.rank = "Queen of ";
                break;
            case 13:
                this.rank = "King of ";
                break;
        }   
    }
    
    
    // Getters
    public String getRank() {
        return this.rank;
    }
    
    public String getSuit() {
        return this.suit;
    }
    
    public int getRankId() {
        return this.rankId;
    }
    
    public String getCard() {
        return rank + suit;
    }
    
}
