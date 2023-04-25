package cube;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Field extends JComponent implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4776364143551654984L;

	public static final int STEP = 5; // time quantum
	public static final int ANIMATION_STEP = 10;

	Color fieldColor = Color.BLACK;
	Cube cube;
	Timer timer = new Timer(ANIMATION_STEP, this);

	public Field(int screenWidth, int screenHeight) {
		super();
		
		cube = new Cube(screenWidth, screenHeight);
		
		Thread motion = new Thread() {
			public void run() {
				double nextTick = System.currentTimeMillis();
				while (true) {
					while (System.currentTimeMillis() > nextTick) {
						cube.update(STEP);
						nextTick+=(STEP);
					}
				}
			}
		};
		motion.start();
		timer.start();
	}

	protected void paintComponent(Graphics g) {
		g.setColor(fieldColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		cube.draw(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

}