import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

public class Pong extends JPanel implements ActionListener, MouseListener, KeyListener {

	private static final int START_SPEED = 9;
	private static final int RADIUS = 10;
	private static final int ACCELERATION = 125;

	private static final int PADDING = 10;
	private static final int HEIGHT = 50;
	private static final int WIDTH = 20;
	private static final int SPEED = 12;
	private static final int TOLERANCE = 5;

	private Player playerOne;
	private Player playerTwo;

	private boolean new_game = true;

	private int ball_x;
	private int ball_y;
	private double ball_x_speed;
	private double ball_y_speed;

	public boolean acceleration = false;
	private int ball_acceleration_count;

	private boolean mouse_inside = false;
	private boolean key_up = false;
	private boolean key_down = false;

	public Pong(int p1Type, int p2Type) {
		super();
		setBackground(new Color(0, 0, 139));

		playerOne = new Player(p1Type);
		playerTwo = new Player(p2Type);
	}

	private void computeDestination(Player player) {
		if (ball_x_speed > 0)
			player.destination = ball_y
					+ (getWidth() - PADDING - WIDTH - RADIUS - ball_x) * (int) (ball_y_speed) / (int) (ball_x_speed);
		else
			player.destination = ball_y
					- (ball_x - PADDING - WIDTH - RADIUS) * (int) (ball_y_speed) / (int) (ball_x_speed);

		if (player.destination <= RADIUS)
			player.destination = 2 * PADDING - player.destination;

		if (player.destination > getHeight() - 10) {
			player.destination -= RADIUS;
			if ((player.destination / (getHeight() - 2 * RADIUS)) % 2 == 0)
				player.destination = player.destination % (getHeight() - 2 * RADIUS);
			else
				player.destination = getHeight() - 2 * RADIUS - player.destination % (getHeight() - 2 * RADIUS);
			player.destination += RADIUS;
		}
	}

	private void movePlayer(Player player, int destination) {
		int distance = Math.abs(player.position - destination);

		if (distance != 0) {
			int direction = -(player.position - destination) / distance;

			if (distance > SPEED)
				distance = SPEED;

			player.position += direction * distance;

			if (player.position - HEIGHT < 0)
				player.position = HEIGHT;
			if (player.position + HEIGHT > getHeight())
				player.position = getHeight() - HEIGHT;
		}
	}

	private void computePosition(Player player) {

		if (player.getType() == Player.MOUSE) {
			if (mouse_inside) {
				int cursor = getMousePosition().y;
				movePlayer(player, cursor);
			}
		} else if (player.getType() == Player.KEYBOARD) {
			if (key_up && !key_down) {
				movePlayer(player, player.position - SPEED);
			} else if (key_down && !key_up) {
				movePlayer(player, player.position + SPEED);
			}
		} else if (player.getType() == Player.CPU_HARD) {
			movePlayer(player, player.destination);
		} else if (player.getType() == Player.CPU_EASY) {
			movePlayer(player, ball_y);
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (new_game) {
			ball_x = getWidth() / 2;
			ball_y = getHeight() / 2;

			double phase = Math.random() * Math.PI / 2 - Math.PI / 4;
			ball_x_speed = (int) (Math.cos(phase) * START_SPEED);
			ball_y_speed = (int) (Math.sin(phase) * START_SPEED);

			ball_acceleration_count = 0;

			if (playerOne.getType() == Player.CPU_HARD || playerOne.getType() == Player.CPU_EASY) {
				playerOne.position = getHeight() / 2;
				computeDestination(playerOne);
			}
			if (playerTwo.getType() == Player.CPU_HARD || playerTwo.getType() == Player.CPU_EASY) {
				playerTwo.position = getHeight() / 2;
				computeDestination(playerTwo);
			}

			new_game = false;
		}

		if (playerOne.getType() == Player.MOUSE || playerOne.getType() == Player.KEYBOARD || ball_x_speed < 0)
			computePosition(playerOne);
		if (playerTwo.getType() == Player.MOUSE || playerTwo.getType() == Player.KEYBOARD || ball_x_speed > 0)
			computePosition(playerTwo);

		ball_x += ball_x_speed;
		ball_y += ball_y_speed;
		if (ball_y_speed < 0)
			ball_y++;
		if (acceleration) {
			ball_acceleration_count++;
			if (ball_acceleration_count == ACCELERATION) {
				ball_x_speed = ball_x_speed
						+ (int) ball_x_speed / Math.hypot((int) ball_x_speed, (int) ball_y_speed) * 2;
				ball_y_speed = ball_y_speed
						+ (int) ball_y_speed / Math.hypot((int) ball_x_speed, (int) ball_y_speed) * 2;
				ball_acceleration_count = 0;
			}
		}

		if (ball_x <= PADDING + WIDTH + RADIUS) {
			int collision_point = ball_y + (int) (ball_y_speed / ball_x_speed * (PADDING + WIDTH + RADIUS - ball_x));
			if (collision_point > playerOne.position - HEIGHT - TOLERANCE &&
					collision_point < playerOne.position + HEIGHT + TOLERANCE) {
				ball_x = 2 * (PADDING + WIDTH + RADIUS) - ball_x;
				ball_x_speed = Math.abs(ball_x_speed);
				ball_y_speed -= Math.sin((double) (playerOne.position - ball_y) / HEIGHT * Math.PI / 4)
						* Math.hypot(ball_x_speed, ball_y_speed);
				if (playerTwo.getType() == Player.CPU_HARD)
					computeDestination(playerTwo);
			} else {
				playerTwo.points++;
				new_game = true;
			}
		}
		if (ball_x >= getWidth() - PADDING - WIDTH - RADIUS) {
			int collision_point = ball_y
					- (int) (ball_y_speed / ball_x_speed * (ball_x - getWidth() + PADDING + WIDTH + RADIUS));
			if (collision_point > playerTwo.position - HEIGHT - TOLERANCE &&
					collision_point < playerTwo.position + HEIGHT + TOLERANCE) {
				ball_x = 2 * (getWidth() - PADDING - WIDTH - RADIUS) - ball_x;
				ball_x_speed = -1 * Math.abs(ball_x_speed);
				ball_y_speed -= Math.sin((double) (playerTwo.position - ball_y) / HEIGHT * Math.PI / 4)
						* Math.hypot(ball_x_speed, ball_y_speed);
				if (playerOne.getType() == Player.CPU_HARD)
					computeDestination(playerOne);
			} else {
				playerOne.points++;
				new_game = true;
			}
		}

		if (ball_y <= RADIUS) {
			ball_y_speed = Math.abs(ball_y_speed);
			ball_y = 2 * RADIUS - ball_y;
		}
		if (ball_y >= getHeight() - RADIUS) {
			ball_y_speed = -1 * Math.abs(ball_y_speed);
			ball_y = 2 * (getHeight() - RADIUS) - ball_y;
		}

		g.setColor(Color.YELLOW);

		g.fillRect(PADDING, playerOne.position - HEIGHT, WIDTH, HEIGHT * 2);
		g.fillRect(getWidth() - PADDING - WIDTH, playerTwo.position - HEIGHT, WIDTH, HEIGHT * 2);
		g.fillOval(ball_x - RADIUS, ball_y - RADIUS, RADIUS * 2, RADIUS * 2);

		g.drawString(playerOne.points + " ", getWidth() / 2 - 20, 20);
		g.drawString(playerTwo.points + " ", getWidth() / 2 + 20, 20);
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	public void mouseEntered(MouseEvent e) {
		mouse_inside = true;
	}

	public void mouseExited(MouseEvent e) {
		mouse_inside = false;
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP)
			key_up = true;
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
			key_down = true;
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP)
			key_up = false;
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
			key_down = false;
	}

	public void keyTyped(KeyEvent e) {
	}
}
