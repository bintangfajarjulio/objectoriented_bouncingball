// Ball Move
package BasicBall;

import java.awt.Color;
import java.awt.geom.*;
import java.util.Random;

/*A ball that moves and bounces off the edges of a rectangle*/

public class Ball {
	Random rand = new Random();

	private static final int XSIZE = 15;
	private static final int YSIZE = 15;
	private double x = rand.nextInt(430);
	private double y = rand.nextInt(330);
	private double dx = 1;
	private double dy = 1;
	private boolean status = false;

	public void move(Rectangle2D bounds) {
		x += dx;
		y += dy;

		if (x < bounds.getMinX()) {
			x = bounds.getMinX();
			dx = -dx;
		}

		if (x + XSIZE >= bounds.getMaxX()) {
			x = bounds.getMaxX() - XSIZE;
			dx = -dx;
		}

		if (y < bounds.getMinY()) {
			y = bounds.getMinY();
			dy = -dy;
		}

		if (y + YSIZE >= bounds.getMaxY()) {
			y = bounds.getMaxY() - YSIZE;
			dy = -dy;
		}
	}

	/* Gets the shape of the ball at its current position */
	public Ellipse2D getEllipse() {
		return new Ellipse2D.Double(x, y, XSIZE, YSIZE);
	}

	public Rectangle2D getRectangle() {
		return new Rectangle2D.Double(x, y, XSIZE, YSIZE);
	}
	
	public void setMoveStatus(boolean status) {
		this.status = status;
	}

	public boolean getMoveStatus(){
		return status;
	}

	public void run(BallComponent comp, int DELAY) {		
		Runnable r = () -> {
			try {
				while (status) {
					move(comp.getBounds());
					comp.repaint();
					Thread.sleep(DELAY);
				}

			} catch (InterruptedException e) {
				
			}
		};

		Thread t = new Thread(r);

		// if(status)
			t.start();
		
		// else
			// t.interrupt();
	}
}