package com.cognixia.jump.progresstracker.dao;

public class Show {
private int id;
private String showName;
private String desc;
private int NumEp;
public Show(int id, String showName, String desc, int numEp) {
	super();
	this.id = id;
	this.showName = showName;
	this.desc = desc;
	this.NumEp = numEp;
	
}

public Show(String showName, String desc, int numEp) {
	this.showName = showName;
	this.desc = desc;
	this.NumEp = numEp;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getShowName() {
	return showName;
}
public void setShowName(String showName) {
	this.showName = showName;
}
public String getDesc() {
	return desc;
}
public void setDesc(String desc) {
	this.desc = desc;
}
public int getNumEp() {
	return NumEp;
}
public void setNumEp(int numEp) {
	NumEp = numEp;
}
@Override
public String toString() {
	return "Shows [id=" + id + ", showName=" + showName + ", desc=" + desc + ", NumEp=" + NumEp + "]";
}


}
