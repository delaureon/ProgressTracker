package com.cognixia.jump.progresstracker.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
	public List<Show> getUserShows() {
		
		// List to store all the current shows in the database
		// This view will allow the user to pick and choose the shows they want to add to their list
		// Different methods will be made to obtain shows pertaining to the user
		List<Show> showList = new ArrayList<Show>();
		
		try(Statement stmnt = conn.createStatement();
			// We'll fill in the with the proper query later
			ResultSet rs = stmnt.executeQuery("")
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
try(PreparedStatement pstmt = conn.prepareStatement("select * from shows where Show_ID = ?")) {
			
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			
			// rs.next() will return false if nothing found
			// if you enter the if block, that show with that id was found
			if(rs.next()) {
				
				int showId = rs.getInt("Show_ID");
				String name = rs.getString("Name");
				String descript = rs.getString("Descript");
				int numEp = rs.getInt("TotalEps");
				
				rs.close();
				
				// constructing the object
				Show currShow = new Show(showId, name, descript, numEp);
				
				// placing it in the optional
				Optional<Show> showFound = Optional.of(dept);
				
				// return the optional
				return showFound;
			}
			else {
				// if you did not find the department with that id, return an empty optional
				rs.close();
				return Optional.empty();
			}
			
			
		} catch (SQLException e) {
			
			// just in case an exception occurs, return nothing in the optional
			return Optional.empty();
		} catch (Exception e) {
			
		}
		return Optional.empty();
	}

}
