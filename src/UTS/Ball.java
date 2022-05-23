package UTS;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.*;
import java.util.Random;

public class Ball {
	Random rand = new Random();

	private double x = rand.nextInt(910);
	private double y = rand.nextInt(570);

	private double dx;
	private double dy;

	private boolean moveStatus = false;
	private static boolean newSpawnMove = false;

	private boolean verticalMove;
	private boolean horizontalMove;

	Color color = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));

	private String shape;

	private int XSIZE;
	private int YSIZE;

	Ball(String shape, String large, String direction, String speed) {
		this.shape = shape;

		if (large.equals("large")) {
			XSIZE = 40;
			YSIZE = 40;
		}

		if (large.equals("medium")) {
			XSIZE = 25;
			YSIZE = 25;
		}

		if (large.equals("small")) {
			XSIZE = 15;
			YSIZE = 15;
		}

		if (direction.equals("diagonal"))
			setDiagonalMove();

		if (direction.equals("vertical"))
			setVerticalMove();

		if (direction.equals("horizontal"))
			setHorizontalMove();

		if (speed.equals("fast")) {
			dx = 1.25;
			dy = 1.25;
		}

		if (speed.equals("medium")) {
			dx = 0.40;
			dy = 0.40;
		}

		if (speed.equals("slow")) {
			dx = 0.1;
			dy = 0.1;
		}
	}

	public String getShape() {
		return shape;
	}

	public Color getColor() {
		return color;
	}

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
	
	public boolean getMoveStatus() {
		return moveStatus;
	}

	public void setMoveStatus(boolean moveStatus) {
		this.moveStatus = moveStatus;
	}

	public static boolean getNewSpawnMove() {
		return newSpawnMove;
	}

	public static void setNewSpawnMove(boolean newSpawnMove) {
		Ball.newSpawnMove = newSpawnMove;
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