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
		
		try (PreparedStatement pstmt = conn.prepareStatement("select Users_Shows.ShowID, progress.progress,Users.Userid,Name, CurrentEp,Rating , totalEps, ((CurrentEp /totalEps)*100) as percentcomplete "
				+ "from users "
				+ "join Users_Shows on users.UserID=Users_Shows.UserID "
				+ "join progress on Users_Shows.ProgressID=progress.ProgressID "
				+ "join Shows on Users_Shows.ShowID=Shows.ShowID "
				+ "where Users.userid=?;")){
				
				// Obtain the shows for the currently logged on user
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();
//	
				System.out.printf("%10s %20s %20s %20s %15s %15s %20s", "Show ID","Name", "Current Episode","Progress", "Rating","Total Episodes", "Percent Complete");
				System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------");
			while (rs.next()) {

				int showId = rs.getInt("showid");
				String name = rs.getString("Name");
				int currEp = rs.getInt("CurrentEp");
				String progress=rs.getString("progress");
				int rating = rs.getInt("Rating");
				int totalEp = rs.getInt("totalEps");
				int percComp=rs.getInt("percentcomplete");
				
				System.out.printf("%10s %20s %20s %20s %15s %15d %20s%n", showId, name, currEp,progress, rating,totalEp,(percComp+ "%"));			
				
			}
			
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Optional<Show> getShowById(int id) {
try(PreparedStatement pstmt = conn.prepareStatement("select * from shows where ShowID = ?")) {
			
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			
			// rs.next() will return false if nothing found
			// if you enter the if block, that show with that id was found
			while(rs.next()) {
				
				int showId = rs.getInt("ShowID");
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
			
				// if you did not find the department with that id, return an empty optional
				rs.close();
				return Optional.empty();
			
			
			
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
//			System.out.println("Welcome "+ uName +"!" +"\n\n");
			return userFound;
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Optional.empty();
	}

	@Override
	public boolean addUser(User user){
		int count = 0;
		try (PreparedStatement pstmt = conn.prepareStatement("insert into Users values(null,?,?,0)")) {
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Username has already been taken");
		}
		return count > 0;
	}


	@Override
	public boolean updateShows(UserShow show) {
		try(PreparedStatement pstmt=conn.prepareStatement("Update users_shows set ProgressID=?,Rating=?,CurrentEp=? Where UserID=? and ShowID=?")){
		pstmt.setInt(1, show.getProgressID());
		pstmt.setInt(2,show.getRating());
		pstmt.setInt(3, show.getCurrEp());
		pstmt.setInt(4, show.getUserID());
		pstmt.setInt(5, show.getShowID());
		int count=pstmt.executeUpdate();
		if( count>0) return true;
			System.out.println(count + " show(s) updated");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	


	@Override
	public boolean addShows(UserShow show) {
		try(PreparedStatement pstmt=conn.prepareStatement("Insert into Users_Shows values (?,?,?,?,?)");){
			pstmt.setInt(1, show.getUserID());
			pstmt.setInt(2, show.getShowID());
			pstmt.setInt(3, show.getProgressID());
			pstmt.setInt(4, show.getRating());
			pstmt.setInt(5, show.getCurrEp());
			
			int count=pstmt.executeUpdate();
			if(count>0) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Could not add show");
		}
		return false;
	}

	@Override
	public boolean addFavorite(UserShow show) {
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			pstmt = conn.prepareStatement("insert into user_favorites values (?,?)");
			pstmt.setInt(1, show.getUserID());
			pstmt.setInt(2, show.getShowID());
			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Could not add to favorites");
		}

		return count > 0;
	}

	@Override
	public boolean removeFavorite(int userID, int showID) throws SQLException {
		int count = 0;
		try (PreparedStatement pstmt = conn.prepareStatement("delete from user_favorites where user_id=? and show_id=?")) {
			pstmt.setInt(1, userID);
			pstmt.setInt(2, showID);
			count = pstmt.executeUpdate();
			if(count==0){
				System.out.println("Show is not a favorite");
			}
		}catch (SQLException e){
			System.out.println("Could not remove favorite");
		}
		return count > 0;
	}

	@Override
	public boolean getFavorites(int userID)  {
		try(PreparedStatement pstmt= conn.prepareStatement("select show_id,Name from user_favorites uf join Shows s on uf.show_id=s.ShowID where uf.user_id=? ")){
	pstmt.setInt(1,userID);
	ResultSet rs=pstmt.executeQuery();
			System.out.printf("%20s %20s\n","Show ID","Name" );
			System.out.println("----------------------------------------------------------");
	while(rs.next()){
		int showID=rs.getInt("show_id");
		String name=rs.getString("name");
		System.out.printf("%20s %20s\n", showID,name);
	}
		} catch (SQLException e) {
			System.out.println("Could not get user favorites");
		}
		return false;
	}

	@Override
	public boolean getAllShows(int id) {
		
		try(PreparedStatement pstmt = conn.prepareStatement("select * from shows where ShowID not in (select ShowID from users_shows where UserID=?)")){
			pstmt.setInt(1,id);
			ResultSet rs = pstmt.executeQuery();
				System.out.printf("%10s %20s %20s %20s", "Show ID","Name", "Total Episodes","Description");
				System.out.println("\n----------------------------------------------------------------------------------------------------------------");		
				while(rs.next()) {
					
					int showId = rs.getInt("ShowID");
					String name = rs.getString("Name");
					String descript = rs.getString("Descript");
					int numEp = rs.getInt("TotalEps");
					
					System.out.printf("%10s %20s %20s %-10s%n", showId,name,numEp, descript);	
					
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		
		
		return false;
	}

	@Override
	public Optional<UserShow> getUserShow(int userID, int showID) {
		

try(PreparedStatement pstmt = conn.prepareStatement("select * from users_shows where ShowID = ? and UserID=?")) {
			
			pstmt.setInt(1, showID);
			pstmt.setInt(2, userID);
			ResultSet rs = pstmt.executeQuery();
			
			
			// rs.next() will return false if nothing found
			// if you enter the if block, that show with that id was found
			if(rs.next()) {
				int uID=rs.getInt("UserID");
				int showId = rs.getInt("ShowID");
				int progressID=rs.getInt("ProgressID");
				int rating = rs.getInt("rating");
				int currentEp = rs.getInt("CurrentEp");
				
				rs.close();
				
				// constructing the object
				UserShow userShow = new UserShow(uID,showId, progressID, rating, currentEp);
				
				// placing it in the optional
				Optional<UserShow> showFound = Optional.of(userShow);
				
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
	
	public boolean deleteUserShowById(int showID) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("Delete from users_shows where ShowID = ?" );
			pstmt.setInt(1,showID);
			pstmt.executeUpdate();
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	


	

}