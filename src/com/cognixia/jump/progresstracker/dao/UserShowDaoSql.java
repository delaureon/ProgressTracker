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

import com.cognixia.jump.connection.BetterConnectionManager;

public class UserShowDaoSql implements UserShowDao {
	
	private Connection conn;
	
	@Override
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		conn = BetterConnectionManager.getConnection();
	}

	@Override
	public boolean getUserShows(int id) {
		// List to store all the current shows in the database or the user's show based
		// on parameter
		

		try (PreparedStatement pstmt = conn.prepareStatement("select Users_Shows.ShowID showid, Name, CurrentEp,Rating , totalEps, ((CurrentEp /totalEps)*100) as percentcomplete "
				+ "from users join Users_Shows on users.UserID=Users_Shows.UserID "
				+ "join Shows on Users_Shows.ShowID=Shows.ShowID "
				+ "where users.UserID = ?")){
			
			
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();
				
				
				
			

			while (rs.next()) {

				int showId = rs.getInt("showid");
				String name = rs.getString("Name");
				int currEp = rs.getInt("CurrentEp");
				int rating = rs.getInt("Rating");
				int totalEp = rs.getInt("totalEps");

				System.out.println(showId + " " + name + " " + currEp + " " + rating + " " + totalEp);
			}
			
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean updateShows(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addShows(int id) {
		// TODO Auto-generated method stub
		return false;
	}


}
