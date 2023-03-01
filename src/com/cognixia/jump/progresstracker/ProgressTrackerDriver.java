package com.cognixia.jump.progresstracker;

import java.lang.invoke.WrongMethodTypeException;
import java.util.Scanner;

import com.cognixia.jump.progresstracker.dao.*;

public class ProgressTrackerDriver {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		boolean flag = true;
		while (flag == true) {
			System.out.println("What is your username?");
			try {
				// get username
				// check username
				// if user ain't there, redo
			} catch (WrongMethodTypeException e) {
				// sdfasdf
			}
		}
		
		

	}
	
	public boolean checkUserRole(User user) {
		if (user.getRoleType() == 1) {
			return true;
		}
		return false;
	}

}
