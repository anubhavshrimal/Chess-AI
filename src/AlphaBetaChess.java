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
		String moveList="";
		/*
		 	checks for each square on the chess board the further possible moves of the chess pieces
			returns the possible move for each piece and adds it to the moveList
		*/
		for (int i=0; i<64; i++) {
            switch (chessBoard[i/8][i%8]) {
                case "P": moveList+=possibleP(i);
                    break;
                case "R": moveList+=possibleR(i);
                    break;
                case "K": moveList+=possibleK(i);
                    break;
                case "B": moveList+=possibleB(i);
                    break;
                case "Q": moveList+=possibleQ(i);
                    break;
                case "A": moveList+=possibleA(i);
                    break;
            }
        }
		return moveList;
	}
	
	public static String possibleP(int i){
		String moveList="";
		
		return moveList;
	}
	
	public static String possibleR(int i){
		String moveList="";
		
		return moveList;
	}
	
	public static String possibleK(int i){
		String moveList="";
		
		return moveList;
	}
	
	public static String possibleB(int i){
		String moveList="";
		
		return moveList;
	}
	
	public static String possibleQ(int i){
		String moveList="";
		
		return moveList;
	}
	
	public static String possibleA(int i){
		String moveList="";
		
		return moveList;
	}

}
