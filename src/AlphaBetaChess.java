import javax.swing.JFrame;

public class AlphaBetaChess {
	public static void main(String[] args) {
		JFrame f=new JFrame("My title goes here");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UserInterface ui=new UserInterface();
		f.add(ui);
		f.setSize(500, 500);
		f.setVisible(true);
	}

}
