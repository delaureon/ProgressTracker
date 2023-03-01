package com.cognixia.jump.progresstracker;

import java.lang.invoke.WrongMethodTypeException;

import java.util.Scanner;

import com.cognixia.jump.progresstracker.dao.*;

public class ProgressTrackerDriver {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		int role = 0;
		String username = "";
		String password = "";
		System.out.println("Welcome to the TV Show Tracker!");
		while (!scan.equals("quit")) {
			try {
				do {
					System.out.println("Please enter 0 if you're a user or 1 if you're an admin:");
					role = scan.nextInt();
					System.out.println("What is your username?");
					username = scan.nextLine();
					System.out.println("What is your password?");
					password = scan.nextLine();
					checkUser(role, username, password);
					if (checkUser(role, username, password) == false) {
						System.out.println("The data you have entered does not match. Please try again.");
					}
				} while (checkUser(role, username, password) != true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean checkUser(int role, String username, String password) {
		// check user data with database
		// return true;
		return false;
	}

}
