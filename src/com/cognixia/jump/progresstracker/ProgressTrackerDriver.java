package com.cognixia.jump.progresstracker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.invoke.WrongMethodTypeException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import com.cognixia.jump.progresstracker.dao.AdminDaoSql;
import com.cognixia.jump.progresstracker.dao.CurrentEpOverTotalException;
import com.cognixia.jump.progresstracker.dao.Show;
import com.cognixia.jump.progresstracker.dao.User;
import com.cognixia.jump.progresstracker.dao.UserDao;
import com.cognixia.jump.progresstracker.dao.UserDaoSql;
import com.cognixia.jump.progresstracker.dao.UserNotFoundException;
import com.cognixia.jump.progresstracker.dao.UserShow;


//import com.cognixia.jump.progresstracker.dao.*;

public class ProgressTrackerDriver {

	public static void main(String[] args) {

		String username = null;
		String password = null;
		String menuChoice ;
		System.out.println("Welcome to the TV Show Tracker!");

		// Use try-with-resources to automatically close scanner
		try (Scanner scan = new Scanner(System.in)) {
			User u1;
			int choice;
			do {
				System.out.println("1- Sign Up\t2- Login \t0-Exit");
				choice = scan.nextInt();
				if (choice == 1) {
					SignUp(scan);
				} else if (choice == 2) {
					do {
						System.out.print("Username: ");
						username = scan.next();
						System.out.print("Password: ");
						password = scan.next();
						u1 = checkUser(username, password);
					}
					while (u1.getUsername() == null);
					System.out.println("Welcome " + u1.getUsername() + "!" + "\n\n");

					if (u1.getRoleType() == 0) {
						printUserShows(u1.getUserId());
						String input;
						do {
							input = promptUserActions(u1, scan);
						} while (!input.equals("q"));

					} else {
						do {
							AdminDaoSql a1 = new AdminDaoSql();
							a1.setConnection();
							menuChoice = promptAdminActions(scan);


							//------------------
							// CREATE NEW SHOW
							//------------------
							if (menuChoice.equals("1")) {

								System.out.print("Show Name: ");
								String sName = scan.nextLine();

								System.out.print("Description: ");
								String desc = scan.nextLine();

								System.out.println("Number of Episodes:");
								int numEps = scan.nextInt();

								Show s1 = new Show(sName, desc, numEps);
								a1.createShow(s1);
								a1.getAllShows();


								//------------------
								// UPDATE SHOW
								//------------------
							} else if (menuChoice.equals("2")) {

								a1.getAllShows();
								System.out.print("Enter Show ID for update: ");
								int id = scan.nextInt();
								Optional<Show> show = a1.getShowById(id);
								if (show.isPresent()) {

									Show validShow = show.get();

									String option1 = "1-Name", option2 = "2-Description", option3 = "3-Total number of episodes";
									System.out.println("What would you like to update");
									System.out.printf("%-20s %-20s %-20s\n", option1, option2, option3);
									String option = scan.next();

									if (option.equals("1")) {
										System.out.print("Enter new name: ");
										scan.nextLine();
										String name = scan.nextLine();
										validShow.setShowName(name);
									} else if (option.equals("2")) {
										System.out.print("Enter new description: ");
										scan.nextLine();
										String descript = scan.nextLine();
										validShow.setDesc(descript);
									} else if (option.equals("3")) {
										System.out.print("Enter new number of total episodes: ");
										int totalNum = scan.nextInt();
										validShow.setNumEp(totalNum);
									}


									a1.updateShow(validShow);
									a1.getAllShows();
								} else {
									System.out.println("Invalid show entered");
								}


								//------------------
								// DELETE SHOW
								//------------------
							} else if (menuChoice.equals("3")) {

								a1.getAllShows();
								System.out.println("Enter Show ID to delete: ");
								int id = scan.nextInt();

								boolean deleted = a1.deleteShow(id);

								if (deleted) {
									System.out.println("Deleted successfully");
									a1.getAllShows();
								} else {
									System.out.println("Could not delete");
								}

							} else if (menuChoice.equals("q")) {
								System.out.println("Exiting program...");
								break;
							}

						} while (menuChoice.equals("1") || menuChoice.equals("2") || menuChoice.equals("3") || menuChoice.equals("q"));
					}
				}
			} while (choice != 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static User checkUser(String username, String password) {
		// check user data with database
		UserDao userDao = new UserDaoSql();
		User validUser = new User();

		try {
			// Connects to the database
			userDao.setConnection();

			// authenticate user and return user if authentication went well
			Optional<User> currUser = userDao.authenticateUser(username, password);

			// We can create a custom exception if the user is not found
			if (currUser.get().getUsername() == null) {
				throw new UserNotFoundException();
			}

			validUser = currUser.get();
			return validUser;

		} catch (UserNotFoundException e) {
			System.out.println(e.getMessage());
		}  catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return validUser;
	}

	public static void printUserShows(int id) {

		UserDao userDao = new UserDaoSql();

		try {
			userDao.setConnection();
			userDao.getShows(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String promptUserActions(User user,Scanner scan) {
		

		String option1 = "1-Add Show", option2 = "2-Update Progress",option3="3-View Favorites", option9= "4-Delete Show", option0 = "q-Log out";
		System.out.println("\nWhat would you like to do?");
		System.out.printf("%-20s %-20s %-20s %-20s %-20s\n", option1, option2, option3,option9, option0);

		UserDao userDao = new UserDaoSql();
		
		try {
			userDao.setConnection();
			String input = scan.next();
			
			if(input.equals("1")) {
				userDao.getAllShows(user.getUserId());
				System.out.print("\nAdd show by ID: ");
				int showId = scan.nextInt();
				int userId = user.getUserId();
				
				System.out.println("What's your progress on the show?");
				String progress1 = "1-Not Started", progress2 = "2-In Progress", progress3 = "3-Completed";
				System.out.printf("%-20s %-20s %-20s\n", progress1, progress2, progress3);
				int progressId = scan.nextInt();
				
				System.out.println("What would you rate the show (1-5)? ");
				int rating = scan.nextInt();
				
				System.out.println("What episode are you on?");
				int currEp = scan.nextInt();
				Optional<Show> currShow = userDao.getShowById(showId);
				if(currShow.isPresent()) {
					Show validShow = currShow.get();

						if (currEp > validShow.getNumEp()) {
							try {
								throw new CurrentEpOverTotalException(currEp, validShow.getNumEp());
							}catch (CurrentEpOverTotalException e) {
								System.out.println("Invalid number");
							}
						}else{
							UserShow userShow = new UserShow(userId, showId, progressId, rating, currEp);
							userDao.addShows(userShow);

							userDao.getShows(user.getUserId());

						}
					}
				
			} else if(input.equals("2")) {
				// Gets all the user shows so user knows which one to update
				userDao.getShows(user.getUserId());
				System.out.println("\nUpdate progress by Show ID: ");
				int showId = scan.nextInt();
				Optional<Show> currShow = userDao.getShowById(showId);
				
				
				if(currShow.isPresent()) {
					Show validShow = currShow.get();
					Optional<UserShow> showToUpdate = userDao.getUserShow(user.getUserId(),showId);
					UserShow s2U=showToUpdate.get();
					System.out.println("\nWhat would you like to update?");
					String option4 = "1-Progress", option5 = "2-Rating", option6 = "3-Current Episode";
					System.out.printf("%-20s %-20s %-20s\n", option4, option5, option6);
					int choice = scan.nextInt();
					
					
					if(choice == 1) {
						
						System.out.println("What is your current progress?");
						String progress1 = "1-Not Started", progress2 = "2-In Progress", progress3 = "3-Completed";
						System.out.printf("%-20s %-20s %-20s\n", progress1, progress2, progress3);
						int progressId = scan.nextInt();

					
						
						
						s2U.setProgressID(progressId);
						userDao.updateShows(s2U);
						userDao.getShows(user.getUserId());
						
						
					} else if (choice == 2) {
						System.out.println("How would you rate the show (1-5)?");
						int rating = scan.nextInt();
						s2U.setRating(rating);
						userDao.updateShows(s2U);
						userDao.getShows(user.getUserId());
					} else if (choice == 3) {
						System.out.println("What episode are you currently on?");
						int currEp = scan.nextInt();
						Optional<Show> currShow1 = userDao.getShowById(showId);
						if(currShow1.isPresent()) {
							Show validShow1 = currShow1.get();
						try {
							validateShowNumber(validShow1, currEp);
							s2U.setCurrEp(currEp);
							userDao.updateShows(s2U);
						} catch (CurrentEpOverTotalException e) {
							System.out.println(e.getMessage());
						}
						}

						userDao.getShows(user.getUserId());
					}

				}

			} else if(input.equals("3")) {
				int menuChoice;
				do {
					userDao.getFavorites(user.getUserId());

					System.out.printf("%20s %20s %20s\n", "1- Add Favorite", "2- Remove Favorite", "0- Back");
					menuChoice = scan.nextInt();
					int showID;
					if (menuChoice == 1) {
						System.out.print("Show ID:");
						showID = scan.nextInt();
						Optional<UserShow> userShow = userDao.getUserShow(user.getUserId(), showID);
						if (userShow.isEmpty()) {
							System.out.println("This show is not on your list");
						} else {
							userDao.addFavorite(userShow.get());
						}
					}else if(menuChoice==2){
						System.out.print("Show ID:");
						showID= scan.nextInt();
						userDao.removeFavorite(user.getUserId(),showID);
					}
				} while (menuChoice != 0);
			}else if(input.equals("4")) {
				System.out.println("Which Show Would you like to delete?");
				int showId = scan.nextInt();
				userDao.deleteUserShowById(showId);
			}
			else if(input.equals("q")) {
				System.out.println("Signing out...");
			
			}
			
			return input;
		} catch (InputMismatchException e) {
			System.out.println("Invalid choice entered");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String promptAdminActions(Scanner scan) {
		
		String option1 = "1-Create Show", option2 = "2-Update Show", option3 = "3-Delete Show", option4 = "q-Quit";
		System.out.println("\nWhat would you like to do?");
		System.out.printf("%-20s %-20s %-20s %-20s\n",option1, option2, option3, option4);
		String menuChoice = null;
		
		try {
			
			menuChoice = scan.next();
			scan.nextLine();
			
			return menuChoice;
			
		} catch (InputMismatchException e) {
			System.out.println("Invalid choice entered");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	public static void SignUp(Scanner scanner){
		System.out.print("Username:");
		String username= scanner.next();
		System.out.print("Password:");
		String password= scanner.next();
		User user=new User(username,password);
		UserDaoSql userDaoSql=new UserDaoSql();
		try {
			userDaoSql.setConnection();
			if(userDaoSql.addUser(user)) System.out.println("User Added");
		} catch (ClassNotFoundException | IOException | SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public static void validateShowNumber(Show show, int currentEp)throws CurrentEpOverTotalException{
		if(currentEp>show.getNumEp()){
			throw new CurrentEpOverTotalException(currentEp,show.getNumEp());
		}
	}
}