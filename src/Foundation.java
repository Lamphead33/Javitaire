import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;

/*
 * Foundation 
 */

class Foundation extends CardPile {
    Foundation found = this;

	//
	private static final long serialVersionUID = 4962548240071318600L;

	public Foundation() {
		super(false);
	}

	
	@Override
	public void setXY(int x, int y) {
		_x = x;
		_y = y;
		setBounds(_x, _y, Card.CARD_WIDTH + 10, Card.CARD_HEIGHT + 10);
	}

	
	@Override
	public boolean contains(Point p) {
		Rectangle rect = new Rectangle(_x, _y, Card.CARD_WIDTH + 10, Card.CARD_HEIGHT + 10);
		return (rect.contains(p));
	} 

	/*
	 * This draws the "ace" stacks, where the foundation decks go
	 */
	@Override
	protected void paintComponent(Graphics g) {
		removeAll();
		if (!empty()) {
			add(Board.moveCard(this.getLast(), 1, 1));
		} else {
			// draw the stack area for the ACE cards
			Graphics2D g2d = (Graphics2D) g;
			RoundRectangle2D rect = new RoundRectangle2D.Double(0, 0, Card.CARD_WIDTH, Card.CARD_HEIGHT,
					Card.CORNER_ANGLE, Card.CORNER_ANGLE);
			g2d.setColor(Color.RED); //we can change this to be an image or something? this is where the ACES stack
			g2d.fill(rect);
			g2d.setColor(Color.black);
			g2d.draw(rect);
		}
	}
	
	
	
	
	
	/*
	 * Not sure what this is so I'm gonna leave it just in case
	 * 
	  
	  Clicker c;
	  
	   public class Clicker extends JButton {
        Foundation f;
        public Clicker(Foundation f) {
            this.f = f;
        }
    }
    
    public void createClicker() {
        Clicker c = new Clicker(found);
    }
    
    public Clicker getClicker() {
        return this.c;
    }
	 
	 *
	 */
}
