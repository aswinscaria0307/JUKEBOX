package com.niit.Main;

import com.niit.Bean.Music;
import com.niit.Bean.Playlist;
import com.niit.Bean.Search;
import com.niit.DBUtil.DBConnection;
import com.niit.DOA.JukeBoxDOA;
import com.niit.DOA.PlayJukeBox;
//import com.niit.DOA.PlayJukeBox;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JukeBoxMain {
    static Scanner scanner = new Scanner(System.in);
    static DBConnection db=new DBConnection();
    static Connection connection;
    static List<Playlist> playlists=new ArrayList<>() ;
//    static List<Playlist> playlist1=new ArrayList<>();
//    static List<Music> playlistsongs=new ArrayList<>();
    static String qry ="SELECT * FROM songs";

    static {
        try {
            connection = db.getConnection();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public JukeBoxMain() throws SQLException, ClassNotFoundException {
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        int choice = 0;
        Search search;
//        Playlist playlist=new Playlist();
        JukeBoxDOA jukeBoxDOA=new JukeBoxDOA();
        List<Music> songList=new ArrayList<>();
//        List<Music> songList1=new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(qry);
        while (rs.next()) {
                Music music = new Music();
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String artist = rs.getString(4);
                String genre = rs.getString(5);
                Time duration = rs.getTime(6);
                music.setSongId(id);
                music.setSongName(name);
                music.setSongSource(rs.getString(3));
                music.setArtistName(artist);
                music.setGenreName(genre);
                music.setSongDuration(duration);
                songList.add(music);
        }
                        do {
                System.out.println("\033[0;30m");
                System.out.println("+---------------------------------------------------------------+");
                System.out.println("|                   \uD83C\uDFB6 Welcome To  Juke Box \uD83C\uDFB6                 |");
                System.out.println("+---------------------------------------------------------------+");
                System.out.println();
                //System.out.println("-----------------------------");
                System.out.println(">>  1.Play song \uD83C\uDFA7\n" + ">>  2.Search Song \uD83D\uDD0D\n" + ">>  3.Select from Category \uD83D\uDC48 \n" + ">>  4.Playlist \uD83D\uDCC3\n" + ">>  5.Exit ❌");
                //System.out.println("-----------------------------");//
                System.out.println("");
                try {
                    choice = scanner.nextInt();
                }catch (Exception e){
                    System.out.println(" ??? Wrong Entry...Exiting...");
                    break;
                }
                switch (choice) {
                    case 1:
                        jukeBoxDOA.playSong(songList);
                        jukeBoxDOA=new JukeBoxDOA(3);
                        break;
                    case 2:
                        System.out.println("\n");
                        System.out.println(">>> 🔎 Enter you [ Search Element ] or [ exit ] to go to Main Menu <<<");
                        scanner.nextLine();
                        search = new Search();
                        String searchElement=scanner.nextLine();
                        if(!searchElement.equalsIgnoreCase("exit")){
                            search.setSearchElement(searchElement);
                            List<Music> newList=jukeBoxDOA.searchSongs(searchElement);
                            try {
                                JukeBoxDOA jukeBoxDOA1=new JukeBoxDOA();
                                jukeBoxDOA1.playSong(newList);
                                System.out.println(">>> If you Need to Search Again, Please Start Again <<<");
                            }catch (Exception exception){
                                System.out.println(">>> ⚠️ Something Went Wrong <<<");break;
                            }
                        }else {
                            break;
                        }
                        break;
                    case 3:
                        System.out.println();
                        System.out.println(">>> Enter [ 1. pop ] or [ 2. Rock ] or [ 3. Melody ] [ 4 ] to go to Main Menu <<<");
                        int searchInGenre=scanner.nextInt();
                        if(!(searchInGenre ==4)){
                            List<Music> sortedList=jukeBoxDOA.searchSong(songList,searchInGenre );
                            JukeBoxDOA jukeBoxDOA2=new JukeBoxDOA();
                            jukeBoxDOA2.playSong(sortedList);
                        }else {
                            break;
                        }
                        break;
                    case 4:
                        int flag=0;
                        System.out.println(">>> Press [ 1 to view Playlist ] [ 2 to Create playlist ] [ 3 to Add songs to playlist] <<<");
                        int choose=scanner.nextInt();
                        if(choose==1){
                            JukeBoxDOA jukeBoxDOA3=new JukeBoxDOA();
                            playlists =jukeBoxDOA3.playList();//return playlist list of type playlist contain all playlist id and playlist name
                            while (playlists != null) {
                                jukeBoxDOA3.viewPlaylist(playlists, songList);//display all the playlist and also return the playlist

                                System.out.println(">>> Select [ playlist Id ] or [ 0 to Exit ] <<<");
                                int i = scanner.nextInt();
                                if (i != 0) {
                                    if (!(i>playlists.size())) {
                                    List<Integer> id = jukeBoxDOA.getSongId(playlists, i);//returns song id list
                                    List<Music> lt = jukeBoxDOA.getAllSongsBySongId(id);//taking all music list
                                    jukeBoxDOA3 = new JukeBoxDOA();
                                    try {
                                        jukeBoxDOA3.playSong(lt);

                                    } catch (Exception e) {
                                        System.out.println(">>> 🏁No More Songs <<<");
                                        PlayJukeBox playJukeBox1 = new PlayJukeBox();
                                        playJukeBox1.stop();
                                        break;
                                    }
                                }else {
                                        System.out.println(">>> No Playlist Found <<<");
                                    }}
                                break;
                            }

                        } if(choose==2){
                        JukeBoxDOA jukeBoxDOA4=new JukeBoxDOA();
                        jukeBoxDOA4.createPlayList(songList);
                    }
                        if (choose==3) {
                            JukeBoxDOA jukeBoxDOA5=new JukeBoxDOA();
                            jukeBoxDOA5.addInToPlaylist();

                        }
                        if (choose>=4){
                            System.out.println(">>> Wrong Entry Come Again <<<");
                           // break;
                        }break;

                    case 5:
                        break;
                    default:

                        System.out.println(">>> ❌ Enter Correct Option <<<");
                }
            }
            while (choice != 5);
        }
        }


