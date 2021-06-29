/**
 * 
 */
package boardgame;

import java.util.ArrayList;

import java.util.LinkedList;

import java.util.Random;

/**
 * @author JackBoston
 *
 */
public class Player {

	//setting up var 
	private String name;
	private int currentSquare;
	private double resources;
	private LinkedList<AreaSquare>squaresOwned;
	
	

	/**
	 * Default Constructor
	 */
	public Player() {
		
	}

	public Player(String name, int currentSquare, double resources) {
		this.setName(name);
		this.setCurrentSquare(currentSquare);
		this.setResources(resources);
		this.squaresOwned= new LinkedList<AreaSquare>();

	}

	/**
	 * @return the name
	 */

	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the currentSquare
	 */

	public int getCurrentSquare() {
		return currentSquare;
	}

	/**
	 * @param currentSquare the currentSquare to set
	 */

	public void setCurrentSquare(int currentSquare) {
		this.currentSquare = currentSquare;
	}

	/**
	 * @return the resources
	 */
	public double getResources() {
		return resources;
	}

	/**
	 * sets resources
	 * has to be more than 0 less than 1000000
	 * @param resources the resources to set
	 */
	public void setResources(double resources) {
	
		if ((resources < 1000000)&&(resources>0)) {
			this.resources = resources;
		} else if (this.resources<=0) {
			System.out.println("Elimination");
		}
	}

	/**
	 * @return the squares
	 */
	public LinkedList<AreaSquare> getSquares() {
		return squaresOwned;
	}

	/**
	 * this method adds a square object to the linked list of squares
	 * that this player owns
	 * @param square
	 */
	public void addSquare(AreaSquare square) {

		//adding square
		squaresOwned.add(square);
		//setting owner to this player 
		square.setOwner(this);
	}
	

	/**
	 * This a method that adds a set amount of resources to a player
	 * @param amount
	 * @return
	 */
	public double addResources(double amount) {
		System.out.println();
		//before
		System.out.println("Resources before for "+this.getName()+" are " + this.resources+" Carbon Credits");
		//operation
		this.resources = this.resources + amount;
		//after
		System.out.println("Resources after for "+this.getName()+" are " + this.resources+" Carbon Credits");
		System.out.println();
		return this.resources;
	}

	/**
	 * This is a method that removes the set amount of resources from a player 
	 * @param amount
	 * @return
	 */
	public double removeResources(double amount) {
		System.out.println();
		//before 
		System.out.println("Resources before for "+this.getName()+" are " + this.resources+" Carbon Credits");
		//operation
		this.resources = this.resources - amount;
		//after 
		System.out.println("Resources after for "+this.getName()+" are " + this.resources+" Carbon Credits" );
		System.out.println();
		return this.resources;
	}

	/**
	 * This is a method that displays all details for players
	 */
	public void displayAll() {
		
		//displaying resources 
		System.out.println();
		System.out.println("The resources for "+this.getName()+" are:");
		System.out.println("Carbon Credits : "+this.getResources());
		System.out.println("Current Square :  "+this.getCurrentSquare());
		System.out.print("Squares Owned : ");
		
		//for the squares owned a for loop is used to gain intuitive access 
		//to the squares properties and then display them such as developement 
		//level and square type, prints straight after Squares Owned :  
		for (AreaSquare square: squaresOwned) {
			
			System.out.print(square.getSquareNumber()+" "+"("+square.getSquareType()+")"+ "("+square.getDevelopementLevel()+"),");
		}
		System.out.println();
	}
	/**
	 * This is a method between two players that initiates the payment of
	 * rent between the two players, it makes two method calls to the 
	 * add resources and remove resources methods in this class
	 * @param giver
	 * @param receiver
	 * @param amount
	 */
	public void payRent (Player giver, Player receiver, double amount) {
		
		//confirmation of rents sum 
		System.out.println("Rent must be paid on this square to the sum of "+amount);
		System.out.println();
		
		//before resources 
		System.out.println(giver.getName()+" has "+giver.getResources()+" Carbon Credits.");
		System.out.println("Owner: "+receiver.getName()+" has "+receiver.getResources()+" Carbon Credits.");
		
		//if giver doesn't have enough set rent to their total resources
		if(giver.getResources()<amount) {
			amount = giver.getResources();
		}
		
		//operation, calling add and remove methods 
		giver.removeResources(amount);
		receiver.addResources(amount);
		
		//after resources 
		System.out.println();
		System.out.println(giver.getName()+" now has "+giver.getResources()+" Carbon Credits.");
		System.out.println("Owner: "+receiver.getName()+" now has "+receiver.getResources()+" Carbon Credits.");
		System.out.println();
		System.out.println("Transaction complete :).");
		
	}
	
	
	/**
	 * This is a a method for the player to roll the dice 
	 * and move the alotted spaces it returns the new square
	 * object
	 * @param board
	 * @return
	 */
	public Square throwDice(ArrayList<Square> board, Game game) {

		Random rand = new Random();

		//players old square 
		System.out.println("You are on square " + currentSquare);
		
		//first throw 
		int throw1 = rand.nextInt(6) + 1;

		System.out.println("Your first throw was a " + throw1);

		//second throw
		int throw2 = rand.nextInt(6) + 1;
		
		System.out.println("Your second throw was a " + throw2);

		//result
		int result = throw1+throw2;

		System.out.println("Combined you threw a " + result);

		//new square 
		currentSquare = currentSquare + result;

		//altering for board structure 
		if (currentSquare >game.getFinalSquareNumber() ) {
			currentSquare = currentSquare - game.getNumOfSquares();
		}

		//making new square 
		Square newSquare = null;
		
		//setting new square 
		for (Square square : board) {
			if (square.getSquareNumber() == currentSquare) {
				newSquare = square;
			}
		}
		
		return newSquare;

	}
}
