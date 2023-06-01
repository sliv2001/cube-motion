package cube;

import java.awt.*;
import java.util.Formatter;

public class Cube {

	static final double PIXEL_SCALE = 100;
	static final int MARGIN_Y = 40;
	static final int MARGIN_X = 15;
	static final int PIERCE_THRESHOLD = 10;

	double m = 1.0;
	double R = 2.0;
	double E = 0.50;
	double x = 1, y = 1;
	double angle = 0;
	double vx, vy;
	double spin;
	double energyRatio = 0.5;
	private int xpoints[] = new int[4];
	private int ypoints[] = new int[4];
	private int screenWidth, screenHeight;
	private int pointsPierceDepth[] = new int[4]; //Relational depth of pierce through wall

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
		formatter.format("(%f,%f) Speed=(%f,%f) Angular speed= %f", x, y, vx, vy, spin);
		formatter.close();
		g.drawString(sb.toString(), 20, 30);
		minimizeAngle();
	}

	private int anyPierce() {
		for (int i = 0; i < 4; i++)
			if (pointsPierceDepth[i]>PIERCE_THRESHOLD)
				return i;
		return -1;
	}
	
	private void reflectX(int iterator, boolean isPositive) {
		if (vx > 0 && isPositive)
			vx *= -1;
		if (vx < 0 && !isPositive)
			vx *= -1;
		if (anyPierce() >= 0)
			spin *= -1;
//		pointsPierceDepth[iterator]++;
//		double v = Math.sqrt(vx*vx+vy*vy);
//		vx/=v;
//		vy/=v;
//		double v0 = spin*R*Math.sqrt(2.0)+v;
//		spin = (Math.sqrt(2.0)*m*R*v0-Math.sqrt(2*m*m*R*R*v0*v0-26/6*m*R*R*(m*v0*v0/2-E)));
//		spin/=13/6*m*R*R;
//		v = v0-spin*R*Math.sqrt(2);
//		vx*=v;
//		vy*=v;

	}

	private void reflectY(int iterator, boolean isPositive) {
		if (vy > 0 && isPositive)
			vy *= -1;
		if (vy < 0 && !isPositive)
			vy *= -1;
		if (anyPierce() >= 0)
			spin *= -1;
//		pointsPierceDepth[iterator]++;
//		double v = Math.sqrt(vx*vx+vy*vy);
//		vx/=v;
//		vy/=v;
//		double v0 = spin*R*Math.sqrt(2.0)+v;
//		spin = (Math.sqrt(2.0)*m*R*v0-Math.sqrt(2*m*m*R*R*v0*v0-26/6*m*R*R*(m*v0*v0/2-E)));
//		spin/=13/6*m*R*R;
//		v = v0-spin*R*Math.sqrt(2);
//		vx*=v;
//		vy*=v;
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
			if (xpoints[i] <= 0) {
				reflectX(i, false);
				x-=xpoints[i]/PIXEL_SCALE;
			}
			if (xpoints[i] >= -MARGIN_X + screenWidth) {
				reflectX(i, true);
				x+=(screenWidth-MARGIN_X-xpoints[i])/PIXEL_SCALE;
			}
			if (ypoints[i] <= 0) {
				reflectY(i, false);
				y-=ypoints[i]/PIXEL_SCALE;
			}
			if (ypoints[i] >= -MARGIN_Y + screenHeight) {
				reflectY(i, true);
				y+=(screenHeight-MARGIN_Y-ypoints[i])/PIXEL_SCALE;
			}
			if (xpoints[i] > 0 && xpoints[i] < -MARGIN_X + screenWidth && ypoints[i] > 0
					&& ypoints[i] < -MARGIN_Y + screenHeight)
				pointsPierceDepth[i] = 0;
		}

	}
}
