create database progresstracker;
use progresstracker;
create table Users(
UserID int auto_increment primary key ,
UserName varchar(255),
Password varchar(255),
RoleType int
);

create table Shows(
ShowID int auto_increment primary key ,
Name varchar(255),
Descript text,
TotalEps int);

create table progress(
progressID int primary key auto_increment,
progress varchar(255)
);

insert into progress values(null, "Not Started");
insert into progress values(null, "In Progress");
insert into progress values(null, "Complete");



create table Users_Shows(
UserID int, 
ShowID int,
ProgressID int,
Rating int,
CurrentEp int,
primary key (UserID,ShowID),
foreign key (UserID) references Users(UserID),
foreign key (ShowID) references Shows(ShowID),
foreign key (ProgressID) references progress(ProgressID)
);
insert into Users(UserName, Password, RoleType)
	values("arenlaure", "aaa123", 1);
insert into Users(UserName, Password, RoleType)
	values("albertpaez", "bbb456", 0);
insert into Users(UserName, Password, RoleType)
	values("alexisenri", "ccc789", 0);
    insert into Shows(Name, Descript, TotalEps)
	values("Walking Dead", "Show about zombies.", 62);
insert into Shows(Name, Descript, TotalEps)
	values("Grey's Anatomy", "Show about dramatic doctors.", 205);
insert into Shows(Name, Descript, TotalEps)
	values("Spongebob", "Show about a sponge.", 111);
    
insert into Users_Shows(UserID, ProgressID,ShowID, Rating, CurrentEp)
	values(1, 2,3, 5, 1);
insert into Users_Shows(UserID, ProgressID,ShowID, Rating, CurrentEp)
	values(1,2, 2, 5, 30);
    
-- Albert watches Walking Dead, Spongebob
insert into Users_Shows(UserID, ProgressID, ShowID, Rating, CurrentEp)
	values(2,2, 1, 2, 30);
insert into Users_Shows(UserID,ProgressID, ShowID, Rating, CurrentEp)
	values(2,2, 3, 2, 60);

-- Alexis watches Grey's Anatomy, Walking Dead
insert into Users_Shows(UserID, ProgressID, ShowID, Rating, CurrentEp)
	values(3, 2,2, 3, 100);
insert into Users_Shows(UserID, ProgressID,ShowID, Rating, CurrentEp)
	values(3,2, 1, 3, 100);
    
select * from users_shows;

select Users_Shows.ShowID, progress.progress,Users.Userid,Name, CurrentEp,Rating , totalEps, ((CurrentEp /totalEps)*100) as percentcomplete
from users
join Users_Shows on users.UserID=Users_Shows.UserID
join progress on Users_Shows.ProgressID=progress.ProgressID
join Shows on Users_Shows.ShowID=Shows.ShowID
where Users.userid=2;

Update users_shows set ProgressID=1,Rating=3,CurrentEp=1 Where UserID=2 and ShowID = 5;

select * from users_shows where ShowID = 5 and UserID=2;

select * from users;
select * from shows;
select * from users_shows;
select * from progress;