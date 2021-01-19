import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * @author ariella
 *
 */

public class Board {
	
	//GUI
	private static final JFrame frame = new JFrame("Javitaire");
	protected static final JPanel table = new JPanel();
	
	//TABLES & DECKS
	public static final int BOARD_HEIGHT = Card.CARD_HEIGHT * 4;
	public static final int BOARD_WIDTH = (Card.CARD_WIDTH * 7) + 100;
	
	//MENU COMPONENTS
	static JMenuBar mb;
	static JMenu x;
	static JMenuItem ng, vegas, rules;
	private static JButton newGameButton = new JButton("New Game");

	public static void main(String[] args) {
		Container contentPane;
		frame.setSize(BOARD_WIDTH, BOARD_HEIGHT);

		table.setLayout(null);
		table.setBackground(new Color(211, 211, 211));

		contentPane = frame.getContentPane();
		contentPane.add(table);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createTopMenu();
		playNewGame();
		
		table.add(newGameButton);

		frame.setVisible(true);
	}

	private static void createTopMenu() {
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
		/* needs way more logic in here
		deck = new CardPile(true); 
		Deck.shuffle(); 
		*/
		
		newGameButton.addActionListener(new NewGameListener());
		newGameButton.setBounds(10, BOARD_HEIGHT - 100, 120, 30);
		newGameButton.setVisible(true);
	}
	
	//BUTTON LISTENERS
	private static class NewGameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			playNewGame();
		}
	}
}
