package com.cognixia.jump.progresstracker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.invoke.WrongMethodTypeException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import com.cognixia.jump.progresstracker.dao.AdminDaoSql;
import com.cognixia.jump.progresstracker.dao.Show;
import com.cognixia.jump.progresstracker.dao.User;
import com.cognixia.jump.progresstracker.dao.UserDao;
import com.cognixia.jump.progresstracker.dao.UserDaoSql;
import com.cognixia.jump.progresstracker.dao.UserNotFoundException;
import com.cognixia.jump.progresstracker.dao.UserShowDaoSql;

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
			do {
			System.out.print("Username: ");
			username = scan.next();
			System.out.print("Password: ");
			password = scan.next();
			u1 = checkUser(username, password);
			} while (u1.getUsername() == null);
			System.out.println("Welcome "+ u1.getUsername() +"!" +"\n\n");
			
			if (u1.getRoleType() == 0) {
				printUserShows(u1.getUserId());
				promptUserActions(u1);
				
				
				
				
				
				
				
				
			} else {
				do {
					AdminDaoSql a1=new AdminDaoSql();
					a1.setConnection();
					menuChoice = promptAdminActions(scan);
					
					
					//------------------
					// CREATE NEW SHOW
					//------------------
					if (menuChoice.equals("1")) {
						
					 System.out.print("Show Name: ");
					 String sName=scan.nextLine();
					 
					 System.out.print("Description: ");
					 String desc=scan.nextLine();

					 System.out.println("Number of Episodes:");
					 int numEps=scan.nextInt();
					 
					 Show s1=new Show(sName,desc,numEps);
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
						if(show.isPresent()) {
							
							Show validShow = show.get();
							
							String option1 = "1-Name", option2 = "2-Description", option3 = "3-Total number of episodes";
							System.out.println("What would you like to update");
							System.out.printf("%-20s %-20s %-20s\n",option1, option2, option3);
							String option = scan.next();
							
							if(option.equals("1")) {
								System.out.print("Enter new name: ");
								scan.nextLine();
								String name = scan.nextLine();
								validShow.setShowName(name);
							} else if(option.equals("2")) {
								System.out.print("Enter new description: ");
								scan.nextLine();
								String descript = scan.nextLine();
								validShow.setDesc(descript);
							} else if(option.equals("3")) {
								System.out.print("Enter new number of total episodes: ");
								int totalNum = scan.nextInt();
								validShow.setNumEp(totalNum);
							}
							

							a1.updateShow(validShow);
						} else {
							System.out.println("Invalid show entered");
						}
						
				
					//------------------
					// DELETE SHOW
					//------------------
					} else if (menuChoice.equals("3")){
						
						a1.getAllShows();
						System.out.println("Enter Show ID to delete: ");
						int id = scan.nextInt();
						
						boolean deleted = a1.deleteShow(id);	
						
						if(deleted) {
							System.out.println("Deleted successfully");
						}else {
							System.out.println("Could not delete");
						}
						
					} else if(menuChoice.equals("q")) {
						System.out.println("Exiting program...");
						break;
					}
				
				} while (menuChoice.equals("1") || menuChoice.equals("2") || menuChoice.equals("3") || menuChoice.equals("q") );
			}
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

	public static void promptUserActions(User user) {
		
		String option1 = "1-Add Show", option2 = "2-Update Progress", option3 = "q-Quit";
		System.out.println("\nWhat would you like to do?");
		System.out.printf("%-20s %-20s %-20s\n", option1, option2, option3);
		UserDao userDao = new UserDaoSql();
		
		try(Scanner scan = new Scanner(System.in)) {
			userDao.setConnection();
			String input = scan.next();
			
			if(input.equals("1")) {
				userDao.getAllShows();
				System.out.print("\nAdd show by ID: ");
				int showId = scan.nextInt();
				int userId = user.getUserId();
				
				System.out.println("Where would you like to add it?");
				String progress1 = "1-Not Started", progress2 = "2-In Progress", progress3 = "3-Completed";
				System.out.printf("%-20s %-20s %-20s\n", progress1, progress2, progress3);
				int choice = scan.nextInt();
				
				
				
			} else if(input.equals("2")) {
				// Gets all the user shows so user knows which one to update
				userDao.getShows(user.getUserId());
				System.out.println("\nUpdate progress by Show ID: ");
				int showId = scan.nextInt();
				Optional<Show> currShow = userDao.getShowById(showId);
				
				if(currShow.isPresent()) {
					Show validShow = currShow.get();
					//userDao.updateShows(validShow);
					
				} else {
					// Exception here?
				}
				
			} else if(input.equals("q")) {
				System.out.println("Exiting program...");
			}
			
			
		} catch (InputMismatchException e) {
			System.out.println("Invalid choice entered");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
	
}