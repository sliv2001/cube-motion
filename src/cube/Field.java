package cube;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Field extends JComponent implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4776364143551654984L;

	public static final int STEP = 100; // duration of an animation frame in milliseconds
	
	Color fieldColor = Color.BLACK;
	Cube cube = new Cube();
	Timer timer = new Timer(STEP, this);

	public Field() {
		super();
		timer.start();
	}
	
	protected void paintComponent(Graphics g) {
		g.setColor(fieldColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		cube.draw(g);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		cube.update(STEP);
		repaint();
	}
}