import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

public class FeedbackWindow extends JFrame {

	private static final long serialVersionUID = 1L;
    //private JFrame rulesFrame = new JFrame();
    private JTextPane rulesText = new JTextPane();
    
    public FeedbackWindow() {
        super("Send Feedback");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(700, 500);
        
        rulesText.setContentType("text/html");
        rulesText.setEditable(false);
        rulesText.setFocusable(false);
        JScrollPane scroll = new JScrollPane(rulesText);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        this.rulesText.setText(getRules());
        
        this.add(scroll);
        
        this.setVisible(true);
        
        
    }
    
    
    private String getRules() {
        return "<html>"
        		+ "<form action=\"/action_page.php\">"
        		+ "<b><u>SEND US YOUR FEEDBACK</b></u><br>"    				+ 
        		"<label for='fname'>First name:</label><br>"      			+ 
        		"<input type='text' id='fname' name='fname'><br>" 			+
        		"<label for='lname'>Last name:</label><br>"		  			+
        		"<input type='text' id='lname' name='lname'><br>" 			+
        		"<label for='lname'>Email Address:</label><br>"	  			+
        		"<input type='text' id='email'><br>" 						+
        		"<label for='lname'>Leave us feedback here:</label><br>"	+
        		"<input type='text' id='message'><br>" 						+
        		"<input type='submit' value='Submit'><br>"		  			+
        		"</form>" 													+
                "</html>" 													+ 
                "";
    }
}
