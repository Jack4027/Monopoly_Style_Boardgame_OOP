/**
 * 
 */
package boardgame;

import java.util.LinkedList;


/**
 * In this class we populate the methods for the Areasquare 
 * which has all the possibilities of the Square object but 
 * also has investment opportunities available to it. These 
 * squares will populate are fields. 
 * @author JackBoston
 *
 */
public class AreaSquare extends Square {
	
	
	//setting up vars 
	private double price;
	private Player owner = null;
	private double baseRentPrice;
	private String developementLevel = "Level 0";
	
	/**
	 * Default Constructor
	 */
	public AreaSquare () {
		
	}
	
	/**
	 * Constructor with args
	 * @param number
	**/
	
	/**
	 * Constructor for AreaSquare with args, has all the arguments of Square 
	 * with Rent and Price to buy added in
	 * @param squareNumber
	 * @param price
	 * @param squareType
	 * @param rent
	 */
	public AreaSquare(int squareNumber, double price, String squareType, double rent) {
		
		super(squareNumber, squareType);
		this.setPrice(price);
		this.setRentPrice(rent);

	}
	

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the owner
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
	}


	/**
	 * @return the rentPrice
	 */
	public double getRentPrice() {
		return baseRentPrice;
	}
	
	/**
	 * @param rentPrice the rentPrice to set
	 */
	public void setRentPrice(double rentPrice) {
	
			this.baseRentPrice = rentPrice;
		
	}
	
	/**
	 * @return the developementLevel
	 */
	public String getDevelopementLevel() {
		return developementLevel;
	}
	
	/**
	 * @param developementLevel the developementLevel to set
	 */
	public void setDevelopementLevel(String developementLevel) {
		this.developementLevel = developementLevel;
	}

	/**
	 * This is a method that sets the development price
	 *  of a Square depending on the current development
	 *  level
	 * @return
	 */
	public double developmentPrice () {
		
		double basePrice = this.price;
		double developmentprice=0;
		double premium = 300;
		
		 if (this.getDevelopementLevel().equalsIgnoreCase("Level 3")) {
			 //+1200 for level 4
			developmentprice=basePrice+(premium*4);
		} else if (this.getDevelopementLevel().equalsIgnoreCase("Level 2")) {
			//+900 for level 3
			developmentprice=basePrice+(premium*3);
			
		} else if (this.getDevelopementLevel().equalsIgnoreCase("Level 1")) {
			//+600 for level 2
			developmentprice=basePrice+(premium*2);
			
		} else if (this.getDevelopementLevel().equalsIgnoreCase("Level 0")) {
			//+300 for level 1
			developmentprice=basePrice+premium;
			
		}
		return developmentprice;
	}
	
	/**
	 * This method will deliver a custom message to the user depending 
	 * on the type of square they have to buy 
	 * @param square
	 * @return
	 */
	public String customInvestMessage (AreaSquare square) {
		
		String message = "Do wish to invest in this square?";
		if (square.getSquareType().equalsIgnoreCase("Nuclear Square")) {
			message = "Do you want to invest in "+square.getSquareNumber()+" or is your brain going into meltdown?(Y/N)";
		}
		else if (square.getSquareType().equalsIgnoreCase("Solar Square")) {
			message = "Do you want to invest in "+square.getSquareNumber()+" or is the sun tooo bright?(Y/N)";
			
		}
		else if (square.getSquareType().equalsIgnoreCase("Wind Square")) {
			message = "Do you want to invest in "+square.getSquareNumber()+" or are you about to be blown off the board?(Y/N)";
			
		}
		else if (square.getSquareType().equalsIgnoreCase("Tidal Square")) {
			message = "Do you want to invest in "+square.getSquareNumber()+" or are drowning in indecision?(Y/N)";
			
		}
		return message;
	}
	
	/**
	 * this is a method which checks what the current status of the square is 
	 * and then upgrades it so for example if the square is at a level 1 development 
	 * this method will upgrade it to a level 2 development
	 * @AreaSquare  
	 */
	public void developmentLevelUp (AreaSquare square, Player player) {
		
		if (square.getDevelopementLevel().equalsIgnoreCase("Level 0")) {
			
			square.setDevelopementLevel("Level 1");
			System.out.println();
			System.out.println("Congratulations "+player.getName()+" on your level 1 "+square.getSquareType()+" development. :)");
		}
		else if (square.getDevelopementLevel().equalsIgnoreCase("Level 1")) {
			
			square.setDevelopementLevel("Level 2");
			System.out.println();
			System.out.println("Congratulations "+player.getName()+" on your level 2 "+square.getSquareType()+" development. :)");
			
		}
		else if (square.getDevelopementLevel().equalsIgnoreCase("Level 2")) {
			
			square.setDevelopementLevel("Level 3");
			System.out.println();
			System.out.println("Congratulations "+player.getName()+" on your level 3 "+square.getSquareType()+" development. :)");
			
		}
		else if (square.getDevelopementLevel().equalsIgnoreCase("Level 3")) {
			
			square.setDevelopementLevel("Level 4");
			System.out.println();
			System.out.println("Congratulations "+player.getName()+" on your level 4 "+square.getSquareType()+" development. :)");
			
		} else {
			
			System.out.println();
			System.out.println("This square is fully developed");
		}
		
	}
	
	
}

