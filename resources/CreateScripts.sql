-- create database progresstracker;
use progresstracker;
create table Users(
UserID int primary key,
UserName varchar(255),
Password varchar(255),
RoleType int
);

create table Shows(
ShowID int primary key,
Name varchar(255),
Descript text,
TotalEps int);

create table Users_Shows(
UserID int, 
ShowID int,
Rating int,
CurrentEp int,
primary key (UserID,ShowID),
foreign key (UserID) references Users(UserID),
foreign key (ShowID) references Shows(ShowID)
);