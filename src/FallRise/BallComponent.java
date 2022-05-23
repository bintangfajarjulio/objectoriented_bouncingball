package FallRise;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class BallComponent extends JPanel {
	private static final int DEFAULT_WIDTH = 900;
	private static final int DEFAULT_HEIGHT = 700;

	private java.util.List<Ball> balls = new ArrayList<>();

	public void add(Ball b) {
		balls.add(b);
	}

	public void remove() {
		balls.remove(0);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		for (Ball b : balls) {
			g2.setPaint(b.getColor());
			g2.fill(b.getEllipse());
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public java.util.List<Ball> getBalls() {
		return balls;
	}
}
