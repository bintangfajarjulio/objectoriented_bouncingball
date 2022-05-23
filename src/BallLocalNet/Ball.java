package BallLocalNet;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.*;
import java.util.Random;

public class Ball {
	Random rand = new Random();

	private static final int XSIZE = 15;
	private static final int YSIZE = 15;

	private double x = rand.nextInt(430);
	private double y = rand.nextInt(330);

	private double dx = 1;
	private double dy = 1;

	private boolean moveStatus = false;

	private static boolean verticalMove = true;
	private static boolean horizontalMove = true;

	public void move(Rectangle2D bounds) {
		if (horizontalMove)
			x += dx;

		if (verticalMove)
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

	public Ellipse2D getEllipse() {
		return new Ellipse2D.Double(x, y, XSIZE, YSIZE);
	}

	public Rectangle2D getRectangle() {
		return new Rectangle2D.Double(x, y, XSIZE, YSIZE);
	}

	public RoundRectangle2D getRoundRectangle() {
		return new RoundRectangle2D.Double(x, y, XSIZE, YSIZE, 5, 15);
	}

	public void setMoveStatus(boolean moveStatus) {
		this.moveStatus = moveStatus;
	}

	public boolean getMoveStatus() {
		return moveStatus;
	}

	public void setVerticalMove() {
		verticalMove = true;
		horizontalMove = false;
	}

	public void setHorizontalMove() {
		verticalMove = false;
		horizontalMove = true;
	}

	public void setDiagonalMove() {
		verticalMove = true;
		horizontalMove = true;
	}

	public void run(BallComponent comp, int DELAY) {
		Runnable r = () -> {
			try {
				while (moveStatus) {
					move(comp.getBounds());
					comp.repaint();
					Thread.sleep(DELAY);
				}

			}

			catch (InterruptedException e) {

			}
		};

		Thread t = new Thread(r);

		// if(status)
		t.start();

		// else
		// t.interrupt();
	}
}