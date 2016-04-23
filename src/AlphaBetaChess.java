import javax.swing.JFrame;

public class AlphaBetaChess {
	
	//array for representation of the chess board
	/*
	 * color=WHITE/black
	 * pawn=P/p
	 * knight=K/k
	 * rook=R/r
	 * bishop=B/b
	 * queen=Q/q
	 * king=A/a	
	*/
	static String chessBoard[][]={
	        {"r","k","b","q","a","b","k","r"},
	        {"p","p","p","p","p","p","p","p"},
	        {" "," "," "," "," "," "," "," "},
	        {" "," "," "," "," "," "," "," "},
	        {" "," "," "," "," "," "," "," "},
	        {" "," "," "," "," "," "," "," "},
	        {"P","P","P","P","P","P","P","P"},
	        {"R","K","B","Q","A","B","K","R"}};
	
	public static void main(String[] args) {
		JFrame f=new JFrame("Chess");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UserInterface ui=new UserInterface();
		f.add(ui);
		f.setSize(500, 500);
		f.setVisible(true);
	}
	
	//returns previous position(row1,column1), current position (row2,column2) and the captured piece if any else space
	public static String possibleMoves(){
		return "";
	}

}
