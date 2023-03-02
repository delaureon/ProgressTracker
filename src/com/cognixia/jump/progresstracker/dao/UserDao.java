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
	
	public boolean getAllShows();
	
	public Optional<Show> getShowById(int id);
	
	public Optional<User> authenticateUser(String username, String password);
	
	public boolean updateShows(int id);
	
	public boolean addShows(int id);
	
	// Admin class that implements this interface will have create, update, and delete functions within its own class
	
	
}