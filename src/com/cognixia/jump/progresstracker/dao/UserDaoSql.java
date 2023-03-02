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
	public boolean getShows(int id) {
		
		try (PreparedStatement pstmt = conn.prepareStatement("select Users_Shows.ShowID showid, Name, CurrentEp,Rating , totalEps, ((CurrentEp /totalEps)*100) as percentcomplete "
				+ "from users join Users_Shows on users.UserID=Users_Shows.UserID "
				+ "join Shows on Users_Shows.ShowID=Shows.ShowID "
				+ "where users.UserID = ?")){
				
				// Obtain the shows for the currently logged on user
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();
//	
				System.out.printf("%10s %20s %20s %15s %15s %20s", "Show ID","Name", "Current Episode", "Rating","Total Episodes", "Percent Complete");
				System.out.println("\n----------------------------------------------------------------------------------------------------------------");
			while (rs.next()) {

				int showId = rs.getInt("showid");
				String name = rs.getString("Name");
				int currEp = rs.getInt("CurrentEp");
				int rating = rs.getInt("Rating");
				int totalEp = rs.getInt("totalEps");
				int percComp=rs.getInt("percentcomplete");
				
				System.out.printf("%10s %20s %20s %15s %15d %20c%n", showId, name, currEp, rating,totalEp,percComp);
//				System.out.println("Show ID: " + showId + " " 
//									+ "Title: " + name + " " 
//									+ "Episode: "+ currEp + "/" + totalEp + " "
//									+ "Rating: " + rating
//								  );
//				
				
			}
			
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
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
				Optional<Show> showFound = Optional.of(currShow);
				
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

	@Override
	public Optional<User> authenticateUser(String username, String password) {
		
		try(PreparedStatement pstmnt = conn.prepareStatement("select * from users where username = ? and password = ?")) {
			
			// Obtain username and password arguments and insert them into the prepare statement
			pstmnt.setString(1, username);
			pstmnt.setString(2, password);
			
			ResultSet rs = pstmnt.executeQuery();
			
			int id = 0;
			String uName = null;
			String pwd = null;
			int role = 0;
			
			while(rs.next()) {
				
				 id = rs.getInt("userid");
				 uName = rs.getString("username");
				 pwd = rs.getString("password");
				 role = rs.getInt("roletype");
			}
			User user = new User(id, uName, pwd, role);
			
			Optional<User> userFound = Optional.of(user);
			System.out.println("Welcome "+ uName +"!" +"\n\n");
			return userFound;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Optional.empty();
	}


	
	@Override
	public boolean updateShows(int id) {
		
		System.out.println();
		
		
		return false;
	}
	


	@Override
	public boolean addShows(int id) {
		// TODO Auto-generated method stub
		return false;
	}


	

}
