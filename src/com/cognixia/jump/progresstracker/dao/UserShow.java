package com.cognixia.jump.progresstracker.dao;

public class UserShow {
	private int userID;
	private int showID;
	private int rating;
	private int currEp;
	private int progressID;

	public UserShow(int userID, int showID, int progressID,int rating, int currEp) {
		super();
		this.userID = userID;
		this.showID = showID;
		this.rating = rating;
		this.currEp = currEp;
		this.progressID=progressID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getShowID() {
		return showID;
	}

	public void setShowID(int showID) {
		this.showID = showID;
	}

	public int getRating() {
		return rating;
	}
	

	public int getProgressID() {
		return progressID;
	}

	public void setProgressID(int progressID) {
		this.progressID = progressID;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getCurrEp() {
		return currEp;
	}

	public void setCurrEp(int currEp) {
		this.currEp = currEp;
	}

	@Override
	public String toString() {
		return "UserShow [userID=" + userID + ", showID=" + showID + ", rating=" + rating + ", currEp=" + currEp
				+ ", progressID=" + progressID + "]";
	}
	
	

}
