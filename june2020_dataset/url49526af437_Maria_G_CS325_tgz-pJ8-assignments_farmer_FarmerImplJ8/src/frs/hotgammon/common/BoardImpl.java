package frs.hotgammon.common;

import frs.hotgammon.Board;
import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Location;



public class BoardImpl implements Board {

	private final int SQUARES_ON_BOARD = 28;
	private Square[] board = new Square[SQUARES_ON_BOARD];
	
	public BoardImpl(){ 
		for(int i = 0; i < SQUARES_ON_BOARD; i++){
			board[i] = new Square();
		}
	}	
	
	public BoardImpl(int squaresOnBoard){ 
		for(int i = 0; i < squaresOnBoard; i++){
			board[i] = new Square();
		}
	}
	
	/* (non-Javadoc)
	 * @see frs.hotgammon.BoardInter#move(frs.hotgammon.Location, frs.hotgammon.Location, frs.hotgammon.Color)
	 */
	@Override
	public boolean move(Location from, Location to, Color playerInTurn){

		boolean rem = remove(playerInTurn, from.ordinal());
		boolean add = place(playerInTurn,to.ordinal());
		return ( rem && add );
		
	}
	
	/* (non-Javadoc)
	 * @see frs.hotgammon.BoardInter#getCountAt(frs.hotgammon.Location)
	 */
	@Override
	public int getCountAt(Location loc){
		
		return board[loc.ordinal()].getCount();
		
	}

	/* (non-Javadoc)
	 * @see frs.hotgammon.BoardInter#getColorAt(frs.hotgammon.Location)
	 */
	@Override
	public Color getColorAt(Location loc){
		
		return board[loc.ordinal()].getColor();
		
	}

	@Override
	public boolean place(Color col, int index){
		
		if( index > -1 && index < SQUARES_ON_BOARD){
				board[index].add(col);
				return true;
		}
		return false;
	}

	@Override
	public boolean remove(Color col, int index){
		
		if( index > -1 && index < SQUARES_ON_BOARD){
			if( board[index].color == col ){
				board[index].remove(col);
				return true;
			}
		}
		return false;
	}
	
	
	public class Square{
		public int occupants = 0;
		private Color color = Color.NONE;
				
		public boolean add(Color col){
			
			if(this.color == Color.NONE || this.color == col){
				this.color = col;
				this.occupants++;
				return true;
			}			
			return false;
		}
		
		public boolean remove(Color col){

			if(this.color == col && this.occupants > 0){
				this.occupants--;
				if (this.occupants == 0){
					this.color = Color.NONE;
				}
				return true;
			}			
			return false;
		}
		
		public Color getColor(){
			return this.color;
		}
		
		public int getCount(){
			return this.occupants;
		}
		
	}


	public Square getSquare(int ordinal) {
		return board[ordinal];
	}


}
