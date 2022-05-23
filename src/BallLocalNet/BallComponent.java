package BallLocalNet;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class BallComponent extends JPanel {
	Random rand = new Random();

	private static final int DEFAULT_WIDTH = 450;
	private static final int DEFAULT_HEIGHT = 350;

	private static int red = 0;
	private static int green = 0;
	private static int blue = 0;

	private static int indexShapes = 0;

	private java.util.List<Ball> balls = new ArrayList<>();

	public void add(Ball b) {
		balls.add(b);
	}

	public void remove() {
		try {
			int index = balls.size() - 1;
			balls.remove(index);
		}

		catch (Exception e) {

		}
	}

	public void setShapes() {
		indexShapes++;
	}

	public Color getColor() {
		return new Color(red, green, blue);
	}

	public void setColor() {
		red = rand.nextInt(255);
		green = rand.nextInt(255);
		blue = rand.nextInt(255);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		if (indexShapes > 2)
			indexShapes = 0;

		g2.setPaint(getColor());

		for (Ball b : balls) {
			if (indexShapes == 0)
				g2.fill(b.getEllipse());
			if (indexShapes == 1)
				g2.fill(b.getRectangle());
			if (indexShapes == 2)
				g2.fill(b.getRoundRectangle());
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public java.util.List<Ball> getBalls() {
		return balls;
	}
}
