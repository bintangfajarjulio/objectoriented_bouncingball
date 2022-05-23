package FallRise;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class CustomBounce {
	public static void main(String[] args) throws IOException {
		try (ServerSocket s = new ServerSocket(6789)) {
			try (Socket incoming = s.accept()) {
				InputStream inStream = incoming.getInputStream();
				OutputStream outStream = incoming.getOutputStream();

				EventQueue.invokeLater(() -> {
					JFrame frame = new BounceFrame();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
					frame.setResizable(false);
				});

				try (Scanner in = new Scanner(inStream, "UTF-8")) {
					PrintWriter out = new PrintWriter(new OutputStreamWriter(outStream, "UTF-8"), true);

					out.println("Command: Bye or Sky");
					out.println("Sky [fall|rise] n");

					while (in.hasNextLine()) {
						String line = in.nextLine();
						line = line.toLowerCase();
						try {

							Scanner lineScan = new Scanner(line);

							String sky = lineScan.next();
							String command = lineScan.next();
							int ballCount = lineScan.nextInt();

							if (sky.equals("sky") && command.equals("fall")) {
								BounceFrame.fallBall(ballCount);
							}

							else if (sky.equals("sky") && command.equals("rise")) {
								BounceFrame.riseBall(ballCount);
							}

							else {
								out.println("Wrong Command");
							}
						}

						catch (Exception e) {
							if (line.trim().equals("bye")) {
								System.exit(0);
							}

							else {
								out.println("Wrong Command");
							}
						}
					}
				}
			}
		}
	}
}

class BounceFrame extends JFrame {
	private static BallComponent comp;
	public static final int DELAY = 8;
	Random rand = new Random();
	int gridy = 0;

	GridBagConstraints gbc = new GridBagConstraints();

	public BounceFrame() {
		setTitle("BallFall&Rise");
		comp = new BallComponent();
		JPanel buttonPanel = new JPanel();

		buttonPanel.setLayout(new GridBagLayout());

		addButton(buttonPanel, "Sky Fall", 10, event -> fallBall(getBallCount()));
		addButton(buttonPanel, "Close", 600, event -> System.exit(0));
		addButton(buttonPanel, "Sky Rising", 10, event -> riseBall(getBallCount()));

		add(buttonPanel, BorderLayout.EAST);
		add(comp, BorderLayout.CENTER);
		pack();
	}

	public void addButton(Container c, String title, int ipady, ActionListener listener) {
		JButton button = new JButton(title);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 20;
		gbc.ipady = ipady;
		gbc.gridy = ++gridy;

		c.add(button, gbc);
		button.addActionListener(listener);
	}

	public int getBallCount() {
		int ballCount = rand.nextInt(150);
		boolean recount = true;

		while (recount) {
			if (ballCount > 0) {
				recount = false;
			}

			else {
				ballCount = rand.nextInt(150);
				recount = true;
			}
		}

		return ballCount;
	}

	public static void fallBall(int ballCount) {
		java.util.List<Ball> balls = comp.getBalls();

		while (balls.size() > 0)
			comp.remove();

		while (balls.size() < ballCount) {
			Ball ball = new Ball();
			comp.add(ball);
		}

		for (Ball b : balls) {
			b.setFallMove();
			if (b.getMoveStatus() == false) {
				b.setMoveStatus(true);
				b.run(comp, DELAY);
			}
		}

	}

	public static void riseBall(int ballCount) {
		java.util.List<Ball> balls = comp.getBalls();

		while (balls.size() > 0)
			comp.remove();

		while (balls.size() < ballCount) {
			Ball ball = new Ball();
			comp.add(ball);
		}

		for (Ball b : balls) {
			b.setRiseMove();
			if (b.getMoveStatus() == false) {
				b.setMoveStatus(true);
				b.run(comp, DELAY);
			}
		}
	}

}
