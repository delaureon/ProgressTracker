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

			System.out.print("Username: ");
			username = scan.next();
			System.out.print("Password: ");
			password = scan.next();
			User u1 = checkUser(username, password);
			if (u1.getRoleType() == 0) {
				printUserShows(u1.getUserId());
				promptUserActions();
				
			} else {
				do {
					AdminDaoSql a1=new AdminDaoSql();
					a1.setConnection();
					a1.getAllShows();
					System.out.println("Press 1 to add a new show to your list or 2 to update a status");
					menuChoice = scan.next();
					if (menuChoice.equals("1")) {
					 System.out.print("Show Name: ");
					 String sName=scan.nextLine();
					 scan.nextLine();
					 System.out.print("Description: ");
					 String desc=scan.nextLine();

					 System.out.println("Number of Episodes:");
					 int numEps=scan.nextInt();
					 Show s1=new Show(sName,desc,numEps);
					 a1.createShow(s1);
					 a1.getAllShows();
					} else if (menuChoice.equals("2")) {
                      
					}

				} while (!menuChoice.equals("1")|| menuChoice.equals("2"));
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
			if (currUser.isEmpty()) {
//				throw UserNotFoundException();
			}

			validUser = currUser.get();
			return validUser;

		} catch (FileNotFoundException e) {
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

	public static void promptUserActions() {
		
		String option1 = "1-Add Shows", option2 = "2-Update Progress";
		System.out.println("\nWhat would you like to do?");
		System.out.printf("%10s %20s\n",option1, option2);
		UserDao userDao = new UserDaoSql();
		
		try(Scanner scan = new Scanner(System.in)) {
			userDao.setConnection();
			int input = scan.nextInt();
			
			if(input == 1) {
				userDao.getAllShows();
				System.out.print("\nAdd show by ID: ");
				int id = scan.nextInt();
			}
			
		} catch (InputMismatchException e) {
			System.out.println("Invalid choice entered");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}