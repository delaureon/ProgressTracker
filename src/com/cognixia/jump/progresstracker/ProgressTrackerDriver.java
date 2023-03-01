package com.cognixia.jump.progresstracker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.invoke.WrongMethodTypeException;
import java.sql.SQLException;
import java.util.Scanner;

import com.cognixia.jump.progresstracker.dao.UserDao;
import com.cognixia.jump.progresstracker.dao.UserDaoSql;
import com.cognixia.jump.progresstracker.dao.UserShowDaoSql;

//import com.cognixia.jump.progresstracker.dao.*;

public class ProgressTrackerDriver {
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		String username = "";
		String password = "";
		String quit = "";
		System.out.println("Welcome to the TV Show Tracker!");
		while (!quit.equals("quit")) {
			try {
				do {
					System.out.println("What is your username?");
					username = scan.next();
					System.out.println("What is your password?");
					password = scan.next();
					checkUser(username, password);
					if (checkUser(username, password) == false) {
						System.out.println("The data you have entered does not match. Please try again.");
					}
					System.out.println("Do you want to continue?");
					quit = scan.next();
					
					
				} while (checkUser(username, password) != true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean checkUser(String username, String password) {
		// check user data with database
		UserDao userDao = new UserDaoSql();
//		UserShowDaoSql userShowDao = new UserShowDaoSql();
		try {
			userDao.setConnection();
			userDao.authenticateUser(username, password);
			
			
			userDao.getShows(2);
			
//			userShowDao.getUserShows(1);
			
			
			return true;
			
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
		

		return false;
	}
	
}