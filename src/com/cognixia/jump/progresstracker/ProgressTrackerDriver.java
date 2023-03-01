package com.cognixia.jump.progresstracker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.invoke.WrongMethodTypeException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

import com.cognixia.jump.progresstracker.dao.User;
import com.cognixia.jump.progresstracker.dao.UserDao;
import com.cognixia.jump.progresstracker.dao.UserDaoSql;
import com.cognixia.jump.progresstracker.dao.UserShowDaoSql;

//import com.cognixia.jump.progresstracker.dao.*;

public class ProgressTrackerDriver {
	
	public static void main(String[] args) {
		
		String username = null;
		String password = null;
		System.out.println("Welcome to the TV Show Tracker!");
	
		// Use try-with-resources to automatically close scanner
		try(Scanner scan = new Scanner(System.in)) {
			
				System.out.println("What is your username?");
				username = scan.next();
				System.out.println("What is your password?");
				password = scan.next();
				User u1 = checkUser(username, password);
				printUserShows(u1.getUserId());
				
				
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
			if(currUser.isEmpty()) {
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
}