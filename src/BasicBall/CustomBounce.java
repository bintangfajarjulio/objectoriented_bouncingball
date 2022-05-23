package BasicBall;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*Shows an animated bouncing ball.*/

public class CustomBounce {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JFrame frame = new BounceFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}
}

/* The frame with ball component and buttons */

class BounceFrame extends JFrame {
	private BallComponent comp;
	public static final int STEPS = 1000;
	public static final int DELAY = 1;

	public BounceFrame() {
		setTitle("BounceThread");
		comp = new BallComponent();
		JPanel buttonPanel = new JPanel();
		// buttonPanel.setLayout(new GridLayout(7, 0));
		addButton(buttonPanel, "Add Ball", event -> addBall());
		addButton(buttonPanel, "Remove Ball", event -> removeBall());
		addButton(buttonPanel, "Change Color", event -> changeColor());
		addButton(buttonPanel, "Change Type", event -> changeShape());
		addButton(buttonPanel, "Start", event -> runBall());
		addButton(buttonPanel, "Pause", event -> pauseBall());
		addButton(buttonPanel, "Close", event -> System.exit(0));
		// add(buttonPanel, BorderLayout.WEST);
		add(buttonPanel, BorderLayout.SOUTH);
		add(comp, BorderLayout.CENTER);
		pack();
	}

	public void addButton(Container c, String title, ActionListener listener) {
		JButton button = new JButton(title);
		c.add(button);
		button.addActionListener(listener);
	}

	/* Adds a bouncing ball to the panel and makes it bounce */
	public void addBall() {
		Ball ball = new Ball();
		comp.add(ball);
		comp.repaint();
	}

	public void changeShape() {
		comp.setShapes();
		comp.repaint();
	}

	public void changeColor() {
		comp.setColor();
		comp.repaint();
	}
	
	public void removeBall() {
		comp.remove();
		comp.repaint();
	}
	
	public void pauseBall() {
		java.util.List<Ball> balls = comp.getBalls();
		for (Ball b : balls) {
			b.setMoveStatus(false);
		}
	}

	public void runBall() {
			java.util.List<Ball> balls = comp.getBalls();
			for (Ball b : balls) {
				if(b.getMoveStatus() == false){
					b.setMoveStatus(true);
					b.run(comp, DELAY);
				}
			}
		}
}