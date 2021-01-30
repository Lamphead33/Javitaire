import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Board
{
	// CONSTANTS
	public static final int TABLE_HEIGHT = Card.CARD_HEIGHT * 4; //height of board overall
	public static final int TABLE_WIDTH = (Card.CARD_WIDTH * 7) + 100; //width of board overall
	public static final int NUM_FINAL_DECKS = 4; //foundation decks
	public static final int NUM_PLAY_DECKS = 7; //tableau decks
	
	public static final Point DECK_POS = new Point(5, 5); //board position of the initial playing deck
	public static final Point SHOW_POS = new Point(DECK_POS.x + Card.CARD_WIDTH + 5, DECK_POS.y); //board position of the waste card deck
	public static final Point FINAL_POS = new Point(SHOW_POS.x + Card.CARD_WIDTH + 150, DECK_POS.y); //board position of the tableau deck's bottom card
	public static final Point PLAY_POS = new Point(DECK_POS.x, FINAL_POS.y + Card.CARD_HEIGHT + 30); //board position of the tableau decks top card

	// GAMEPLAY STRUCTURES
	private static Foundation[] final_cards;// Foundation Stacks
	private static CardPile[] playCardStack; // Tableau stacks
	private static final Card newCardPlace = new Card();// waste card spot
	private static CardPile deck; // populated with standard 52 card deck
	private static boolean cardSelected; // tracks whether a card is currently selected
	private static Game game; 
	private static CardPile waste; //waste Pile

	// GUI COMPONENTS (top level)
	private static final JFrame frame = new JFrame("Javitaire");
	protected static final JPanel table = new JPanel();
	
	//MENU COMPONENTS
	static JMenuBar mb;
	static JMenu x;
	static JMenuItem ng, vegas, rules;
	private static JButton newGameButton = new JButton("New Game"); //Starts new game, which also shuffles deck & resets dealt cards
	private static JButton scoreButton = new JButton("Toggle Score"); //PLACEHOLDER . . . will toggle a score off/on
	private static final Card newCardButton = new Card();// reveals waste card

	// moves a card to abs location within a component
	protected static Card moveCard(Card c, int x, int y) {
		c.setBounds(new Rectangle(new Point(x, y), new Dimension(Card.CARD_WIDTH + 10, Card.CARD_HEIGHT + 10)));
		c.setXY(new Point(x, y));
		return c;
	}
	
	
	//main method and window initialization 
	public static void main(String[] args) {

		Container contentPane;

		frame.setSize(TABLE_WIDTH, TABLE_HEIGHT);

		table.setLayout(null);
		table.setBackground(new Color(211, 211, 211));

		contentPane = frame.getContentPane();
		contentPane.add(table);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createTopMenu();
		playNewGame();

		frame.setVisible(true);
	}
	
	private static void createTopMenu() {
		// TODO Auto-generated method stub
		mb = new JMenuBar();
		x = new JMenu("File");
		
		ng = new JMenuItem("New Game");
		vegas = new JMenuItem("Vegas Style");
		rules = new JMenuItem("Game Rules");
		
		x.add(ng); //needs action listener to start new game
		x.add(vegas); //for the last game iteration, this switches to vegas rules
		x.add(rules); //should generate a popup for the game rules
		
		mb.add(x);
		frame.setJMenuBar(mb);
	}

	private static void playNewGame() {
	    game = new Game();
	    
	    
		deck = new CardPile(true); // deal 52 cards
		deck.shuffle();
		deck.setDeck();
		
		waste = new CardPile(false); //sets an empty CardPile where waste will go
		waste.setWaste();
		
		// Initializes location as 'deck' for each card
		for (Card c : deck.cardsInPile) {
		    c.setCurrentPile(deck);
		    c.addMouseListener(new CardListener(c)); // ADDS LISTENER
		}

        
		table.removeAll();
		
		// reset stacks if user starts a new game
		if (playCardStack != null && final_cards != null) { //if game in progress
			for (int x = 0; x < NUM_PLAY_DECKS; x++) {
				playCardStack[x].makeEmpty(); //reset
			}
			for (int x = 0; x < NUM_FINAL_DECKS; x++) {
				final_cards[x].makeEmpty(); //reset
			}
		}
		
		// initialize & place foundation decks on the board
		final_cards = new Foundation[NUM_FINAL_DECKS];
		for (int x = 0; x < NUM_FINAL_DECKS; x++) {
			final_cards[x] = new Foundation();
			final_cards[x].setFoundation();
			final_cards[x].addMouseListener(new FoundationListener(final_cards[x]));

			final_cards[x].setXY((FINAL_POS.x + (x * Card.CARD_WIDTH)) + 10, FINAL_POS.y); //setting location
			table.add(final_cards[x]); //adding to board
		}
		
		//deck button to reveal waste card
		table.add(moveCard(newCardButton, DECK_POS.x, DECK_POS.y));
		newCardButton.addMouseListener(new WasteListener());
		
		// initialize & place play (tableau) decks/stacks
		playCardStack = new CardPile[NUM_PLAY_DECKS];
		for (int x = 0; x < NUM_PLAY_DECKS; x++) {
			playCardStack[x] = new CardPile(false);
			playCardStack[x].setTableau();
			playCardStack[x].setXY((DECK_POS.x + (x * (Card.CARD_WIDTH + 10))), PLAY_POS.y); //positioning on the board
			
			// vvvv I don't think this is gonna work. Need another solution for Kings onto blank spaces
			// playCardStack[x].addMouseListener(new TableauListener(playCardStack[x]));

			table.add(playCardStack[x]);
		}

		// Dealing cards for new game
		for (int x = 0; x < NUM_PLAY_DECKS; x++) {
			int hld = 0;
			Card c = deck.pop().setFaceup();
			playCardStack[x].putFirst(c);
			c.setCurrentPile(playCardStack[x]);

			for (int y = x + 1; y < NUM_PLAY_DECKS; y++) {
				playCardStack[y].putFirst(c = deck.pop());
			}
		}

		newGameButton.addActionListener(new NewGameListener()); 
		newGameButton.setBounds(10, TABLE_HEIGHT - 100, 120, 30); //setting button bounds
		
		table.add(newGameButton); //putting button on table
		table.repaint();
		
		// TEST BUTTONS
		scoreButton.addActionListener(new TestListener());
		scoreButton.setBounds(150, TABLE_HEIGHT - 100, 200, 30); //setting button bounds
		table.add(scoreButton); //putting button on table
		table.repaint();
	}
	
	// BUTTON LISTENERS
	private static class NewGameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			playNewGame();
		}
	}
	
	private static class TestListener implements ActionListener {
	    @Override
	    public void actionPerformed(ActionEvent e) {
	        Card c = deck.pop().setFaceup();
	        playCardStack[6].putFirst(c); 
	        table.repaint();
	    }
	}
	
	//reveals waste card
    private static class WasteListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
        	//on mouse click, peek top card & set face UP
        	Card c = deck.cardsInPile.get(0);

        	//if the waste pile is NOT empty:
            if (!waste.cardsInPile.isEmpty()) {
            	Card t = waste.cardsInPile.get(0); //Card t is the card on the waste pile at Index 0 (should be the only card there)
                t.setCurrentPile(deck);
                t.setFaceup(); //sets waste card faceup
                t.repaint(); //redundant? shouldn't need to repaint as c flipped, only card t
                table.repaint(); //repaints table to show flipped card
                //waste.removeCard(); //not sure what this is doing here?
                
            } 
            //if the waste pile is NOT empty
            	table.add(Board.moveCard(c, SHOW_POS.x, SHOW_POS.y));
                c.setFacedown();
                waste.putFirst(c);
                c.setCurrentPile(waste);
                c.repaint();
                table.repaint();
            
	            System.out.println("Number of cards in deck: " + deck.cardsInPile.size()); //printing to verify # of cards in play Deck as game progresses                         
	        } /* KRYS I ADDED THIS BUT IT CURRENTLY DOES **NOTHING**. ENJOY :D
        public void mouseReleased(MouseEvent e) {
        	if (!waste.cardsInPile.isEmpty()) {
        		Card t = waste.cardsInPile.get(0);
        		deck.add(t);
        		t.repaint();
        		table.repaint();
        	}
        } */
    }
	
	private static class CardListener extends MouseAdapter {
	    Card c;
	    
	    public CardListener(Card c) {
	        this.c=c;
	    }
	    
	    @Override
	    public void mouseClicked(MouseEvent e) {  
	        if (c.getFaceStatus()) {
    	        if (game.selectedCard == null) {
    	            game.selectedCard = c;
    	            
    	            System.out.println("A card is selected."); //printing to track if card is selected or not
    	        } else if (game.selectedCard != null) {
    	            game.moveCard(game.selectedCard, c.getCurrentPile());
    	            c.repaint();
    	            table.repaint();
    	        }
	        }
	        
	        if (!c.getFaceStatus() && c.getCurrentPile().cardsInPile.indexOf(c) == 0) {
	            c.setFaceup();
	            c.repaint();
	            table.repaint();
	        }
	    }
	    
	}
	
	   private static class FoundationListener extends MouseAdapter {
	       Foundation f;
	       
	       public FoundationListener(Foundation f) {
	           this.f = f;
	       }
	       
	       @Override
	       public void mouseClicked(MouseEvent e) {
	           
	           game.moveToFoundation(game.selectedCard, f);
	           table.repaint();
	       }
	}
	   
	   private static class TableauListener extends MouseAdapter {
	       CardPile t;
	       
	       public TableauListener(CardPile t) {
	           this.t = t;
	       }
	       
	       @Override
	       public void mouseClicked(MouseEvent e) {
	           if (game.selectedCard != null) {
	               game.moveToTableau(game.selectedCard, t);
	               table.repaint();
	           } 
	       }
	   }   
}