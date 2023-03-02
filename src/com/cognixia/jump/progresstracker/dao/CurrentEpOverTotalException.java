package com.cognixia.jump.progresstracker.dao;

public class CurrentEpOverTotalException extends Exception {

		public CurrentEpOverTotalException(int current, int total) {
			super("Invalid number of current episodes: " + current + ". Out of scope of total number: " + total);
		}
}
