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

	static int kingPositionA, kingPositiona;	//white and black king's position in the array
	
	public static void main(String[] args) {
		/*JFrame f=new JFrame("Chess");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UserInterface ui=new UserInterface();
		f.add(ui);
		f.setSize(500, 500);
		f.setVisible(true);*/
		System.out.println(possibleMoves());
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
		String moveList="",oldPiece;
		int kingTemp;
		int r=i/8,c=i%8;	//row and column position of the king
		for(int j=0;j<9;j++){
			if(j!=4){		//avoid checking for the place where king is already placed
				try {
					if(Character.isLowerCase(chessBoard[r-1+j/3][c-1+j%3].charAt(0)) || " ".equals(chessBoard[r-1+j/3][c-1+j%3])){
						oldPiece=chessBoard[r-1+j/3][c-1+j%3];
						chessBoard[r][c]=" ";
						chessBoard[r-1+j/3][c-1+j%3]="A";
						kingTemp=kingPositionA;	
						kingPositionA=i+(j/3)*8+j%3-9;
						if(kingSafe()){
							moveList=moveList+r+c+(r-1+j/3)+(c-1+j%3)+oldPiece;
						}
						chessBoard[r][c]="A";
						chessBoard[r-1+j/3][c-1+j%3]=oldPiece;
						kingPositionA=kingTemp;
					}						
				} catch (Exception e) {
						
				}			
			}
		}
		//need to add castling
		return moveList;
	}
	
	public static boolean kingSafe(){
		
		return true;
	}

}
