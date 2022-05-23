package BallLocalNet;

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
				});

				try (Scanner in = new Scanner(inStream, "UTF-8")) {
					PrintWriter out = new PrintWriter(new OutputStreamWriter(outStream, "UTF-8"), true /* autoFlush */);

					out.println("Remote Ball:\n");
					out.println("1.Ketik \"add\" untuk menambah bola");
					out.println("2.Ketik \"shape\" untuk mengganti bentuk bola");
					out.println("3.Ketik \"color\" untuk mengganti warna bola");
					out.println("4.Ketik \"move\" untuk menggerakkan bola");
					out.println("5.Ketik \"pause\" untuk memberhentikan bola");
					out.println("6.Ketik \"remove\" untuk menghapus satu bola");
					out.println("7.Ketik \"horizontal\" mengatur arah gerak bola secara horizontal");
					out.println("8.Ketik \"vertical\" mengatur arah gerak bola secara vertikal");
					out.println("9.Ketik \"diagonal\" mengatur arah gerak bola secara diagonal");
					out.println("10.Ketik \"close\" untuk menutup sambungan\n");

					while (in.hasNextLine()) {
						String line = in.nextLine();

						line = line.toLowerCase();
						out.println("input: " + line + "\n");

						if (line.trim().equals("close")) {
							out.println("Remote dimatikan!\n");
							System.exit(0);
						} else if (line.trim().equals("add")) {
							BounceFrame.addBall();
							out.println("Bola ditambahkan!\n");
						} else if (line.trim().equals("pause")) {
							BounceFrame.pauseBall();
							out.println("Pergerakkan bola dihentikan!\n");
						} else if (line.trim().equals("color")) {
							BounceFrame.changeColor();
							out.println("Warna bola diubah!\n");
						} else if (line.trim().equals("shape")) {
							BounceFrame.changeShape();
							out.println("Bentuk bola diubah!\n");
						} else if (line.trim().equals("move")) {
							BounceFrame.runBall();
							out.println("Bola digerakkan!\n");
						} else if (line.trim().equals("remove")) {
							BounceFrame.removeBall();
							out.println("Sebuah bola dihilangkan!\n");
						} else if (line.trim().equals("horizontal")) {
							BounceFrame.moveHorizontal();
							out.println("Arah pergerakkan bola horizontal!\n");
						} else if (line.trim().equals("vertical")) {
							BounceFrame.moveVertical();
							out.println("Arah pergerakkan bola vertikal!\n");
						} else if (line.trim().equals("diagonal")) {
							BounceFrame.moveDiagonal();
							out.println("Arah pergerakkan bola diagonal!\n");
						} else {
							out.println("Input tidak valid, pilih input yang tersedia:\n");
							out.println("1.Ketik \"add\" untuk menambah bola");
							out.println("2.Ketik \"shape\" untuk mengganti bentuk bola");
							out.println("3.Ketik \"color\" untuk mengganti warna bola");
							out.println("4.Ketik \"move\" untuk menggerakkan bola");
							out.println("5.Ketik \"pause\" untuk memberhentikan bola");
							out.println("6.Ketik \"remove\" untuk menghapus satu bola");
							out.println("7.Ketik \"horizontal\" mengatur arah gerak bola secara horizontal");
							out.println("8.Ketik \"vertical\" mengatur arah gerak bola secara vertikal");
							out.println("9.Ketik \"diagonal\" mengatur arah gerak bola secara diagonal");
							out.println("10.Ketik \"close\" untuk menutup sambungan\n");
						}
					}
				}
			}
		}
	}
}

class BounceFrame extends JFrame {
	private static BallComponent comp;
	public static final int DELAY = 1;

	public BounceFrame() {
		setTitle("BallNetwork");
		comp = new BallComponent();
		JPanel buttonPanel = new JPanel();

		// buttonPanel.setLayout(new GridLayout(7, 0));

		/*
		 * addButton(buttonPanel, "Add Ball", event -> addBall());
		 * addButton(buttonPanel, "Remove Ball", event -> removeBall());
		 * addButton(buttonPanel, "Change Color", event -> changeColor());
		 * addButton(buttonPanel, "Change Type", event -> changeShape());
		 * addButton(buttonPanel, "Start", event -> runBall()); addButton(buttonPanel,
		 * "Pause", event -> pauseBall()); addButton(buttonPanel, "Close", event ->
		 * System.exit(0));
		 */

		// add(buttonPanel, BorderLayout.WEST);
		// add(buttonPanel, BorderLayout.SOUTH);

		add(comp, BorderLayout.CENTER);
		pack();
	}

	public void addButton(Container c, String title, ActionListener listener) {
		JButton button = new JButton(title);
		c.add(button);
		button.addActionListener(listener);
	}

	public static void addBall() {
		Ball ball = new Ball();
		comp.add(ball);
		comp.repaint();
	}

	public static void changeShape() {
		comp.setShapes();
		comp.repaint();
	}

	public static void changeColor() {
		comp.setColor();
		comp.repaint();
	}

	public static void removeBall() {
		comp.remove();
		comp.repaint();
	}

	public static void pauseBall() {
		java.util.List<Ball> balls = comp.getBalls();
		for (Ball b : balls) {
			b.setMoveStatus(false);
		}
	}

	public static void moveHorizontal() {
		java.util.List<Ball> balls = comp.getBalls();
		for (Ball b : balls) {
			b.setHorizontalMove();
		}
	}

	public static void moveVertical() {
		java.util.List<Ball> balls = comp.getBalls();
		for (Ball b : balls) {
			b.setVerticalMove();
		}
	}

	public static void moveDiagonal() {
		java.util.List<Ball> balls = comp.getBalls();
		for (Ball b : balls) {
			b.setDiagonalMove();
		}
	}

	public static void runBall() {
		java.util.List<Ball> balls = comp.getBalls();
		for (Ball b : balls) {
			if (b.getMoveStatus() == false) {
				b.setMoveStatus(true);
				b.run(comp, DELAY);
			}
		}
	}
}
