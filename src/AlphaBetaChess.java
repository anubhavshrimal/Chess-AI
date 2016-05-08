import java.util.Arrays;
import java.util.Scanner;

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
	static int globalDepth=4;
	
	public static void main(String[] args) {
		//get initial position of white and black king
		while(!"A".equals(chessBoard[kingPositionA/8][kingPositionA%8]))	kingPositionA++;
		while(!"a".equals(chessBoard[kingPositiona/8][kingPositiona%8]))	kingPositiona++;
		/*JFrame f=new JFrame("Chess");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UserInterface ui=new UserInterface();
		f.add(ui);
		f.setSize(500, 500);
		f.setVisible(true);*/		
		System.out.println(possibleMoves());
		makeMove("7657 ");
		undoMove("7657 ");
		for(int i=0;i<8;i++)
			System.out.println(Arrays.toString(chessBoard[i]));
	}
	
	//returns a String with move , captured piece and a score value of that move
	public static String alphaBeta(int depth, int beta, int alpha, String move, int player){
		String moveList=possibleMoves();
		if(depth==0 || moveList.length()==0)	//limiting cases if searching depth becomes 0 or no possible moves left (eg. checkmate)
			return move+(rating()*(player*2-1));
		
		player=1-player;	//1 or 0
		
		String returnedStr="";
		int ratingValue;
		for(int i=0;i<moveList.length();i+=5){
			makeMove(moveList.substring(i, i+5));
			flipBoard();
			returnedStr=alphaBeta(depth-1, beta, alpha, moveList.substring(i,i+5), player); //recursive call for more depth
			ratingValue=Integer.valueOf(returnedStr.substring(5));	//get the value of the rating from the depth
			flipBoard();
			undoMove(moveList.substring(i,i+5));
			
			if(player==0){
				if(ratingValue<=beta){
					beta=ratingValue;
					if(depth==globalDepth)
						move=returnedStr.substring(0,5);	
				}
				else{
					if(ratingValue>alpha){
						alpha=ratingValue;
						if(depth==globalDepth)
								move=returnedStr.substring(0,5);					
					}
					if(alpha>=beta){
						if(player==0){
							return move+beta;
						}
						else{
							return move+alpha;
						}
					}
				}
			}
		}
		if(player==0){
			return move+beta;
		}
		else{
			return move+alpha;
		}
	}
	
	public static void flipBoard(){
		
	}
	
	//returns the rating of the move made
	public static int rating(){
		System.out.print("Enter the score: ");
		Scanner sc=new Scanner(System.in);
		return sc.nextInt();
	}
	
	public static void makeMove(String move){
		if(move.charAt(4)!='P'){
			//old position in array
			int x1=Character.getNumericValue(move.charAt(0));
			int y1=Character.getNumericValue(move.charAt(1));
			//new position in array
			int x2=Character.getNumericValue(move.charAt(2));
			int y2=Character.getNumericValue(move.charAt(3));
			
			chessBoard[x2][y2]=chessBoard[x1][y1];
			chessBoard[x1][y1]=" ";
		}
		else{	//if pawn promotion
			int col1=Character.getNumericValue(move.charAt(0));
			int col2=Character.getNumericValue(move.charAt(1));
			chessBoard[1][col1]=" ";
			chessBoard[0][col2]=String.valueOf(move.charAt(3));	//new piece value		
		}
	}
	
	public static void undoMove(String move){
		if(move.charAt(4)!='P'){
			//old position in array
			int x1=Character.getNumericValue(move.charAt(0));
			int y1=Character.getNumericValue(move.charAt(1));
			//new position in array
			int x2=Character.getNumericValue(move.charAt(2));
			int y2=Character.getNumericValue(move.charAt(3));
			
			chessBoard[x1][y1]=chessBoard[x2][y2];
			chessBoard[x2][y2]=String.valueOf(move.charAt(4));	//bring back the captured piece on board
		}
		else{	//if pawn promotion
			int col1=Character.getNumericValue(move.charAt(0));
			int col2=Character.getNumericValue(move.charAt(1));
			chessBoard[1][col1]="P";
			chessBoard[0][col2]=String.valueOf(move.charAt(2));	//new piece value		
		}
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
		String moveList="",oldPiece;		
		int r=i/8,c=i%8;	//row and column position of the PAWN
		
		for(int j=-1;j<=1;j+=2){
			try {	//finding possible captures diagonally
				if(Character.isLowerCase(chessBoard[r-1][c+j].charAt(0)) && i>=16){
					oldPiece=chessBoard[r-1][c+j];
					chessBoard[r][c]=" ";
					chessBoard[r-1][c+j]="P";
					if(kingSafe()){
						moveList=moveList+r+c+(r-1)+(c+j)+oldPiece;
					}					
					chessBoard[r][c]="P";
					chessBoard[r-1][c+j]=oldPiece;
				}
			}catch(Exception e){}
			
			//promotion & capture
			try {	//finding possible captures diagonally
				if(Character.isLowerCase(chessBoard[r-1][c+j].charAt(0)) && i<16){
					String [] temp ={"Q","R","K","B"};
					for(int k=0;k<4;k++){
						oldPiece=chessBoard[r-1][c+j];
						chessBoard[r][c]=" ";
						chessBoard[r-1][c+j]=temp[k];
						if(kingSafe()){
							//old Column position, new Column position, captured piece, new piece, P(promotion)
							moveList=moveList+c+(c+j)+oldPiece+temp[k]+"P";
						}					
						chessBoard[r][c]="P";
						chessBoard[r-1][c+j]=oldPiece;
					}					
				}
			}catch(Exception e){}
		}
		try {//move one up
            if (" ".equals(chessBoard[r-1][c]) && i>=16) {
                oldPiece=chessBoard[r-1][c];
                chessBoard[r][c]=" ";
                chessBoard[r-1][c]="P";
                if (kingSafe()) {
                	moveList=moveList+r+c+(r-1)+c+oldPiece;
                }
                chessBoard[r][c]="P";
                chessBoard[r-1][c]=oldPiece;
            }
        } catch (Exception e) {}
        try {//promotion && no capture
            if (" ".equals(chessBoard[r-1][c]) && i<16) {
                String[] temp={"Q","R","B","K"};
                for (int k=0; k<4; k++) {
                    oldPiece=chessBoard[r-1][c];
                    chessBoard[r][c]=" ";
                    chessBoard[r-1][c]=temp[k];
                    if (kingSafe()) {
						//old Column position, new Column position, captured piece, new piece, P(promotion)
                    	moveList=moveList+c+c+oldPiece+temp[k]+"P";
                    }
                    chessBoard[r][c]="P";
                    chessBoard[r-1][c]=oldPiece;
                }
            }
        } catch (Exception e) {}
        
        try {//move two up
            if (" ".equals(chessBoard[r-1][c]) && " ".equals(chessBoard[r-2][c]) && i>=48) {
                oldPiece=chessBoard[r-2][c];
                chessBoard[r][c]=" ";
                chessBoard[r-2][c]="P";
                if (kingSafe()) {
                	moveList=moveList+r+c+(r-2)+c+oldPiece;
                }
                chessBoard[r][c]="P";
                chessBoard[r-2][c]=oldPiece;
            }
        } catch (Exception e) {}
		return moveList;
	}
	
	public static String possibleR(int i){
		String moveList="",oldPiece;		
		int r=i/8,c=i%8;	//row and column position of the ROOK
		int temp=1;
		
		//loop for checking the horizontal and vertical directions for the ROOK
		for(int j=-1;j<=1;j+=2){
			try {
				while(" ".equals(chessBoard[r][c+temp*j])){	//horizontal direction
					oldPiece=chessBoard[r][c+temp*j];
					chessBoard[r][c]=" ";
					chessBoard[r][c+temp*j]="R";
					if(kingSafe()){
						moveList=moveList+r+c+r+(c+temp*j)+oldPiece;
					}					
					chessBoard[r][c]="R";
					chessBoard[r][c+temp*j]=oldPiece;
					temp++;
				}
				if(Character.isLowerCase(chessBoard[r][c+temp*j].charAt(0))){
					oldPiece=chessBoard[r][c+temp*j];
					chessBoard[r][c]=" ";
					chessBoard[r][c+temp*j]="R";
					if(kingSafe()){
						moveList=moveList+r+c+r+(c+temp*j)+oldPiece;
					}					
					chessBoard[r][c]="R";
					chessBoard[r][c+temp*j]=oldPiece;					
				}
			} catch (Exception e) {}
			temp=1;
			
			try {
				while(" ".equals(chessBoard[r+temp*j][c])){	//vertical direction
					oldPiece=chessBoard[r+temp*j][c];
					chessBoard[r][c]=" ";
					chessBoard[r+temp*j][c]="R";
					if(kingSafe()){
						moveList=moveList+r+c+(r+temp*j)+c+oldPiece;
					}					
					chessBoard[r][c]="R";
					chessBoard[r+temp*j][c]=oldPiece;
					temp++;
				}
				if(Character.isLowerCase(chessBoard[r+temp*j][c].charAt(0))){
					oldPiece=chessBoard[r+temp*j][c];
					chessBoard[r][c]=" ";
					chessBoard[r+temp*j][c]="R";
					if(kingSafe()){
						moveList=moveList+r+c+(r+temp*j)+c+oldPiece;
					}					
					chessBoard[r][c]="R";
					chessBoard[r+temp*j][c]=oldPiece;					
				}
			} catch (Exception e) {}
			temp=1;
		}
		return moveList;
	}
	
	public static String possibleK(int i){
		String moveList="",oldPiece;		
		int r=i/8,c=i%8;	//row and column position of the KNIGHT
		
		//loops for checking the diagonal both ways directions for the KNIGHT
		for(int j=-1;j<=1;j+=2){
			for(int k=-1;k<=1;k+=2){				
				if(j!=0 || k!=0){
					try {
						if(Character.isLowerCase(chessBoard[r+j][c+k*2].charAt(0)) || " ".equals(chessBoard[r+j][c+k*2])){
							oldPiece=chessBoard[r+j][c+k*2];
							chessBoard[r][c]=" ";
							chessBoard[r+j][c+k*2]="K";
							if(kingSafe()){
								moveList=moveList+r+c+(r+j)+(c+k*2)+oldPiece;
							}
							chessBoard[r+j][c+k*2]=oldPiece;
							chessBoard[r][c]="K";
						}
					} catch (Exception e) {}
					
					try {
						if(Character.isLowerCase(chessBoard[r+j*2][c+k].charAt(0)) || " ".equals(chessBoard[r+j*2][c+k])){
							oldPiece=chessBoard[r+j*2][c+k];
							chessBoard[r][c]=" ";
							chessBoard[r+j*2][c+k]="K";
							if(kingSafe()){
								moveList=moveList+r+c+(r+j*2)+(c+k)+oldPiece;
							}
							chessBoard[r+j*2][c+k]=oldPiece;
							chessBoard[r][c]="K";
						}
					} catch (Exception e) {}
				}
			}
		}
		
		return moveList;
	}
	
	public static String possibleB(int i){
		String moveList="",oldPiece;		
		int r=i/8,c=i%8;	//row and column position of the BISHOP
		int temp=1;
		
		//loops for checking the diagonal both ways directions for the BISHOP
		for(int j=-1;j<=1;j+=2){
			for(int k=-1;k<=1;k+=2){
				if(j!=0 || k!=0){
					try {
						while(" ".equals(chessBoard[r+temp*j][c+temp*k])){
							oldPiece=chessBoard[r+temp*j][c+temp*k];
							chessBoard[r][c]=" ";
							chessBoard[r+temp*j][c+temp*k]="B";
							if(kingSafe()){
								moveList=moveList+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
							}
							chessBoard[r+temp*j][c+temp*k]=oldPiece;
							chessBoard[r][c]="B";
							temp++;
						}
						if(Character.isLowerCase(chessBoard[r+temp*j][c+temp*k].charAt(0))){
							oldPiece=chessBoard[r+temp*j][c+temp*k];
							chessBoard[r][c]=" ";
							chessBoard[r+temp*j][c+temp*k]="B";
							if(kingSafe()){
								moveList=moveList+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
							}
							chessBoard[r+temp*j][c+temp*k]=oldPiece;
							chessBoard[r][c]="B";						
						}
					} catch (Exception e) {}
					temp=1;
				}
			}	
		}
		
		return moveList;
	}
	
	public static String possibleQ(int i){
		String moveList="",oldPiece;		
		int r=i/8,c=i%8;	//row and column position of the QUEEN
		int temp=1;
		
		//loops for checking the horizontal, vertical, diagonal both ways directions for the queen
		for(int j=-1;j<=1;j++){
			for(int k=-1;k<=1;k++){
				if(j!=0 || k!=0){
					try {
						while(" ".equals(chessBoard[r+temp*j][c+temp*k])){
							oldPiece=chessBoard[r+temp*j][c+temp*k];
							chessBoard[r][c]=" ";
							chessBoard[r+temp*j][c+temp*k]="Q";
							if(kingSafe()){
								moveList=moveList+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
							}
							chessBoard[r+temp*j][c+temp*k]=oldPiece;
							chessBoard[r][c]="Q";
							temp++;
						}
						if(Character.isLowerCase(chessBoard[r+temp*j][c+temp*k].charAt(0))){
							oldPiece=chessBoard[r+temp*j][c+temp*k];
							chessBoard[r][c]=" ";
							chessBoard[r+temp*j][c+temp*k]="Q";
							if(kingSafe()){
								moveList=moveList+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
							}
							chessBoard[r+temp*j][c+temp*k]=oldPiece;
							chessBoard[r][c]="Q";						
						}
					} catch (Exception e) {}
					temp=1;
				}
			}
		}
		
		return moveList;
	}
	
	public static String possibleA(int i){
		String moveList="",oldPiece;
		int kingTemp;
		int r=i/8,c=i%8;	//row and column position of the KING
		for(int j=0;j<9;j++){
			if(j!=4){		//avoid checking for the place where KING is already placed
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
				} catch (Exception e) {}			
			}
		}
		//need to add castling
		return moveList;
	}
	
	public static boolean kingSafe(){
		//black bishop or queen in diagonal
		int temp=1;
		
		for(int j=-1;j<=1;j+=2){
			for(int k=-1;k<=1;k+=2){
					try {
						while(" ".equals(chessBoard[kingPositionA/8+temp*j][kingPositionA%8+temp*k])){temp++;}
						
						//if black bishop or queen are at the diagonal positions
						if("b".equals(chessBoard[kingPositionA/8+temp*j][kingPositionA%8+temp*k]) ||
								"q".equals(chessBoard[kingPositionA/8+temp*j][kingPositionA%8+temp*k]))
							return false;							
					} catch (Exception e) {}
					temp=1;				
			}	
		}
		
		//black rook or queen in vertical or horizontal	
		for(int j=-1;j<=1;j+=2){
			try {	//vertical
				while(" ".equals(chessBoard[kingPositionA/8+temp*j][kingPositionA%8])){temp++;}
				
				//if black rook or queen are at the vertical or horizontal positions
				if("r".equals(chessBoard[kingPositionA/8+temp*j][kingPositionA%8]) ||
						"q".equals(chessBoard[kingPositionA/8+temp*j][kingPositionA%8]))
					return false;							
			} catch (Exception e) {}
			
			temp=1;
			try {		//horizontal
				while(" ".equals(chessBoard[kingPositionA/8][kingPositionA%8+temp*j])){temp++;}
				
				//if black rook or queen are at the vertical or horizontal positions
				if("r".equals(chessBoard[kingPositionA/8][kingPositionA%8+temp*j]) ||
						"q".equals(chessBoard[kingPositionA/8][kingPositionA%8+temp*j]))
					return false;							
			} catch (Exception e) {}
			temp=1;									
		}	
		
		//black knight
		for(int j=-1;j<=1;j+=2){
			for(int k=-1;k<=1;k+=2){
					try {												
						if("k".equals(chessBoard[kingPositionA/8+j][kingPositionA%8+k*2]))
							return false;					
					} catch (Exception e) {}
					try {
						if("k".equals(chessBoard[kingPositionA/8+j*2][kingPositionA%8+k]))
							return false;
					} catch (Exception e) {}							
			}	
		}
		
		//black pawn
		if(kingPositionA>=16){
			try {								
				if("p".equals(chessBoard[kingPositionA/8-1][kingPositionA%8-1]))
					return false;					
			} catch (Exception e) {}
			try {
				if("p".equals(chessBoard[kingPositionA/8-1][kingPositionA%8+1]))
					return false;
			} catch (Exception e) {}
		}	
		
		//black king
		for(int j=-1;j<=1;j++){
			for(int k=-1;k<=1;k++){
				if(j!=0 || k!=0){
					try {												
						if("a".equals(chessBoard[kingPositionA/8+j][kingPositionA%8+k]))
							return false;					
					} catch (Exception e) {}
				}
			}	
		}
		
		return true;
	}

}
