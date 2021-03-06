import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.JComponent;


@SuppressWarnings("serial")
class Card extends JComponent  {

    // Variable declarations
	private Suit _suit;
	private Value _value;
	private int rank; // 0-12, to compare against other cards on movement checks
	private Boolean _faceup;
	private Point _location; // location relative to container
	private Point whereAmI; // used to create abs postion rectangle for contains
	private CardPile currentPile;
	private String colour;

	UUID uuid;
	private int x; // used for relative positioning within CardPile Container
	private int y;

	// Aligns text onto drawn cards
	private final int x_offset = 10;
	private final int y_offset = 20;
	private final int new_x_offset = x_offset + (CARD_WIDTH - 30); //distance between Suit & Rank
	
	// Physical card dimensions
	final static public int CARD_HEIGHT = 150;
	final static public int CARD_WIDTH = 100;
	final static public int CORNER_ANGLE = 20;

	Card(Suit suit, Value value)
	{
		_suit = suit;
		_value = value;
		_faceup = false;
		_location = new Point();
		x = 0;
		y = 0;
		_location.x = x;
		_location.y = y;
		whereAmI = new Point();
		if (this._suit == Suit.HEARTS || this._suit == Suit.DIAMONDS) {
		    this.colour = "Red";
		}
		else {
		    this.colour = "Black";
		}
	}
	
	Card()
	{
		_suit = Suit.CLUBS;
		_value = Value.ACE;
		_faceup = false;
		_location = new Point();
		x = 0;
		y = 0;
		_location.x = x;
		_location.y = y;
		whereAmI = new Point();
	}

	// Pulls suit from enum
	public Suit getSuit() {
	    /*
		switch (_suit)
		{
		case HEARTS:
			System.out.println("Hearts");
			break;
		case DIAMONDS:
			System.out.println("Diamonds");
			break;
		case SPADES:
			System.out.println("Spades");
			break;
		case CLUBS:
			System.out.println("Clubs");
			break;
		}
		*/
		return _suit;
	}

	//getting card rank from the enum
	public Value getValue() {
	    /*
		switch (_value) {
		case ACE:
			System.out.println(" Ace");
			break;
		case TWO:
			System.out.println(" 2");
			break;
		case THREE:
			System.out.println(" 3");
			break;
		case FOUR:
			System.out.println(" 4");
			break;
		case FIVE:
			System.out.println(" 5");
			break;
		case SIX:
			System.out.println(" 6");
			break;
		case SEVEN:
			System.out.println(" 7");
			break;
		case EIGHT:
			System.out.println(" 8");
			break;
		case NINE:
			System.out.println(" 9");
			break;
		case TEN:
			System.out.println(" 10");
			break;
		case JACK:
			System.out.println(" Jack");
			break;
		case QUEEN:
			System.out.println(" Queen");
			break;
		case KING:
			System.out.println(" King");
			break;
		}
		*/
		return _value;
	}

	public void setWhereAmI(Point p) {
		whereAmI = p;
	}

	public Point getWhereAmI() {
		return whereAmI;
	}

	public Point getXY() {
		return new Point(x, y);
	}

	public Boolean getFaceStatus() {
		return _faceup;
	}
	
	public int getRank() {
	    return rank;
	}
	
	public String getColour() {
	    return colour;
	}
	
	public String getName() {
	    return getValue() + " of " + getSuit();
	}
	
	public CardPile getCurrentPile() {
	    return currentPile;
	}
	
	public void setCurrentPile(CardPile c) {
	    currentPile = c;
	}
	
	public void setRank(int rank) {
	    this.rank = rank;
	}

	public void setXY(Point p) {
		x = p.x;
		y = p.y;

	}
	
	public UUID getUUID() {
	    return uuid;
	}

	public void setSuit(Suit suit) {
		_suit = suit;
	}

	public void setValue(Value value) {
		_value = value;
	}

	public Card setFaceup() {
		_faceup = true;
		return this;
	}

	public Card setFacedown() {
		_faceup = false;
		return this;
	}

	
	
	
	@Override
	public boolean contains(Point p) {
		Rectangle rect = new Rectangle(whereAmI.x, whereAmI.y, Card.CARD_WIDTH, Card.CARD_HEIGHT);
		return (rect.contains(p));
	}

	private void drawSuit(Graphics2D g, String suit, Color color) {
		g.setColor(color);
		g.drawString(suit, _location.x + x_offset, _location.y + y_offset);
		g.drawString(suit, _location.x + x_offset, _location.y + CARD_HEIGHT - 5);
	}


	private void drawValue(Graphics2D g, String value) {
		g.drawString(value, _location.x + new_x_offset, _location.y + y_offset);
		g.drawString(value, _location.x + new_x_offset, _location.y + y_offset + CARD_HEIGHT - 25);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		RoundRectangle2D rect2 = new RoundRectangle2D.Double(_location.x, _location.y, CARD_WIDTH, CARD_HEIGHT,
				CORNER_ANGLE, CORNER_ANGLE);

		    g2d.setColor(Color.WHITE);
            g2d.fill(rect2);
            g2d.setColor(Color.black);
            g2d.draw(rect2);  

		//If face up, draw the card. 
		if (_faceup) {
			switch (_suit) {
			case HEARTS:
				drawSuit(g2d, "\u2665", Color.RED);
				break;
			case DIAMONDS:
				drawSuit(g2d, "\u2666", Color.RED);
				break;
			case SPADES:
				drawSuit(g2d, "\u2660", Color.BLACK);
				break;
			case CLUBS:
				drawSuit(g2d, "\u2663", Color.BLACK);
				break;
			}
			       
			switch (_value) {
			case ACE:
				drawValue(g2d, "A");
				break;
			case TWO:
				drawValue(g2d, "2");
				break;
			case THREE:
				drawValue(g2d, "3");
				break;
			case FOUR:
				drawValue(g2d, "4");
				break;
			case FIVE:
				drawValue(g2d, "5");
				break;
			case SIX:
				drawValue(g2d, "6");
				break;
			case SEVEN:
				drawValue(g2d, "7");
				break;
			case EIGHT:
				drawValue(g2d, "8");
				break;
			case NINE:
				drawValue(g2d, "9");
				break;
			case TEN:
				drawValue(g2d, "10");
				break;
			case JACK:
				drawValue(g2d, "J");
				break;
			case QUEEN:
				drawValue(g2d, "Q");
				break;
			case KING:
				drawValue(g2d, "K");
				break;
			}
		} else {
		        RoundRectangle2D rect = new RoundRectangle2D.Double(_location.x, _location.y, CARD_WIDTH, CARD_HEIGHT,
                        CORNER_ANGLE, CORNER_ANGLE);
                g2d.setColor(new Color(160,87,168));
                g2d.fill(rect);
                g2d.setColor(Color.black);
                g2d.draw(rect);

		}
	}
}