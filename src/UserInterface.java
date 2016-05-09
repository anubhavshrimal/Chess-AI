import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class UserInterface extends JPanel implements MouseListener, MouseMotionListener{

	static int oldMouseX,oldMouseY,newMouseX, newMouseY;
	static int squareSize=32;
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.addMouseListener(this);
        this.addMouseMotionListener(this);
		for(int i=0;i<64;i+=2){
			g.setColor(new Color(255,200,100));
			g.fillRect((i%8+(i/8)%2)*squareSize, (i/8)*squareSize, squareSize, squareSize);
			g.setColor(new Color(150,50,30));
			g.fillRect(((i+1)%8-((i+1)/8)%2)*squareSize, ((i+1)/8)*squareSize, squareSize, squareSize);
		}
		Image chessPiecesImage;
        chessPiecesImage=new ImageIcon("ChessPieces.png").getImage();
        int x,y;
        for(int i=0;i<64;i++){
        	x=-1;
        	y=-1;
	    	 switch (AlphaBetaChess.chessBoard[i/8][i%8]) {
	    	 case "P": x=5; y=0;
             break;
	         case "p": x=5; y=1;
	             break;
	         case "R": x=2; y=0;
	             break;
	         case "r": x=2; y=1;
	             break;
	         case "K": x=4; y=0;
	             break;
	         case "k": x=4; y=1;
	             break;
	         case "B": x=3; y=0;
	             break;
	         case "b": x=3; y=1;
	             break;
	         case "Q": x=1; y=0;
	             break;
	         case "q": x=1; y=1;
	             break;
	         case "A": x=0; y=0;
	             break;
	         case "a": x=0; y=1;
	             break;
	    	}
	    if(x!=-1 && y!=-1)
	    	g.drawImage(chessPiecesImage, (i%8)*squareSize, (i/8)*squareSize, (i%8+1)*squareSize, (i/8+1)*squareSize, x*64, y*64, (x+1)*64, (y+1)*64, this);
        }
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getX()<8*squareSize && e.getY()<8*squareSize){//if mouse is pressed inside the chess board
			oldMouseX=e.getX()/squareSize;
			oldMouseY=e.getY()/squareSize;
		}		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getX()<8*squareSize && e.getY()<8*squareSize){//if mouse is released inside the chess board
			newMouseX=e.getX()/squareSize;
			newMouseY=e.getY()/squareSize;
			String move;
			if(e.getButton()==MouseEvent.BUTTON1){
				if(newMouseY==0 && oldMouseY==1 && "P".equals(AlphaBetaChess.chessBoard[oldMouseY][oldMouseX])){
					//if pawn promotion
					move=""+oldMouseX+newMouseX+AlphaBetaChess.chessBoard[newMouseY][newMouseX]+"QP";
				}	
				else{	//if a regular move
					move=""+oldMouseY+oldMouseX+newMouseY+newMouseX+AlphaBetaChess.chessBoard[newMouseY][newMouseX];
				}							
				String userPossibleMoves=AlphaBetaChess.possibleMoves();
				if(userPossibleMoves.replaceAll(move, "").length()<userPossibleMoves.length()){
					AlphaBetaChess.makeMove(move);
					AlphaBetaChess.flipBoard();
					AlphaBetaChess.makeMove(AlphaBetaChess.alphaBeta(AlphaBetaChess.globalDepth, Integer.MAX_VALUE, Integer.MIN_VALUE, "", 0));
					AlphaBetaChess.flipBoard();
					repaint();
				}
			}
		}		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}