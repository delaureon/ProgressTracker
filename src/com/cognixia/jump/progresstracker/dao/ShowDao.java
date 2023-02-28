package com.cognixia.jump.progresstracker.dao;

import java.util.List;
import java.util.Optional;

public interface ShowDao {
public List<Show> getAllShows();
public Optional<Show> getShowByID(int id);
public boolean createDepartment(Show dept);
public boolean deleteDepartment(int id);
public boolean updateDepartment(Show dept);
}
