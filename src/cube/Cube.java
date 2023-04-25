package cube;

import java.awt.*;
import java.util.Formatter;

public class Cube {

	static final double PIXEL_SCALE = 100;
	static final int MARGIN_Y = 40;
	static final int MARGIN_X = 15;

	double m = 1.0;
	double R = 0.10;
	double E = 1.0;
	double x = 1, y = 1;
	double angle = 0;
	double vx, vy;
	double spin;
	double energyRatio = 0.99;
	private int xpoints[] = new int[4];
	private int ypoints[] = new int[4];
	private int screenWidth, screenHeight;

	public Cube(int screenWidth, int screenHeight) {
		setDynamicParameters();
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
	}

	private void setDynamicParameters() {
		double eSpin = (1 - energyRatio) * E;
		double eMove = energyRatio * E;
		vx = Math.random();
		vy = Math.sqrt(1 - Math.pow(vx, 2.0));
		double vMod = Math.sqrt(eMove * 2 / m);
		vx *= vMod;
		vy *= vMod;
		spin = Math.sqrt(eSpin * 12 / m / Math.pow(R, 2));
		spin *= Math.random() > 0.5 ? 1 : -1;
	}

	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
//        g.fillRect(x, y, (int)(R*PIXEL_SCALE), (int)(R*PIXEL_SCALE));
		g.fillPolygon(xpoints, ypoints, 4);

		g.setFont(new Font("Courier New", Font.PLAIN, 12));
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		formatter.format("(%f,%f) Speed=(%f,%f)", x, y, vx, vy);
		formatter.close();
		g.drawString(sb.toString(), 20, 30);
		minimizeAngle();
	}

	private void reflectX(int iterator) {
		vx*=-1;
	}

	private void reflectY(int iterator) {
		vy*=-1;
	}

	private void minimizeAngle() {
		if (angle > 2 * Math.PI)
			angle -= 2 * Math.PI;
		else if (angle < -2 * Math.PI)
			angle += 2 * Math.PI;
	}

	public void update(int step) {
		angle += step / 1000.0 * spin;
		minimizeAngle();
		x += vx * step / 1000.0;
		y += vy * step / 1000.0;

		for (int i = 0; i < 4; i++) {
			xpoints[i] = (int) (x * PIXEL_SCALE + R * PIXEL_SCALE * Math.cos(angle + i * Math.PI / 2));
			ypoints[i] = (int) (y * PIXEL_SCALE + R * PIXEL_SCALE * Math.sin(angle + i * Math.PI / 2));
		}
		for (int i = 0; i < 4; i++) {
			if (xpoints[i] <= 0 || xpoints[i] >= -MARGIN_X + screenWidth)
				reflectX(i);
			if (ypoints[i] <= 0 || ypoints[i] >= -MARGIN_Y + screenHeight)
				reflectY(i);
		}
	}
}
