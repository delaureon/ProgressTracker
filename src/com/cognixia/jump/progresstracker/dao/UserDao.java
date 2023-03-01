package com.cognixia.jump.progresstracker.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;



public interface UserDao {
	
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;
	
	// Obtains the list of user shows
	public boolean getShows(int id);
	
	public Optional<Show> getShowById(int id);
	
	public Optional<User> authenticateUser(String username, String password);
	

	// This will be used to allow the user to categorize into not started, in-progress, or completed
	public boolean updateShowProgress();
	
	// Admin class that implements this interface will have create, update, and delete functions within its own class
	
	
}