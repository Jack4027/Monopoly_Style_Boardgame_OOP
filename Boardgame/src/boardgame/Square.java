/**
 * 
 */
package boardgame;

import java.util.LinkedList;

/**
 * @author JackBoston
 *
 */
public class Square {
	
	

	//setting up vars 
	private int squareNumber; 
	private String squareType;
	


	/**
	 * Default Constructor
	 */
	public Square () {
		
	}
	
	/**
	 * Constructor with args
	 * @param number
	**/
	
	public Square(int squareNumber,  String squareType) {
		this.setSquareNumber(squareNumber);
		this.setSquareType(squareType);
		

	}
	

	/**
	 * @return the squareNumber
	 */
	public int getSquareNumber() {
		return squareNumber;
	}
	
	/**
	 * @param squareNumber the squareNumber to set
	 */
	public void setSquareNumber(int squareNumber) {
		this.squareNumber = squareNumber;
	}
	
	/**
	 * @return the squareType
	 */
	public String getSquareType() {
		return squareType;
	}
	
	/**
	 * @param squareType the squareType to set
	 */
	public void setSquareType(String squareType) {
		this.squareType = squareType;
	}

	
	/**
	 * This is a method that rewards players when they pass go.
	 * @param oldSquare
	 * @param newSquare
	 * @param player
	 */
	public void PassGo ( int oldSquare,int newSquare, Player player) {
	
		
		if (newSquare<=oldSquare) {
			System.out.println();
			System.out.println("Congratulations! You just passed GO, you have been rewarded with 200 CC. ");
			player.addResources(200);
		}
	}

	
	

}
