import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Vector;

import javax.swing.JComponent;

/* This is GUI component with a embedded
 * data structure. This structure is a mixture
 * of a queue and a stack
 */
class CardPile extends JComponent {
	
	//DECK VARIABLES
	protected final int NUM_CARDS = 52;
	protected ArrayList<Card> cardsInPile;
	protected boolean playStack = false;
	
	//GUI PLACEMENT VARIABLES
	protected int SPREAD = 18; //this variable is the "stack distance" between cards on the tableau/spacing
	protected int _x = 0;
	protected int _y = 0;

	public CardPile(boolean isDeck)
	{
		int f = 1;
		this.setLayout(null);
		cardsInPile = new ArrayList<Card>();
		if (isDeck)
		{
			// set deck position
			for (Suit suit : Suit.values())
			{
				for (Value value : Value.values())
				{
					cardsInPile.add(new Card(suit, value));
				}
			}
		} else {
			playStack = true;
		}
	}

	public boolean empty()
	{
		if (cardsInPile.isEmpty())
			return true;
		else
			return false;
	}

	public void push(Card card) {
		cardsInPile.add(card);
	}


	//shuffle the cards in the deck
	public void shuffle() {
		Collections.shuffle(cardsInPile);
	}

	public int showSize() {
		return cardsInPile.size();
	}

	// reverse the order of the stack
	public CardPile reverse() {
		Vector<Card> v = new Vector<Card>();
		while (!this.empty())
		{
			v.add(this.pop());
		}
		while (!v.isEmpty())
		{
			Card card = v.firstElement();
			this.push(card);
			v.removeElement(card);
		}
		return this;
	} 

	public void makeEmpty() {
		while (!this.empty())
		{
			this.popFirst();
		}
	} 
	
	public Card popFirst() {
		if (!this.empty())
		{
			Card card = this.getFirst();
			cardsInPile.remove(0);
			return card;
		} else
			return null;
	}
	
	public Card getFirst() {
		if (!this.empty())
		{
			return cardsInPile.get(0);
		} else
			return null;
	}
	
	public Card getLast() {
		if (!this.empty())
		{
			return cardsInPile.get(cardsInPile.size()-1);
			//return cardsInPile.lastElement();
		} else
			return null;
	}	
	
	public Card pop() {
		if (!this.empty()) {
			Card card = cardsInPile.get(cardsInPile.size()-1);
			//Card card = cardsInPile.lastElement();
			cardsInPile.remove(cardsInPile.size() - 1);
			return card;
		} else
			return null;
	}
	
	public void putFirst(Card card) {
		cardsInPile.add(0, card);
	}

	/*
	@Override
	public boolean contains(Point p) {
		Rectangle rect = new Rectangle(_x, _y, Card.CARD_WIDTH + 100, Card.CARD_HEIGHT * 3);
		return (rect.contains(p));
	} */

	//called in Foundation class
	public void setXY(int x, int y) {
		_x = x;
		_y = y;
		setBounds(_x, _y, Card.CARD_WIDTH + 100, Card.CARD_HEIGHT * 3);
	} 

	public Point getXY() {
		return new Point(_x, _y);
	}

	//this paints the cards onto the tableau/stack spots
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		if (playStack)
		{
			removeAll();
			ListIterator<Card> iter = cardsInPile.listIterator();
			Point prev = new Point(); // positioning relative to the container
			Point prevWhereAmI = new Point();// abs positioning on the board
			if (iter.hasNext())
			{
				Card card = iter.next();
				// this origin is point(0,0) inside the cardstack container
				prev = new Point();// c.getXY(); // starting deck pos
				add(Board.moveCard(card, prev.x, prev.y));
				// setting x & y position
				card.setWhereAmI(getXY());
				prevWhereAmI = getXY();
			} else {
				removeAll();
			}
			for (; iter.hasNext();) {
				Card card = iter.next();
				card.setXY(new Point(prev.x, prev.y + SPREAD));
				add(Board.moveCard(card, prev.x, prev.y + SPREAD));
				prev = card.getXY();
				// setting x & y position
				card.setWhereAmI(new Point(prevWhereAmI.x, prevWhereAmI.y + SPREAD));
				prevWhereAmI = card.getWhereAmI();
			}
		}
	} 
}