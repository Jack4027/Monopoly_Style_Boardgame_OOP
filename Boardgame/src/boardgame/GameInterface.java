/**
 * 
 */
package boardgame;

//importing objects
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;



/**
 * This is the class in which we create the main running of 
 * the game and also the methods that will facilitate quick, 
 * easy and flexible running of the game, the main method is where
 * the running of the game occur, methods such as development, playerInvests 
 * and paysRent facilitate quick and easy running of the game by making method 
 * calls to the relevant OOP classes so as to perform these operations
 * 
 * @author JackBoston
 *
 */
public class GameInterface {

	/**
	 * This is the main method which is mainly in charge of running through the game
	 * interface and making calls to other methods in the class as well as
	 * initiating methods within the OOP objects themselves
	 * 
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {

		// Creating the games opening message
		Game game = new Game();
		game.opening();
		game.gameRules();
		System.out.println();
		
		//!gameComplete
		boolean gameCompleted = false;
		
		//Setting up scanner for user input 
		Scanner sc = new Scanner(System.in);
		String user;
		
		// creating array list for players
		ArrayList<Player> players = new ArrayList<Player>();
		
		//player entry process, setting up players with starting resources and placing them into array 
		players=setUp(players,1000,4);
		
		// creating board
		ArrayList<Square> board = new ArrayList<Square>();
		
		//populating the board
		board = game.Populate(board);
	
		//stores the Squares where the player has purchased the entire field
		LinkedList<AreaSquare> availableOpportunities = new LinkedList<AreaSquare>();

		try {
			// execute do while game is not completed
			// each iteration is equivalent to a round
			do {
				//thread.start();
				// standard for loop iterating through for every player in the players array
				// each iteration is equivalent to a players turn
				for (int loop = 0; loop < players.size(); loop++) {

					//thread sleep helps to space out players turns 
					Thread.sleep(1000);
					//* help to separate player turns
					
					System.out.println("************************************************************************************************************************************************************");
					System.out.println();
					System.out.println("Hello " + players.get(loop).getName());
					
					//checking to see if game.fieldownership has results to return 
					if (game.fieldOwnership(players.get(loop).getSquares(),players.get(loop)) != null) {

						//assigning values to availableOpportunities
						availableOpportunities = game.fieldOwnership(players.get(loop).getSquares(),players.get(loop));
						System.out.println();
						
						//user prompt
						System.out.println(players.get(loop).getName()+ " do you want to see your Development opportunities? (Y/N).");

						user = sc.next();

						//if user wants to see development opportunities execute.....
						if (user.equalsIgnoreCase("Y")) {
							
							//development method, takes the player and the fields he/she owns as arguments
							Developement(availableOpportunities, players.get(loop));
						}

					}
					
					//user prompt for di roll
					System.out.println("Are you ready for your turn? Press Q to quit the game (Y/N/Q).");

					user = sc.next();

					// if user is ready for they're turn do the following.....
					if (user.equalsIgnoreCase("Y")) {

						// records the players square before rolling the dice
						int oldSquare = players.get(loop).getCurrentSquare();

						// rolls the dice and sets the players current square = to the new square
						// declared from method
						Square currentSquare = players.get(loop).throwDice(board, game);

						// sets players current square to the number of the current square
						players.get(loop).setCurrentSquare(currentSquare.getSquareNumber());

						System.out.println("You have landed on Square " + players.get(loop).getCurrentSquare());

						// passGo check to determine if player is eligible for a passGo bonus
						currentSquare.PassGo(oldSquare, currentSquare.getSquareNumber(), players.get(loop));

						// Checks whether square is a normal type square, if true continues to next
						// iteration of the do while loop as there is no investment opportunities for these squares.
						if (currentSquare.getSquareType().equalsIgnoreCase("Normal Square")) {
							System.out.println(
									players.get(loop).getName() + " congratulations you have completed your turn.");
							System.out.println();

							// continue statement pushes do while on to next iteration
							continue;
						}

						// If condition checks whether an AreaSquare has an owner
						// if the square does not have an owner then investment opportunities are
						// available for the square.
						if ((((AreaSquare) currentSquare).getOwner() != null)
								&& (((AreaSquare) currentSquare).getOwner() != players.get(loop))) {

							payRent((AreaSquare) currentSquare, players.get(loop));
							Thread.sleep(500);

						}
							// check to see if player has run out of resources.
							if (players.get(loop).getResources() == 0) {

								System.out.println(players.get(loop).getName()
										+ " has ran out of resources and is eliminated from the game.");

								System.out.println();
								System.out.println("The developments made by "+players.get(loop).getName()+" have not been demolished, so they are up for grabs........");
								
								//loop iterates through eliminated players developments ownership and sets it to null
								for(AreaSquare square: players.get(loop).getSquares()) {
									System.out.println();
									System.out.println(square.getDevelopementLevel()+" "+ square.getSquareType()+" "+", Number: "+ square.getSquareNumber() );
									
									//ownership set to null
									square.setOwner(null);
								}
								// removing player from the players array removes them from the game.
								players.remove(loop);
								
								
								//continue statement pushes do while onto next iteration 
								continue;
							}

						
					// execute investment method	
					playerInvests(players.get(loop), (AreaSquare) currentSquare);
							
						// confirmation of turn ending
						System.out.println(players.get(loop).getName() + " congratulations you have completed your turn. Your still in the Eco Contest.");
						// line spacing is important throughout helps to keep text readable and the
						// layout friendly
						System.out.println();

					} // end of if Yes

					//if player decides to quit the game execute......
					else if (user.equalsIgnoreCase("Q") ) {
						
						System.out.println();
						//confirmation
						System.out.println("Are you sure you want to quit? (Y/N).");
						
						user=sc.next(); 
						
						if (user.equalsIgnoreCase("Y")) {
							
							System.out.println();
							//goodbye message
							System.out.println("Thats fine I will see you warriors another time :).\n\nResources are: ");
							//display player information
							playerInformation(players);
							
							//game complete 
							gameCompleted=true;
							
							//break out of for loop without this all player must select Q
							break;
						}
					}
					// iterates at the end of the last players go
					if (loop == players.size() - 1) {
						System.out.println("Displaying the current resources for all Players......");

						// initiates the player information method
						playerInformation(players);
					}

					// Winner Checks below:

					// Check to see if the current player has fully developed 2 fields
					// if they have then they are declared the winner

					// initiates check method from the game class
					if (game.FieldCheck(players.get(loop).getSquares())) {

						// winner set
						game.setWinner(players.get(loop));

						// winning message
						System.out.println();
						System.out.println("Winner announced.........");
						Thread.sleep(2000);
						System.out.println();
						System.out.println("The winner of Penny Bags goes green is........ " + game.getWinner().getName()
								+ " congratulations " + game.getWinner().getName() + "!!!! :)");
						System.out.println();
						System.out.println("They were the first player to fully develope two fields. :)");
						
						playerInformation(players);
						
						gameCompleted=true;
					}

					// This if condition checks if only one player remains in the game and declares
					// he/she the winner
					if (players.size() == 1) {

						// iterates through the one person array
						for (Player player : players) {
							// declares its only member the winner
							game.setWinner(player);
							// winning message
							System.out.println("The winner of Penny Bags goes green is........ " + game.getWinner().getName()
									+ " congratulations " + game.getWinner().getName() + "!!!! :)");
						}
						playerInformation(players);
						// boolean set to true, while loop ends, game ends
						gameCompleted = true;
					}

				} // end of for

			} while (!gameCompleted);

			// closing messages for game
			game.closing();
			//closing resources
			 sc.close();

		} catch (NoSuchElementException e) {
			System.err.println("Ths element does not exist.");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Something went wrong. ");
			e.printStackTrace();
		}

	}// end of main
	
	public static ArrayList<Player> setUp (ArrayList<Player>players, double resources, int numberOfPlayers) throws IOException, InterruptedException {

		Scanner sc = new Scanner (System.in);
		String user;
		boolean setUpComplete=false;
		//int count starts at 0 
		int count=0;
		
		//while loop initiates while set up is not complete 
		while (!setUpComplete)  {
			//spacing important for clarity 
			System.out.println();
			if (count >=1) {
				System.out.println("Moving on to my next competitor.............");
				System.out.println();
				
				
			}
			//Enter name
			System.out.println("What name would you like to be called? ");
			user=sc.next();
			//sets user response as part of a player object 
			Player player = new Player (user, 0, resources);
			System.out.println();
			
			//confirmation player has been added to the game 
			System.out.println("Ok "+player.getName()+" your in the game.");
			//adds player to player array 
			players.add(player);
			//checks player for duplicate name and if there is more than 4 players 
			playerChecks(players, 4);
			++count;
			
			//if 2 players are in the game currently asks them if they want to add more or just continue 
			if ((count>1)&&(count<=3)) {
				System.out.println();
				System.out.println("Is there more players to be added? (Y/N)");
				user=sc.next();
				
				//if Y add more players
				if (user.equalsIgnoreCase("Y")) {
					System.out.println();
					System.out.println("Ok ");
					continue;
					
					//if N set up is complete move on to next phase 
				} else {
					setUpComplete=true;
				}
			}
			
			//if number of players is equals to 4 set up is complete
			if (count == numberOfPlayers) {
		setUpComplete=true;
			}
		}
		
		System.out.println();
		System.out.println("Welcome players you have "+resources+" Carbon Credits to save the board. ");
		for (Player player : players) {
			Thread.sleep(1000);
			System.out.println();
			System.out.println(player.getName()+"......you are in the eco sphere.");
		}
		Thread.sleep(1000);
		System.out.println();
		System.out.println("Goooooooo!!!!!!!!");
		return players;
	}
	/**
	 * Method to check that the players array does not contain any duplicates 
	 * and also that the number of players is not set beyond a max limit
	 * @param players
	 * @throws IOException
	 */
	public static void playerChecks (ArrayList<Player>players,int maxPlayers) throws IOException {
		
		
		if (players.size()>maxPlayers) {
			throw new IOException("Too many players please limit player size to 4.");
		}
		
		//using a hashset to check for name duplicates 
		Set<String>duplicateNameCheck = new HashSet<String>();
		
		//adding player names to hash set 
		for (Player player : players) {
			duplicateNameCheck.add(player.getName());
		}
		
		//The hash set automatically removes duplicate objects from its collection 
		//this means that in our case duplicate strings have been removed 
		//if no strings have been removed the hash set will be the same size as the 
		//players array 
		if (duplicateNameCheck.size()<players.size()) {
			throw new IOException("Player names have been duplicated over two or more players.\n\nPlease remove these before continuing.");
		}
	}

	/**
	 * This is a method to run through the procedure for buying a square it performs
	 * necessary checks on whether the square has a current owner whether the player
	 * has enough resources to make the purchase (resources>price) and whether the
	 * buying player wants to go through with the transaction
	 * 
	 * @param player
	 * @param currentSquare
	 */
	public static void playerInvests(Player player, AreaSquare currentSquare) {

		try {
			//user entry for scanner 
			String user;
			//scanner opened 
			Scanner sc = new Scanner(System.in);

			//checking that the square is a null owned square available for purchase
			if (currentSquare.getOwner() == null) {

				//returns square type with space
				System.out.println();
				System.out.println("This is a " + currentSquare.getSquareType() + ", so........");
				
				Thread.sleep(500);
				System.out.println(currentSquare.customInvestMessage(currentSquare));

				user = sc.next();

				//if user wishes to buy this will execute
				if (user.equalsIgnoreCase("Y")) {

					//square price and player resource comparison 
					System.out.println("This square costs " + currentSquare.getPrice());
					System.out.println("You have " + player.getResources());
					System.out.println();
					//user prompt
					System.out.println("Do you want to buy? (Y/N)");
					user = sc.next();

					//if user can and wants to make purchase execute.....
					if ((user.equalsIgnoreCase("Y")) && (player.getResources() > currentSquare.getPrice())) {

						//resources removed
						player.removeResources(currentSquare.getPrice());
						
						//square added to owned squares 
						player.addSquare(currentSquare);
						
						//square owner set to buyer
						currentSquare.setOwner(player);

						//if user does not wish to make purchase
					} else {
						System.out.println("The transaction did not go through. You still have your resources.");
						System.out.println("Resources are " + player.getResources());

					}

				} else {
					System.out.println("No Investment Made.");

				}

				//if square has owner 
			} else {
				System.out.println("This " + currentSquare.getSquareType() + " is owned by "
						+ currentSquare.getOwner().getName() + ".");
			}

			//display the buying players details 
			player.displayAll();

			//try catch
		} catch (NoSuchElementException e) {
			System.err.println("Element does not exist.");
		} catch (Exception e) {
			System.err.println("Something went wrong.");
		}
	}

	/**
	 * This is a method which displays the information for all players using a for
	 * each loop it iterates through each element in the players array and activates
	 * the display all method built into the Player class which prints out to screen
	 * the players current resources, current square and squares owned
	 * 
	 * @param players
	 */
	public static void playerInformation(ArrayList<Player> players) {

		for (Player player : players) {

			player.displayAll();
		}
	}

	/**
	 * This is a method to initiate a transaction from player to player which uses
	 * the boolean properties from the square to determine whether it is a level
	 * 1,2,3 or 4 development and then adjusts the rent accordingly
	 * 
	 * @param square
	 * @param giver
	 * @throws InterruptedException 
	 */
	public static void payRent(AreaSquare square, Player giver) throws InterruptedException {

		int premium = 300;
		if (square.getDevelopementLevel().equalsIgnoreCase("Level 1")) {
			
			Thread.sleep(500);
			//introductory message
			System.out.println("This "+square.getSquareType()+" is a level 1 development, a bit extra is owed this time "+giver.getName()+" i'm afraid");
			
			// player method to pay rent is built into Player class
			// just need to instantiate it and put in the giver, receiver
			// and amount
			giver.payRent(giver, square.getOwner(), square.getRentPrice() +premium);
			
		} else if (square.getDevelopementLevel().equalsIgnoreCase("Level 2")) {
			
			Thread.sleep(500);
			System.out.println("This "+square.getSquareType()+" is a level 2 development a hefty sum is paid my friend ");
			
			giver.payRent(giver, square.getOwner(), square.getRentPrice() +(premium*2));
			
		} else if (square.getDevelopementLevel().equalsIgnoreCase("Level 3")) {
			
			Thread.sleep(500);
			System.out.println("This "+square.getSquareType()+" is a level 3 development so, "+square.getOwner().getName()+" is entitled to some big money from "+giver.getName());
			
			giver.payRent(giver, square.getOwner(), square.getRentPrice() +(premium*3));
			
		} else if (square.getDevelopementLevel().equalsIgnoreCase("Level 4")) {
			
			Thread.sleep(500);
			System.out.println("This "+square.getSquareType()+" is a level 4 development, you owe major money now "+giver.getName());
			
			giver.payRent(giver, square.getOwner(), square.getRentPrice() +(premium*4));
			
		} else {
			System.out.println(giver.getName()+" you owe money to "+square.getOwner().getName());
			giver.payRent(giver, square.getOwner(), square.getRentPrice());
		}
	}

	/**
	 * This method iterates through the development process the method starts by
	 * telling a player what their development opportunities are and then after that
	 * the method asks the player to choose the square number that they wish to
	 * develop.
	 * 
	 * In the transaction phase the method checks whether the user has enough to
	 * make the purchase if so the purchase is made and the square is upgraded if
	 * not then the method ends.
	 * 
	 * @param squaresOwned
	 * @param player
	 */
	public static void Developement(LinkedList<AreaSquare> squaresOwned, Player player) {

		//creating an array list for the non level 4 developments - available opportunities 
		ArrayList<AreaSquare> availableOpportunities = new ArrayList<AreaSquare>();

		//setting up scanner
		Scanner sc = new Scanner(System.in);

		//setting up user input vars 
		String userStr;
		int userint;

		//try
		try {

			//showing player resources and declaring opportunities 
			System.out.println("You have " + player.getResources() + "CC.");
			System.out.println();
			System.out.println("Your investment opportunities are.......");
			System.out.println();

			//iterates through linked list and declares Square developement level
			//and opportunities for relevant squares (non level 4)
			for (AreaSquare square : squaresOwned) {

				if (square.getDevelopementLevel().equalsIgnoreCase("Level 4")) {
					System.out.println("Square number " + square.getSquareNumber() + " is a fully developed "
							+ square.getSquareType() + "..........WOW, not bad ;)");

				} else if (square.getDevelopementLevel().equalsIgnoreCase("Level 3")) {
					System.out.println("Square number " + square.getSquareNumber() + " is a level 3 "
							+ square.getSquareType() + " development.");
					System.out.println("level 4 Opportunities available.......$$$$$$$$$$$$$$$$$......Your nearly at the summit of sustainability!!! Well Done!!");
					System.out.println();
					System.out.println("Alright, This will cost " + square.developmentPrice() + "CC.");
					System.out.println();

					availableOpportunities.add(square);

				} else if (square.getDevelopementLevel().equalsIgnoreCase("Level 2")) {
					System.out.println("Square number " + square.getSquareNumber() + " is a level 2 "
							+ square.getSquareType() + " development.");
					System.out.println("level 3 Opportunities available. $$$$$$$ Impressive $$$$$, you care alot about this development");
					System.out.println();
					System.out.println("Alright, This will cost " + square.developmentPrice() + "CC.");
					System.out.println();

					availableOpportunities.add(square);

				} else if (square.getDevelopementLevel().equalsIgnoreCase("Level 1")) {
					System.out.println("Square number " + square.getSquareNumber() + " is a level 1 "
							+ square.getSquareType() + " development.");
					System.out.println("level 2 Opportunities available........Climbing the tree to Success I see........");
					System.out.println();
					System.out.println("Alright , This will cost " + square.developmentPrice() + "CC.");
					System.out.println();

					availableOpportunities.add(square);

				} else if (square.getDevelopementLevel().equalsIgnoreCase("Level 0")) {
					System.out.println("Square number " + square.getSquareNumber() + " is a " + square.getSquareType()
							+ " that has been purchased.");
					System.out.println("level 1 Opportunities available.............sustainability takes time, lets gets started!!!!");
					System.out.println();
					System.out.println("Alright, This will cost " + square.developmentPrice() + "CC.");
					System.out.println();

					//non level 4 squares are added to availableOpportunities
					availableOpportunities.add(square);

				}

			}

			//condition for while loop
			boolean choiceComplete = false;
			//space
			System.out.println();
			
			//user prompt
			System.out.println("Do you want to develop one of these squares "+player.getName()+"?");

			userStr = sc.next();

			//if user wishes to develop execute.....
			if (userStr.equalsIgnoreCase("Y")) {

				//while loop means that if user makes an incorrect choice they are
				//restored to start again 
				while (!choiceComplete) {
					
					System.out.println("Ok what square do you want to develop? Enter the Square Number from the choices above.");

					//scanner for user integer entry 
					userint = sc.nextInt();

					//for each loop iterates through availableOpportunities
					for (AreaSquare square : availableOpportunities) {

						//if square number matches user entry move forward to confirmation
						if (square.getSquareNumber() == userint) {
							
							//displaying price and user resources 
							System.out.println("This development costs " + square.developmentPrice() + "CC.");
							System.out.println("You have " + player.getResources() + "CC.");
							System.out.println();
							
							//user prompt
							System.out.println("Do you want to develop?");
							userStr = sc.next();
							
							//if user wishes to develop finalise transaction
							if ((userStr.equalsIgnoreCase("Y") && (player.getResources() > square.developmentPrice()))) {
								
								//remove player resources 
								player.removeResources(square.developmentPrice());
								
								//level up the development on the square 
								square.developmentLevelUp(square, player);
								
								//declare that the user has made their choice (end while)
								choiceComplete = true;
								
								//if player does not wish to make transaction or does not have enough resources 
								//execute......
							} else {
								
								System.out.println();
								
								//confirmation of failed transaction
								System.out.println("Transaction did not go through, your resources are " + player.getResources());
								System.out.println();
								
								//asks user if they want to end transaction process
								System.out.println("Do you want to quit? ");
								
								userStr =sc.next();
								
								//if user wnats to end transaction process execute.....
								if (userStr.equalsIgnoreCase("Y")) {
									System.out.println();
									System.out.println("Ok");
									//declare that the user has made their choice (end while)
									choiceComplete=true;
								}
							}
							
							
							//if choice not complete while will go for another iteration
						}  else {
							System.out.println();
						}
						
					}
				
				}
				
				//confirmation of cancelled transaction at first stage if player did not want to proceed
			} else {
				System.out.println("That is ok cancelling transaction. Your resources are " + player.getResources());

			}

			//try catch general exception
		} catch (Exception e) {
			System.err.println("Something went wrong.");
		}

	}

}// end of class
