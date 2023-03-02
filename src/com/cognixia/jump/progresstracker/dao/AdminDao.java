package com.cognixia.jump.progresstracker.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AdminDao {
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;
	public void getAllShows();
	public boolean createShow(Show show);
	public boolean deleteShow(int id);
	public boolean updateShow(Show show);
	public Optional<Show> getShowById(int id);
}
