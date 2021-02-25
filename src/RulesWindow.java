import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class RulesWindow extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    //private JFrame rulesFrame = new JFrame();
    private JTextArea rulesText = new JTextArea();
    
    public RulesWindow() {
        super("Rules");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(700, 500);
        
        
        rulesText.setEditable(false);
        rulesText.setWrapStyleWord(true);
        rulesText.setLineWrap(true);
        rulesText.setFocusable(false);
        JScrollPane scroll = new JScrollPane(rulesText);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        this.rulesText.setText(getRules());
        
        this.add(scroll);
        
        this.setVisible(true);
        
        
    }
    
    
    private String getRules() {
        return "OBJECT OF THE GAME\n" + 
                "The first objective is to release and play into position certain cards to build up each foundation, in sequence and in suit, from the ace through the king. The ultimate objective is to build the whole pack onto the foundations, and if that can be done, the Solitaire game is won.\n" + 
                "\n" + 
                "RANK OF CARDS\n" + 
                "The rank of cards in Solitaire games is: K (high), Q, J, 10, 9, 8, 7, 6, 5, 4, 3, 2, A (low).\n" + 
                "\n" + 
                "THE DEAL\n" + 
                "There are four different types of piles in Solitaire:\n" + 
                "\n" + 
                "THE TABLEAU: Seven piles that make up the main table.\n" + 
                "THE FOUNDATIONS: Four piles on which a whole suit or sequence must be built up. In most Solitaire games, the four aces are the bottom card or base of the foundations. The foundation piles are hearts, diamonds, spades, and clubs.\n" + 
                "THE STOCK (or “Hand”) PILE: If the entire pack is not laid out in a tableau at the beginning of a game, the remaining cards form the stock pile from which additional cards are brought into play according to the rules.\n" + 
                "THE TALON (or “Waste”) PILE: Cards from the stock pile that have no place in the tableau or on foundations are laid face up in the waste pile.\n" + 
                "To form the tableau, seven piles need to be created. Starting from left to right, place the first card face up to make the first pile, deal one card face down for the next six piles. Starting again from left to right, place one card face up on the second pile and deal one card face down on piles three through seven. Starting again from left to right, place one card face up on the third pile and deal one card face down on piles four through seven. Continue this pattern until pile seven has one card facing up on top of a pile of six cards facing down.\n" + 
                "\n" + 
                "The remaining cards form the stock (or “hand”) pile and are placed above the tableau.\n" + 
                "\n" + 
                "When starting out, the foundations and waste pile do not have any cards.\n" + 
                "\n" + 
                "THE PLAY\n" + 
                "The initial array may be changed by \"building\" - transferring cards among the face-up cards in the tableau. Certain cards of the tableau can be played at once, while others may not be played until certain blocking cards are removed. For example, of the seven cards facing up in the tableau, if one is a nine and another is a ten, you may transfer the nine to on top of the ten to begin building that pile in sequence. Since you have moved the nine from one of the seven piles, you have now unblocked a face down card; this card can be turned over and now is in play.\n" + 
                "\n" + 
                "As you transfer cards in the tableau and begin building sequences, if you uncover an ace, the ace should be placed in one of the foundation piles. The foundations get built by suit and in sequence from ace to king.\n" + 
                "\n" + 
                "Continue to transfer cards on top of each other in the tableau in sequence. If you can’t move any more face up cards, you can utilize the stock pile by flipping over the first card. This card can be played in the foundations or tableau. If you cannot play the card in the tableau or the foundations piles, move the card to the waste pile and turn over another card in the stock pile.\n" + 
                "\n" + 
                "If a vacancy in the tableau is created by the removal of cards elsewhere it is called a “space”, and it is of major importance in manipulating the tableau. If a space is created, it can only be filled in with a king. Filling a space with a king could potentially unblock one of the face down cards in another pile in the tableau.\n" + 
                "\n" + 
                "Continue to transfer cards in the tableau and bring cards into play from the stock pile until all the cards are built in suit sequences in the foundation piles to win!\n" + 
                "\n" + 
                "";
    }
    
    
}
