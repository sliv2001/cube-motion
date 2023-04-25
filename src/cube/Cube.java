package cube;

import java.awt.*;
import java.util.Formatter;

public class Cube {
	
	static final double PIXEL_SCALE=100;
	
	double m=1.0;
	double R=0.10;
	double E=10.0;
	double x=1, y=1;
	double angle=0;
	double vx, vy;
	double spin;
	double energyRatio=0.99;
	
	public Cube() {
		setDynamicParameters();
	}

	private void setDynamicParameters() {
		double eSpin = (1-energyRatio)*E;
		double eMove = energyRatio*E;
		vx = Math.random();
		vy = Math.sqrt(1-Math.pow(vx, 2.0));
		double vMod = Math.sqrt(eMove*2/m);
		vx *= vMod;
		vy *= vMod;
		spin = Math.sqrt(eSpin*12/m/Math.pow(R, 2));
		spin *= Math.random()>0.5? 1 : -1;
	}
	
	public void draw(Graphics g) {
        g.setColor(Color.WHITE);
//        g.fillRect(x, y, (int)(R*PIXEL_SCALE), (int)(R*PIXEL_SCALE));
        int xpoints[]= new int [4];
        int ypoints[] = new int [4];
        for (int i=0; i<4; i++) {
        	xpoints[i] = (int)(x*PIXEL_SCALE+R*PIXEL_SCALE*Math.cos(angle+i*Math.PI/2));
        	ypoints[i] = (int)(y*PIXEL_SCALE+R*PIXEL_SCALE*Math.sin(angle+i*Math.PI/2));
        }
        g.fillPolygon(xpoints, ypoints, 4);
        
        g.setFont(new Font("Courier New", Font.PLAIN, 12));
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        formatter.format("(%f,%f) Speed=(%f,%f)", x, y, vx, vy);
        formatter.close();
        g.drawString(sb.toString(), 20, 30);
        minimizeAngle();
    }

	private void minimizeAngle() {
		if (angle>2*Math.PI)
			angle-=2*Math.PI;
		else if (angle<-2*Math.PI)
			angle+=2*Math.PI;
	}
	
	public void update(int step) {
		angle+=step/1000.0*spin;
		minimizeAngle();
		x+=vx*step/1000.0;
		y+=vy*step/1000.0;
	}
}
