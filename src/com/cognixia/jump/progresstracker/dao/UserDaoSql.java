package com.cognixia.jump.progresstracker.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cognixia.jump.connection.BetterConnectionManager;

public class UserDaoSql implements UserDao {
	
	// Connection used to connect to database
	private Connection conn;

	@Override
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		conn = BetterConnectionManager.getConnection();
		
	}

	@Override
	// Obtains all the shows in the database for the user to see
	public List<Show> getAllShows() {
		
		// List to store all the current shows in the database
		// This view will allow the user to pick and choose the shows they want to add to their list
		// Different methods will be made to obtain shows pertaining to the user
		List<Show> showList = new ArrayList<Show>();
		
		try(Statement stmnt = conn.createStatement();
			ResultSet rs = stmnt.executeQuery("select * from shows")
		   ){
			
			
			while(rs.next()) {
				
				int showId = rs.getInt("Show_ID");
				String name = rs.getString("Name");
				String descript = rs.getString("Descript");
				int numEp = rs.getInt("TotalEps");
				
				Show show = new Show(showId, name, descript, numEp);
				
				showList.add(show);
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	
		return showList;
	}

	@Override
	public Optional<Show> getShowById(int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
