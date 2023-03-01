package com.cognixia.jump.progresstracker;

import java.lang.invoke.WrongMethodTypeException;

import java.util.Scanner;

import com.cognixia.jump.progresstracker.dao.*;

public class ProgressTrackerDriver {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		String username = "";
		String password = "";
		
		System.out.println("Welcome to the TV Show Tracker!");
		while (!scan.equals("quit")) {
			try {
				
				do {
//					System.out.println("Please enter 0 if you're a user or 1 if you're an admin:");
//					role = scan.nextInt();
					System.out.println("What is your username?");
					username = scan.next();
					System.out.println("What is your password?");
					password = scan.next();
					checkUser(username, password);
					if (checkUser(username, password) == null) {
						System.out.println("The data you have entered does not match. Please try again.");
					}
				} while (checkUser(username, password) != null);
				
				User user = checkUser(username, password);
				
				// 
				
				
				
				
				
				
				
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
