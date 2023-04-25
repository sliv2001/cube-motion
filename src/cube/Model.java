package cube;

import javax.swing.*;

public class Model extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6143221428621650296L;
	Field field;
	    
    public Model() {
        // Create our frame
    	
        super("Model of absolutely resilient cube");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
        setResizable(false);
        try {
    		this.field = new Field(this.getWidth(), this.getHeight());
    	} catch (Throwable err) {
    		
    	}
        
        setupField();
    }

    private void setupField() {
        add(field);
    }

    public static void main(String args[]) {
    	Model model = new Model();
        model.setVisible(true);
    }
}
