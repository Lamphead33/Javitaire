import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Card extends JPanel {
    private String rank;
    private String suit;
    private int rankId;
    private String colour;
    private boolean faceDown;
    private CardPile location;
    final static public int CARD_HEIGHT = 150;
	final static public int CARD_WIDTH = 100;
	private String imageName;
	private BufferedImage activeImage;
	Point positionOffset;
    
    
    // Constructor - upon creation, a rank and suit are assigned
    public Card(int rankId, String suit) {
        this.rankId = rankId;
        this.suit = suit;
        this.faceDown = true;
        if (suit.equals("S") || suit.equals("C")) {
            colour = "Black";
        }
        else {
            colour = "Red";
        }
        
        switch (rankId) {
            case 1:
                this.rank = "A";
                break;
            case 2:
                this.rank = "2";
                break;
            case 3:
                this.rank = "3";
                break;
            case 4:
                this.rank = "4";
                break;
            case 5:
                this.rank = "5";
                break;
            case 6:
                this.rank = "6";
                break;
            case 7:
                this.rank = "7";
                break;
            case 8:
                this.rank = "8";
                break;
            case 9:
                this.rank = "9";
                break;
            case 10:
                this.rank = "10";
                break;
            case 11:
                this.rank = "J";
                break;
            case 12:
                this.rank = "Q";
                break;
            case 13:
                this.rank = "K";
                break;
        }   
        
        // Sets default image name
        this.imageName = (this.rank + this.suit + ".svg");
        
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
        return this.rank + this.suit;
    }
    
    public String getColour() {
        return this.colour;
    }
    
    public boolean isFaceDown() {
        return this.faceDown;
    }
    
    public BufferedImage getImage() {
        return activeImage;
    }
    
    public CardPile getPile() {
        return this.location;
    }
    
    // Setters 
    public void setFaceUp() {
        this.faceDown = false;
        String dir = System.getProperty("user.dir");
        try {
        	activeImage = ImageIO.read(new File(dir + "/CardImages/" + this.imageName));
			
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        positionOffset = new Point(0,0);
		setSize(new Dimension(20, 45));
		setOpaque(false);
    }
    
    public void setFaceDown() {
        this.faceDown = true;
        String dir = System.getProperty("user.dir");
        try {
			activeImage = ImageIO.read(new File(dir + "/CardImages/" + this.imageName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void setLocation(CardPile c) {
        this.location = c;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	BufferedImage img = activeImage;
    	g.drawImage(img, 0, 0, activeImage.getHeight(), activeImage.getWidth(), null);
    }
}