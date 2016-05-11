
public class Rating {

	public static int rating(int movesAvailableCount,int depth){
		int rating=0;
		
		rating+=rateAttack()+ratePieceAvailability()+rateMovability(movesAvailableCount,depth)+ratePositional();
		AlphaBetaChess.flipBoard();
		rating-=rateAttack()-ratePieceAvailability()-rateMovability(movesAvailableCount,depth)-ratePositional();
		AlphaBetaChess.flipBoard();
		return -(rating+depth*50);
	}
	
	public static int rateAttack(){
		int rating=0;
		int tempKingPos=AlphaBetaChess.kingPositionA;
		
        for (int i=0;i<64;i++) {
            switch (AlphaBetaChess.chessBoard[i/8][i%8]) {
                case "P": AlphaBetaChess.kingPositionA=i;             
	                	if(!AlphaBetaChess.kingSafe())
	                		rating-=64;
	                    break;
                case "R": AlphaBetaChess.kingPositionA=i;             
		            	if(!AlphaBetaChess.kingSafe())
		            		rating-=500;
		                break;                    
                case "K": AlphaBetaChess.kingPositionA=i;             
		            	if(!AlphaBetaChess.kingSafe())
		            		rating-=300;
                    break;
                case "B": AlphaBetaChess.kingPositionA=i;             
		            	if(!AlphaBetaChess.kingSafe())
		            		rating-=300;
                    break;
                case "Q": AlphaBetaChess.kingPositionA=i;             
		            	if(!AlphaBetaChess.kingSafe())
		            		rating-=900;
                    break;
            }
        }
        AlphaBetaChess.kingPositionA=tempKingPos;
        if(!AlphaBetaChess.kingSafe())
        	rating-=200;
        return rating/2;
	}
	
	//rates according to the chess pieces availability on the board 
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
	
	//rates according to the flexibilty in possible moves
	public static int rateMovability(int movesAvailableCount,int depth){
		int rating=0;
		rating+=movesAvailableCount;	//5 points for each possible move
		if(movesAvailableCount==0){	//checkmate or stalemate condition
			if(!AlphaBetaChess.kingSafe()){	//checkmate
				rating+=-200000*depth;
			}else {	//stalemate
				rating+=-150000*depth;
			}
		}
		return rating;
	}
	
	public static int ratePositional(){
		return 0;
	}
}
