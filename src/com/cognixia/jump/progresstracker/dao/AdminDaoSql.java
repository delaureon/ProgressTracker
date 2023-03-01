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

public class AdminDaoSql  implements AdminDao {
	
	private Connection conn;
	@Override
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		conn=BetterConnectionManager.getConnection();
	}

	@Override
	public List<Show> getAllShows() {

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
	public boolean createShow(Show show) {
		try(PreparedStatement pstmt=conn.prepareStatement("Insert into Shows values null,?,?,?")){
		pstmt.setString(2,show.getShowName());
		pstmt.setString(3,show.getDesc());
		pstmt.setInt(4,show.getNumEp());
		int count=pstmt.executeUpdate();
		if(count>0) {
			return true;
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteShow(int id) {
		try (PreparedStatement pstmt=conn.prepareStatement("Delete from Shows where ShowID=?")){
			pstmt.setInt(1, id);
			int count=pstmt.executeUpdate();
			if(count>0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateShow(Show show) {
		try (PreparedStatement pstmt=conn.prepareStatement("Update Shows where ShowID=? set ShowName=? set Desc=? set NumEp=?")){
			pstmt.setInt(1, show.getId());
			pstmt.setString(2, show.getShowName());
			pstmt.setString(3,show.getDesc());
			pstmt.setInt(4, show.getNumEp());
			int count=pstmt.executeUpdate();
			if(count>0) {
				return true;
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Optional<Show> getShowById(int id) {
		try(PreparedStatement pstmt=conn.prepareStatement("Select * from Shows where ShowID=?")) {
			pstmt.setInt(1, id);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				int showID=rs.getInt("ShowId");
				String name=rs.getString("ShowName");
				String desc=rs.getString("Descript");
				int numEps=rs.getInt("TotalEps");
				rs.close();
				Show show1= new Show(showID,name,desc,numEps);
				Optional<Show> showFound=Optional.of(show1);
				return showFound;
			}
			else return Optional.empty();
					}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Optional.empty();
	}

}
