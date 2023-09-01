import javax.swing.Timer;
import javax.swing.JFrame;

public class Game extends JFrame {
	public Game() {
		super();

		setTitle("Java Pong");
		setSize(740, 580);

		Pong content = new Pong(Player.KEYBOARD, Player.CPU_EASY);
		content.acceleration = true;
		getContentPane().add(content);

		addMouseListener(content);
		addKeyListener(content);

		Timer timer = new Timer(20, content);
		timer.start();
	}
}
