import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		Game pongGame = new Game();
		pongGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pongGame.setVisible(true);
	}
}
