
public class Rating {

	public static int rating(int moveList,int depth){
		int rating=0; 
		rating+=rateAttack()+ratePieceAvailability()+rateMovability()+ratePositional();
		AlphaBetaChess.flipBoard();
		rating-=rateAttack()-ratePieceAvailability()-rateMovability()-ratePositional();
		AlphaBetaChess.flipBoard();
		return -(rating+depth*50);
	}
	
	public static int rateAttack(){
		return 0;
	}
	
	public static int ratePieceAvailability(){
		int rating=0, bishopCount=0;
        for (int i=0;i<64;i++) {
            switch (AlphaBetaChess.chessBoard[i/8][i%8]) {
                case "P": rating+=100;
                    break;
                case "R": rating+=500;
                    break;
                case "K": rating+=300;
                    break;
                case "B": bishopCount++;
                    break;
                case "Q": rating+=900;
                    break;
            }
        }
        if (bishopCount>=2) {
        	rating+=300*bishopCount;
        } else {
            if (bishopCount==1) {rating+=250;}
        }
        return rating;
	}
	
	public static int rateMovability(){
		return 0;
	}
	
	public static int ratePositional(){
		return 0;
	}
}
