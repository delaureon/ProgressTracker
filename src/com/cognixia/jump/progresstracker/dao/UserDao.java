package com.cognixia.jump.progresstracker.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;



public interface UserDao {
	
public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;
	
//	public List<Department> getAllDepartment();
//	
//	public Optional<Department> getDepartmentById(int id);
//	
//	public boolean createDepartment(Department dept);
//	
//	public boolean deleteDepartment(int id);
//	
//	public boolean updateDepartment(Department dept);
	
}
