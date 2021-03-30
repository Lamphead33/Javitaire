import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Board {
    // CONSTANTS
    public static final int TABLE_HEIGHT = Card.CARD_HEIGHT * 4; // height of board overall
    public static final int TABLE_WIDTH = (Card.CARD_WIDTH * 7) + 100; // width of board overall
    public static final int NUM_FINAL_DECKS = 4; // foundation decks
    public static final int NUM_PLAY_DECKS = 7; // tableau decks
    public static final Point DECK_POS = new Point(5, 5); // board position of the initial playing deck
    public static final Point SHOW_POS = new Point(DECK_POS.x + Card.CARD_WIDTH + 5, DECK_POS.y); // board position of
                                                                                                  // the waste card deck
    public static final Point FINAL_POS = new Point(SHOW_POS.x + Card.CARD_WIDTH + 150, DECK_POS.y); // board position
                                                                                                     // of the tableau
                                                                                                     // deck's bottom
                                                                                                     // card
    public static final Point PLAY_POS = new Point(DECK_POS.x, FINAL_POS.y + Card.CARD_HEIGHT + 30); // board position
                                                                                                     // of the tableau
                                                                                                     // decks top card

    // GAMEPLAY STRUCTURES
    private static Foundation[] final_cards;// Foundation Stacks
    private static CardPile[] playCardStack; // Tableau stacks
    private static CardPile deck; // populated with standard 52 card deck
    private static Game game;
    private static CardPile waste; // waste Pile
    private static MouseListener wasteListener;

    // GUI COMPONENTS (top level)
    private static final JFrame frame = new JFrame("Javitaire");
    protected static final JPanel table = new JPanel();

    // MENU COMPONENTS ETC
    static JMenuBar mb;
    static JMenu x;
    static JMenuItem ng, vegas, rules, feedback;
    // private static JButton newGameButton = new JButton("New Game"); //Starts new
    // game, which also shuffles deck & resets dealt cards
    private static JButton toggleTimerButton = new JButton("Toggle Timer"); // PLACEHOLDER . . . will toggle a score
                                                                            // off/on
    private static Card newCardButton;// reveals waste card
    private static JLabel statusDisplay = new JLabel("No card is selected.");

    // SCORING COMPONENTS & VARIABLES
    private static Timer timer = new Timer();
    private static ScoreClock scoreClock = new ScoreClock();
    private static boolean timeRunning = false;// timer running?
    private static int score = 0;// keep track of the score
    private static int time = 0;// keep track of seconds elapsed
    private static JTextField scoreBox = new JTextField();// displays the score
    private static JTextField timeBox = new JTextField();// displays the time

    // Dragging stuff
    protected static Cursor draggingCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    static Card hoveredCard;
    static CardPile hoveredTableau;

    // Physically moves a card to a location within a component
    protected static Card moveCard(Card c, int x, int y) {
        c.setBounds(new Rectangle(new Point(x, y), new Dimension(Card.CARD_WIDTH + 10, Card.CARD_HEIGHT + 10)));
        c.setXY(new Point(x, y));
        return c;
    }

    // Main method and window initialization
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

    // Generates top menu
    private static void createTopMenu() {
        // TODO Auto-generated method stub
        mb = new JMenuBar();
        x = new JMenu("File");
        ng = new JMenuItem("New Game");
        vegas = new JMenuItem("Vegas Style");
        rules = new JMenuItem("Game Rules");
        feedback = new JMenuItem("Send Feedback");
        rules.addActionListener(new RulesListener());
        ng.addActionListener(new NewGameListener());
        feedback.addActionListener(new FeedbackListener());
        x.add(ng); // needs action listener to start new game
        x.add(vegas); // for the last game iteration, this switches to vegas rules
        x.add(rules); // should generate a popup for the game rules
        x.add(feedback);
        mb.add(x);
        frame.setJMenuBar(mb);
    }

    // New Game handler
    private static void playNewGame() {
        game = new Game();
        deck = new CardPile(true); // deal 52 cards
        deck.shuffle();
        deck.setDeck();
        newCardButton = deck.cardsInPile.get(0);
        waste = new CardPile(false); // sets an empty CardPile where waste will go
        waste.setWaste();
        wasteListener = new WasteListener();

        // Initializes location as 'deck' for each card
        for (Card c : deck.cardsInPile) {
            c.setCurrentPile(deck);
            c.addMouseListener(new CardListener(c)); // ADDS LISTENER
        }

        // Clears table for new game
        table.removeAll();

        // Reset stacks if user starts a new game
        if (playCardStack != null && final_cards != null) { // if game in progress
            for (int x = 0; x < NUM_PLAY_DECKS; x++) {
                playCardStack[x].makeEmpty(); // reset
            }
            for (int x = 0; x < NUM_FINAL_DECKS; x++) {
                final_cards[x].makeEmpty(); // reset
            }
        }

        // Initialize & place foundation decks on the board
        final_cards = new Foundation[NUM_FINAL_DECKS];
        for (int x = 0; x < NUM_FINAL_DECKS; x++) {
            final_cards[x] = new Foundation();
            final_cards[x].setFoundation();
            final_cards[x].addMouseListener(new FoundationListener(final_cards[x]));

            final_cards[x].setXY((FINAL_POS.x + (x * Card.CARD_WIDTH)) + 10, FINAL_POS.y); // setting location
            table.add(final_cards[x]); // adding to board
        }

        // Deck button to reveal waste card
        table.add(moveCard(newCardButton, DECK_POS.x, DECK_POS.y));
        newCardButton.addMouseListener(wasteListener);

        // Initialize & place play (tableau) decks/stacks
        playCardStack = new CardPile[NUM_PLAY_DECKS];
        for (int x = 0; x < NUM_PLAY_DECKS; x++) {
            playCardStack[x] = new CardPile(false);
            playCardStack[x].setTableau();
            playCardStack[x].setXY((DECK_POS.x + (x * (Card.CARD_WIDTH + 10))), PLAY_POS.y); // positioning on the board
            table.add(playCardStack[x]);
        }

        // Dealing cards for new game
        for (int x = 0; x < NUM_PLAY_DECKS; x++) {
            Card c = deck.pop().setFaceup();
            playCardStack[x].putFirst(c);
            c.setCurrentPile(playCardStack[x]);
            for (int y = x + 1; y < NUM_PLAY_DECKS; y++) {
                playCardStack[y].putFirst(c = deck.pop());
            }
        }

        // Set up "New Game" button
        // newGameButton.addActionListener(new NewGameListener());
        // newGameButton.setBounds(10, TABLE_HEIGHT - 100, 120, 30); //setting button
        // bounds
        // table.add(newGameButton); //putting button on table
        table.repaint();

        // Test button
        toggleTimerButton.addActionListener(new ToggleTimerListener());
        toggleTimerButton.setBounds(10, TABLE_HEIGHT - 100, 120, 30); // setting button bounds
        table.add(toggleTimerButton); // putting button on table
        table.add(scoreBox);
        table.add(timeBox);
        table.repaint();

        // RESET TIMES AND SCORES
        scoreBox.setBounds(140, TABLE_HEIGHT - 100, 120, 30);
        scoreBox.setText("Score: 0");
        scoreBox.setEditable(false);
        scoreBox.setOpaque(true);

        timeBox.setBounds(270, TABLE_HEIGHT - 100, 120, 30); // lol
        timeBox.setText("Seconds: 0");
        timeBox.setEditable(false);
        timeBox.setOpaque(true);

        // Game status
        statusDisplay.setForeground(Color.DARK_GRAY);
        statusDisplay.setBounds(450, TABLE_HEIGHT - 100, 400, 30);
        table.add(statusDisplay);
        table.repaint();
    }

    // TIMER AND SCORE RELATED METHODS
    // add/subtract points based on gameplay actions
    protected static void setScore(int deltaScore) {
        Board.score += deltaScore;
        String newScore = "Score: " + Board.score;
        scoreBox.setText(newScore);
        scoreBox.repaint();
    }

    // GAME TIMER UTILITIES
    protected static void updateTimer() {
        Board.time += 1;
        // every 10 seconds elapsed we take away 2 points
        if (Board.time % 10 == 0) {
            setScore(-2);
        }
        String time = "Seconds: " + Board.time;
        timeBox.setText(time);
        timeBox.repaint();
    }

    protected static void startTimer() {
        scoreClock = new ScoreClock();
        // set the timer to update every second
        timer.scheduleAtFixedRate(scoreClock, 1000, 1000);
        timeRunning = true;
    }

    // the pause timer button uses this
    protected static void toggleTimer() {
        if (timeRunning && scoreClock != null) {
            scoreClock.cancel();
            timeRunning = false;
        } else {
            startTimer();
        }
    }

    private static class ScoreClock extends TimerTask {
        @Override
        public void run() {
            updateTimer();
        }
    }

    /*
     * The following listeners handle mouse interaction and general card/button
     * behaviour
     */
    private static class NewGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            playNewGame();
        }
    }

    private static class ToggleTimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            toggleTimer();
            if (!timeRunning) {
                toggleTimerButton.setText("Start Timer");
            } else {
                toggleTimerButton.setText("Pause Timer");
            }
        }
    }

    // Reveals waste card
    private static class WasteListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            Card c = deck.popFirst();

            if (!waste.cardsInPile.isEmpty()) { // If there is a card in the waste
                Card t = waste.cardsInPile.get(0); // Card t is the card in the waste (to be moved to deck)
                t.setCurrentPile(deck); // Set t location as deck
                t.setFacedown(); // Flip it face down
                table.remove(t);
                deck.addCard(t); // Puts cars on bottom of deck
                waste.removeCard(); // Clears duplicate from waste
                waste.repaint();
            }

            // Following code executes regardless of whether a card was in the waste or not
            table.add(Board.moveCard(c, SHOW_POS.x, SHOW_POS.y)); // Prints waste card to waste
            c.setFaceup(); // Set card face up
            waste.addCard(c); // Add it to the waste
            c.setCurrentPile(waste); // Set current loc to waste
            c.removeMouseListener(wasteListener); // Removes listener from the card that is in the waste
            c.repaint();
            newCardButton = deck.getTopCard(); // Sets active wastelistener card to top of deck
            table.add(moveCard(newCardButton, DECK_POS.x, DECK_POS.y)); // Sets new card button to deck loc
            newCardButton.addMouseListener(wasteListener); // adds listener
            table.repaint();
        }
    }

    // Handles general card logic
    private static class CardListener extends MouseAdapter implements MouseListener {
        Card c;

        public CardListener(Card c) {
            this.c = c;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (!c.getFaceStatus() && c.getCurrentPile().cardsInPile.indexOf(c) == 0) {
                if (game.selectedCard == null) {
                    c.setFaceup();
                    c.repaint();
                    statusDisplay.setText(game.getStatus());
                    table.repaint();
                }
            }

            if (c.getFaceStatus() && c.getCurrentPile().cardsInPile.indexOf(c) == 0 && c.getValue() == Value.ACE
                    && !c.getCurrentPile().isDeck()) {
                for (int i = 0; i < 4; i++) {
                    if (final_cards[i].cardsInPile.isEmpty()) {
                        c.getCurrentPile().removeCard();
                        final_cards[i].addCard(c);

                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (c.getFaceStatus()) {
                if (game.selectedCard == null) {
                    game.selectedCard = c;
                    table.setCursor(draggingCursor);
                    game.setStatus("A card is selected: " + game.selectedCard.getName() + "\n"); // printing to track if
                                                                                                 // card is selected or
                                                                                                 // not - for debug
                                                                                                 // purposes

                    statusDisplay.setText(game.getStatus());
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            table.setCursor(Cursor.getDefaultCursor());

            if (game.selectedCard != null && hoveredCard != null && hoveredTableau == null) {
                game.moveCard(game.selectedCard, hoveredCard.getCurrentPile());
            }
            
            else if (game.selectedCard != null && hoveredTableau!= null && game.selectedCard.getRank() == 12) {
                game.moveKing(game.selectedCard, hoveredTableau);
                statusDisplay.setText(game.getStatus());

                for (MouseListener ml : hoveredTableau.getMouseListeners()) {
                    hoveredTableau.removeMouseListener(ml);
                }
            }
            
            else {
                statusDisplay.setText("Something went a little wrong.");
                game.selectedCard = null;
                hoveredCard = null;
                hoveredTableau = null;
                
            }

            for (int i = 0; i < NUM_PLAY_DECKS; i++) {
                if (playCardStack[i].cardsInPile.isEmpty()) {
                    playCardStack[i].addMouseListener(new TableauListener(playCardStack[i]));
                    playCardStack[i].repaint();
                    statusDisplay.setText(game.getStatus());
                }
            }

            statusDisplay.setText(game.getStatus());
            table.repaint();
            checkWin();

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            hoveredCard = (Card) e.getComponent();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            hoveredCard = null;
        }

        /*
         * OLD CODE
         *
         * @Override public void mouseClicked(MouseEvent e) { if (c.getFaceStatus()) {
         * if (game.selectedCard == null) { game.selectedCard = c;
         * game.setStatus("A card is selected: " + game.selectedCard.getName() + "\n");
         * //printing to track if card is selected or not - for debug purposes
         * 
         * statusDisplay.setText(game.getStatus());
         * 
         * } else if (game.selectedCard != null) { game.moveCard(game.selectedCard,
         * c.getCurrentPile());
         * 
         * for (int i = 0; i < NUM_PLAY_DECKS; i++) { if
         * (playCardStack[i].cardsInPile.isEmpty()) {
         * playCardStack[i].addMouseListener(new TableauListener(playCardStack[i]));
         * playCardStack[i].repaint();
         * 
         * statusDisplay.setText(game.getStatus()); } }
         * statusDisplay.setText(game.getStatus()); table.repaint(); } } if
         * (!c.getFaceStatus() && c.getCurrentPile().cardsInPile.indexOf(c) == 0) { if
         * (game.selectedCard == null) { c.setFaceup(); c.repaint();
         * statusDisplay.setText(game.getStatus()); table.repaint(); } else if
         * (game.selectedCard.getValue() == Value.KING) {
         * game.moveKing(game.selectedCard, c.getCurrentPile());
         * statusDisplay.setText(game.getStatus()); c.repaint(); table.repaint(); } }
         * checkWin(); }
         */

    }

    // Handles foundation logic
    private static class FoundationListener extends MouseAdapter {
        Foundation f;

        public FoundationListener(Foundation f) {
            this.f = f;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            game.moveToFoundation(game.selectedCard, f);
            statusDisplay.setText(game.getStatus());
            checkWin();
            table.repaint();

        }
    }

    // Mostly used for handling King behaviour
    private static class TableauListener extends MouseAdapter implements MouseListener {
        CardPile t;

        public TableauListener(CardPile t) {
            this.t = t;
        }

         
        @Override
        public void mouseReleased(MouseEvent e) {
            if (game.selectedCard != null) {
                game.moveKing(game.selectedCard, t);
                statusDisplay.setText(game.getStatus());

                for (MouseListener ml : t.getMouseListeners()) {
                    t.removeMouseListener(ml);
                }

                t.repaint();
                table.repaint();
            }
            checkWin();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            hoveredTableau = (CardPile) e.getComponent();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            hoveredTableau = null;
        }
    }

    private static void checkWin() {
        int foundationCount = 0;
        for (int x = 0; x < NUM_FINAL_DECKS; x++) {
            if (final_cards[x].cardsInPile.size() == 13) {
                foundationCount++;
            }
        }

        if (foundationCount == 4) {
            statusDisplay.setForeground(Color.GREEN);
            statusDisplay.setText("YOU WIN! Congratulations!");
            statusDisplay.repaint();
            table.repaint();
        } else {
            foundationCount = 0;
        }
    }

    private static class RulesListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            RulesWindow rules = new RulesWindow();
        }
    }

    private static class FeedbackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            FeedbackWindow feedback = new FeedbackWindow();
        }
    }
}