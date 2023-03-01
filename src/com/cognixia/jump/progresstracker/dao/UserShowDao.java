package com.cognixia.jump.progresstracker.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface UserShowDao {
	
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;

	public boolean getUserShows(int id);
	
	public boolean updateShows(int id);
	
	public boolean addShows(int id);
	
}
