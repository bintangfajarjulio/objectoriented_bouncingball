package UTS;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class BallComponent extends JPanel {
	Random rand = new Random();

	private static final int DEFAULT_WIDTH = 920;
	private static final int DEFAULT_HEIGHT = 620;

	private java.util.List<Ball> balls = new ArrayList<>();

	public void add(Ball ball) {
		balls.add(ball);
	}

	public void remove() {
		if (balls.size() > 0)
			balls.remove(balls.size() - 1);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		BasicStroke stroke = new BasicStroke(5); 

		for (Ball b : balls) {
			g2.setPaint(b.getColor());
			g2.setStroke(stroke);

			if (b.getShape().equals("circle"))
				g2.draw(b.getEllipse());

			if (b.getShape().equals("square"))
				g2.draw(b.getRectangle());
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public java.util.List<Ball> getBalls() {
		return balls;
	}
}
