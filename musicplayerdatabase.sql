create database musicplayer;
use musicplayer;
create table songs (songid int primary key not null AUTO_INCREMENT,songname varchar(50),sourcepath varchar(150),artist varchar(15),genres varchar(30),duration time);
create table playlist (playlistid int primary key not null AUTO_INCREMENT,playlistname varchar(50));
create table playlistsongs (playlistid int, foreign key(playlistid) references playlist(playlistid) on update cascade on delete cascade,songid int,foreign key(songid)references songs(songid) on update cascade on delete cascade);


insert into songs (songname,sourcepath ,artist,genres,duration) values("Black-Beatles","src/main/resources/Black-Beatles.wav","Ajay","Pop","00:03:50");
insert into songs (songname,sourcepath ,artist,genres,duration) values("Enna-Sona","src/main/resources/Enna-Sona.wav","Sharon","Melody","00:03:40");
insert into songs (songname,sourcepath ,artist,genres,duration) values("Feat-Paris","src/main/resources/Feat-Paris.wav","Ajay","Rock","00:04:40");
insert into songs (songname,sourcepath ,artist,genres,duration) values("Sing-Me-To-Sleep","src/main/resources/Sing-Me-To-Sleep.wav","Kiran","Pop","00:04:10");
insert into songs (songname,sourcepath ,artist,genres,duration) values("TouchIt","src/main/resources/TouchIt.wav","Arun","Rock","00:04:05");


