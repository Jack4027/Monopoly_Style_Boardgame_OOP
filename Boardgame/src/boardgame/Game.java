/**
 * 
 */
package boardgame;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * In this class we are setting the game rules, opening and closing messages the
 * ability to populate the board, as well as some checks on whether a player has
 * development opportunities or whether they have won the game. Also in this
 * method the ability to get and set a winner is possible
 * 
 * @author JackBoston
 *
 */
public class Game {


	private Player winner;
	
	private ArrayList<Square>board = new ArrayList<Square>();
	
	private int numOfSquares ;
	
	private int finalSquareNumber ;
	
	
	/**
	 * Default Constructor 
	 */
	public Game () {
		
	
	}
	
	/**
	 * @return the numOfSquares
	 */
	public int getNumOfSquares() {
		return numOfSquares;
		
	}
	
	/**
	 * @param numOfSquares the numOfSquares to set
	 */
	public void setNumOfSquares() {
		
		int numOfSquares;
		numOfSquares= board.size();
		this.numOfSquares = numOfSquares;
		
	}
	
	/**
	 * @return the lastSquareNumber
	 */
	public int getFinalSquareNumber() {
		
		return this.finalSquareNumber;

	
	}
	
	/**
	 * @param lastSquareNumber the lastSquareNumber to set
	 */
	public void setFinalSquareNumber() {
		
		int finalSquareNumber;
		finalSquareNumber= board.get(board.size()-1).getSquareNumber();
		
		this.finalSquareNumber = finalSquareNumber;
		
	}

	/**
	 * @return the winner
	 */
	public Player getWinner() {
		return winner;
	}

	/**
	 * @param winner the winner to set
	 */
	public void setWinner(Player winner) {
		this.winner = winner;
	}

	/**
	 * Displaying opening messages for game
	 */
	public void opening() {

	String	name = "Penny Bags goes Green";

		String openingMessage = "Sustainable Developments take on the famous Monopoly boardgame" + " without the board ;).";

		System.out.println("Welcome to " + name);
		System.out.println();
		System.out.println(openingMessage);

	}

	/**
	 * displaying the game rules
	 */
	public void gameRules() {

		System.out.println();

		System.out.println("Goal is to develope:	More board development == More Sustainability.");
		System.out.println();
		System.out.println("Buying an area is equal to Ownership Level (Level 0).");
		System.out.println("Development Levels range from minor - major: Levels 1 - 4.");
		System.out.println();
		System.out.println("Players have Carbon credits.");
		System.out.println();
		System.out.println("Start with 1000CC, get 200CC for passing GO.");

		System.out.println("Square types: Solar, Nuclear, Wind, Tidal, Start, Normal.");
		System.out.println();
		System.out.println("1 Start, 1 Normal, 3 Wind, 3 Tidal, 2 Solar, 2 Nuclear.");
		System.out.println();
		System.out.println("Players pay rent when they land on another players property.");
		System.out.println("Higher Developement = Higher Rent Rate.");
		System.out.println();
		System.out.println("Solar, Nuclear, Wind, Tidal are all purchaseable area Squares.");
		System.out.println();
		System.out.println("Set of purchaseable area squares are equal to one field, so...........");
		System.out.println("All of the (3) Wind squares are 1 field, All of the (2) Nuclear Squares are 1 Field etc.");
		System.out.println();
		System.out.println("Different Cost for different Fields, Nuclear: Most Exp and Solar: Least Exp.");
		System.out.println();
		System.out.println("Having 0 CC means Disqualification from the game.");
		System.out.println();
		System.out.println("The Winner is the first to develope a field on the board to level 4 (all areas in field).");
		System.out.println();
		System.out.println("If all other players are eliminated (they run out of resources) remaining player is also declared the Winner.");
		System.out.println();
		System.out.println("If a player develops part of a field and is eliminated from the game then the other players can claim their devlopments by paying ");
		System.out.println("the area ownership cost, players can continue to develop the eliminated player's devlopments by purchasing the whole field.");
		System.out.println();
		System.out.println("Good luck and Happy Playing :)" );
		System.out.println();
		System.out.println("************************************************************************************************************************************************************");
		System.out.println();
		System.out.println();
		System.out.println("Hello Eco Warriors............");
		
	}

	/**
	 * Displaying closing messages for game
	 * 
	 * @param winner
	 */
	public void closing() {

		if (winner == null) {

		String 	closingMessage = "It has been fun everybody, I will see you all next time :)";
		
			System.out.println(closingMessage);

		} else {

		String 	closingMessage = "Thank you all for playing and congratulations to our winner...........";

			System.out.println();
			System.out.println(closingMessage + " " + winner.getName());

		}

	}

	public ArrayList<Square> Populate(ArrayList<Square> board) {

		// adding squares to board
		Square Start = new Square(0, "Normal Square");
		AreaSquare Two = new AreaSquare(1, 700, "Solar Square", 300);
		AreaSquare Three = new AreaSquare(2, 300, "Solar Square", 250);
		AreaSquare Four = new AreaSquare(3, 400, "Wind Square", 300);
		AreaSquare Five = new AreaSquare(4, 400, "Wind Square", 300);
		AreaSquare Six = new AreaSquare(5, 400, "Wind Square", 300);
		AreaSquare Seven = new AreaSquare(6, 400, "Tidal Square", 300);
		AreaSquare Eight = new AreaSquare(7, 400, "Tidal Square", 300);
		AreaSquare Nine = new AreaSquare(8, 400, "Tidal Square", 300);
		AreaSquare Ten = new AreaSquare(9, 700, "Nuclear Square", 500);
		AreaSquare Eleven = new AreaSquare(10, 1000, "Nuclear Square", 800);
		Square Twelve = new Square(11, "Normal Square");

		// adding squares to board
		board.add(Start);
		board.add(Two);
		board.add(Three);
		board.add(Four);
		board.add(Five);
		board.add(Six);
		board.add(Seven);
		board.add(Eight);
		board.add(Nine);
		board.add(Ten);
		board.add(Eleven);
		board.add(Twelve);

		this.board = board;
		
		this.setNumOfSquares();
		this.setFinalSquareNumber();
		
		return board;

	}

	/**
	 * This is a check to see if a player is eligible for the development
	 * opportunities screen if they are the available opportunities are returned by
	 * this method in the results list
	 * 
	 * @param squaresOwned
	 * @return
	 */
	public LinkedList<AreaSquare> fieldOwnership(LinkedList<AreaSquare> squaresOwned, Player player) {

		//size of each field 
		int solarSize = 2;
		int nuclearSize = 2;
		int windSize = 3;
		int tidalSize = 3;
		
		// counts for each field type
		int solarCount = 0;
		int windCount = 0;
		int tidalCount = 0;
		int nuclearCount = 0;

		// array list for each individual field
		ArrayList<AreaSquare> solarArray = new ArrayList<AreaSquare>();
		ArrayList<AreaSquare> windArray = new ArrayList<AreaSquare>();
		ArrayList<AreaSquare> tidalArray = new ArrayList<AreaSquare>();
		ArrayList<AreaSquare> nuclearArray = new ArrayList<AreaSquare>();
		LinkedList<AreaSquare> results = new LinkedList<AreaSquare>();

		// iterates through squaresOwned and counts the squares from each field
		for (AreaSquare square : squaresOwned) {

			if (square.getSquareType().equalsIgnoreCase("Solar Square")) {
				solarArray.add(square);

				solarCount++;
			} else if (square.getSquareType().equalsIgnoreCase("Wind Square")) {
				windArray.add(square);

				windCount++;
			} else if (square.getSquareType().equalsIgnoreCase("Tidal Square")) {
				tidalArray.add(square);

				tidalCount++;
			} else if (square.getSquareType().equalsIgnoreCase("Nuclear Square")) {
				nuclearArray.add(square);

				nuclearCount++;
			}

		}

		// if the squaresOwned list contains all of a particular field
		// they are added to the results list returned by this method
		if (solarCount == solarSize) {

			results.addAll(solarArray);

		}
		if (nuclearCount == nuclearSize) {
			results.addAll(nuclearArray);

		}
		if (windCount == windSize) {
			results.addAll(windArray);

		}
		if (tidalCount == tidalSize) {
			results.addAll(tidalArray);

		}

		// if there are results return them
		if (!results.isEmpty()) {
			System.out.println();
			System.out.println("You have some development opportunities " + player.getName()+"!!! If you develop a whole field to level 4, you are the Eco Champion......... ");
			System.out.println();
			return results;

			// if not return null
		} else {
			return null;
		}

	}

	/**
	 * This is a check to see if a player has 2 fields fully developed
	 * 
	 * @param squaresOwned
	 * @return
	 */
	public boolean FieldCheck(LinkedList<AreaSquare> squaresOwned) {

		//size of each field 
		int solarSize = 2;
		int nuclearSize = 2;
		int windSize = 3;
		int tidalSize = 3;
				
		// counting the number of areas fully developed from each field
		int solarCount = 0;
		int nuclearCount = 0;
		int windCount = 0;
		int tidalCount = 0;

		//number of fields to win 
		int fieldSize = 1;
		// counts the number of fields that are fully developed
		int fieldsDeveloped = 0;

		// iterates through squaresOwned
		for (AreaSquare square : squaresOwned) {

			// asks if the square has been fully developed, if so proceed
			if (square.getDevelopementLevel().equalsIgnoreCase("Level 4")) {

				// asks what square type is the square and then adds 1 to the
				// respective count
				if (square.getSquareType().equalsIgnoreCase("Solar Square")) {
					solarCount++;
				} else if (square.getSquareType().equalsIgnoreCase("Wind Square")) {
					windCount++;
				} else if (square.getSquareType().equalsIgnoreCase("Nuclear Square")) {
					nuclearCount++;
				} else if (square.getSquareType().equalsIgnoreCase("Tidal Square")) {
					tidalCount++;
				}

			} else {

			}
		}

		// if all areas in a field are fully developed add 1 to the field count
		if (solarCount == solarSize) {
			fieldsDeveloped++;
		}

		if (nuclearCount == nuclearSize) {
			fieldsDeveloped++;
		}

		if (windCount == windSize) {
			fieldsDeveloped++;
		}

		if (tidalCount == tidalSize) {
			fieldsDeveloped++;
		}

		// if 2 fields are fully developed return true
		if (fieldsDeveloped == fieldSize) {
			return true;
			// if not return false
		} else {
			return false;
		}

	}// end of method

}// end of class
