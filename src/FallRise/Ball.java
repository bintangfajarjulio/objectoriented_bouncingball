package FallRise;

import java.awt.Color;
import java.awt.geom.*;
import java.util.Random;

public class Ball {
	Random rand = new Random();

	private final int XSIZE = getSize();
	private final int YSIZE = XSIZE;

	private double x = rand.nextInt(900);
	private double y = rand.nextInt(700);

	private double dy = 1;

	private boolean moveStatus = false;

	private static boolean riseMove = false;
	private static boolean fallMove = false;

	Color color = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));

	public void move(Rectangle2D bounds) {
		if (riseMove) {
			y -= dy;
			if (y + YSIZE <= bounds.getMinY())
				y = 700;
		}

		if (fallMove) {
			y += dy;
			if (y + YSIZE >= bounds.getMaxY())
				y = 0;
		}
	}

	public Ellipse2D getEllipse() {
		return new Ellipse2D.Double(x, y, XSIZE, YSIZE);
	}

	public int getSize() {
		int ballSize = rand.nextInt(20);
		boolean resize = true;

		while (resize) {
			if (ballSize >= 5) {
				resize = false;
			}

			else {
				ballSize = rand.nextInt(20);
				resize = true;
			}
		}

		return ballSize;
	}

	public Color getColor() {
		return color;
	}

	public void setRiseMove() {
		fallMove = false;
		riseMove = true;
	}

	public void setFallMove() {
		riseMove = false;
		fallMove = true;
	}

	public void setMoveStatus(boolean moveStatus) {
		this.moveStatus = moveStatus;
	}

	public boolean getMoveStatus() {
		return moveStatus;
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
		t.start();
	}
}