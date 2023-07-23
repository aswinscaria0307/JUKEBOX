package com.niit.DOA;

import com.niit.Bean.Music;
import com.niit.Bean.Playlist;
import com.niit.Bean.Search;
import com.niit.DBUtil.DBConnection;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

    public class JukeBoxDOA {
        Search search=new Search();
        Scanner scanner = new Scanner(System.in);
        PlayJukeBox playJukeBox = new PlayJukeBox();
        DBConnection db = new DBConnection();
        Connection connection;
        Connection connection1;
        Playlist playlist=new Playlist();
        Music music=new Music();
        String musicPath;
        private int choice;
        int currentSongIndex;

    public JukeBoxDOA() throws SQLException, ClassNotFoundException {
    }
        public JukeBoxDOA(int choice) throws SQLException, ClassNotFoundException {
        this.choice=choice;
        }

    public List<Music> playSong(List<Music> list) throws UnsupportedAudioFileException, LineUnavailableException, IOException, SQLException, ClassNotFoundException {
        int songId = 0;
        if (!list.isEmpty()) {
            System.out.println("______________________________________________________________________");
            System.out.format("%-9s\t%-20s\t%-9s\t%-9s\t%-9s\n", "| Song id", "Song Name", "Artist", "Genre", "Duration |");
            System.out.println("______________________________________________________________________");
            System.out.println("----------------------------------------------------------------------");
            for (Music music : list) {
                System.out.format("| %-9d\t%-20s\t%-9s\t%-9s\t%tT %s\n", music.getSongId(), music.getSongName(), music.getArtistName(), music.getGenreName(), music.getSongDuration(),"|");
                System.out.println("----------------------------------------------------------------------");
            }
            System.out.println("");
            System.out.println(">> Provide a [ Song Id ] or [ 0 ] to Exit >>");
                 songId = scanner.nextInt();

                if (songId != 0) {
                    for (Music music : list) {
                        if (music.getSongId() == songId) {
                            musicPath = music.getSongSource();
                        }
                    }
                    if (musicPath!=null) {
                        playJukeBox.playAudio(musicPath, 1);

                    while (choice != 11) {
                        System.out.println("        1. ▶️Play         ");
                        System.out.println("        2. ⏸️pause        ");
                        System.out.println("        3. 🔀Shuffle      ");
                        System.out.println("        4. ⏭️playNext     ");
                        System.out.println("        5. ⏮️playPrevious ");
                        System.out.println("        6. ▶️resume       ");
                        System.out.println("        7. 🔄️restart      ");
                        System.out.println("        8. ⏩Forward      ");
                        System.out.println("        9. ⏪Backward     ");
                        System.out.println("       10. 🔀shuffleOff   ");
                        System.out.println("       11. ⏹️Stop         ");
                        scanner.nextLine();
                        choice = scanner.nextInt();
                        switch (choice) {
                            case 1 -> playJukeBox.play();
                            case 2 -> playJukeBox.pause();
                            case 3 -> {
                                System.out.println(">>> Shuffle ON <<<");
                                Collections.shuffle(list);
                            }
                            case 4 -> {
                                PlayJukeBox.playNext(musicPath, currentSongIndex);
                                currentSongIndex = currentSongIndex + 1;
                                Music currentMusic = list.get(currentSongIndex);
                                String path = currentMusic.getSongSource();
                                PlayJukeBox.playNext(path, 1);
                                if (currentSongIndex == list.size() - 1) {
                                    currentSongIndex = 0;
                                }
                            }
                            case 5 -> {
                                currentSongIndex = currentSongIndex - 1;
                                if (currentSongIndex < 0) {
                                    currentSongIndex = list.size() - 1;
                                }
                                Music currentMusic = list.get(currentSongIndex);
                                String path = currentMusic.getSongSource();
                                PlayJukeBox.playPrevious(path, -1);
                            }
                            case 6 -> playJukeBox.resume();
                            case 7 -> playJukeBox.restart();
                            case 8 -> playJukeBox.forward();
                            case 9 -> playJukeBox.backward();
                            case 10 -> {
                                System.out.println(">>> Shuffle OFF <<<");
                                Collections.sort(list, Comparator.comparingInt(Music::getSongId));
                            }
                            case 11 -> {
                                musicPath=null;
                                PlayJukeBox.clip.close();

                                //playJukeBox.stop();
                            }

                            default -> System.out.println(">>> ❌ Wrong entry try again <<<");
                        }
                    }
                } else {
                        System.out.println("No Song found for given Id");
                    }
            } else {
                    System.out.println(">>> Exiting <<<");
            }
            return list;
        }
            return list;
    }

    public List<Music> searchSong(List<Music> list, int searchElement){
            List<Music> sorted = new ArrayList<>();


            switch (searchElement) {
                case 1:
                    for (Music music : list) {
                        if (music.getGenreName().equalsIgnoreCase("pop")) {
                            sorted.add(music);
                        }
                    }
                    break;
                case 2:
                    for (Music music : list) {
                        if (music.getGenreName().equalsIgnoreCase("rock")) {
                            sorted.add(music);
                        }
                    }
                    break;
                case 3:
                    for (Music music : list) {
                        if (music.getGenreName().equalsIgnoreCase("melody")) {
                            sorted.add(music);
                        }
                    }
                    break;
            }
            Collections.sort(sorted, Comparator.comparing(Music::getSongName));

        return sorted;
    }

    public List<Music> searchSongs(String element) throws SQLException, ClassNotFoundException {
            List<Music> searchResult = new ArrayList<>();
            String qry = "SELECT * FROM musicplayer.songs WHERE songname='"+element+"' OR artist='" + element+ "'OR genres='" + element + "';";
        connection = db.getConnection();
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
                    searchResult.add(music);
                }if (searchResult.isEmpty()) {
            System.out.println(">>> ⛔ No Results Found Try Again <<<");
        }
            Collections.sort(searchResult, Comparator.comparing(Music::getSongName));


        return searchResult;
    }

    public  List<Music> allSongsList() throws SQLException, ClassNotFoundException {
        List<Music> allSongList = new ArrayList<>();
        String qry = "SELECT * FROM musicplayer.songs";
        connection = db.getConnection();
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
            allSongList.add(music);
    }return allSongList;
    }
    public List<Music> getAllSongsBySongId(List<Integer> list) throws SQLException, ClassNotFoundException {
        Connection connection = db.getConnection();
        Statement statement = connection.createStatement();
        List<Music> newList = new ArrayList<>();
        if (list.isEmpty()){
            System.out.println(">>> ⛔ No Songs Available In Playlist ,Add Songs [ 4->3 ] <<<");
        }else {


            for (int i = 0; i < list.size(); i++) {
                String qry = "Select * from songs where songid=" + list.get(i) + ";";
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
                    newList.add(music);
                }
            }
        }
        return newList;
    }


    public List<Playlist> playList () throws SQLException, ClassNotFoundException {
        List<Playlist> playlists=new ArrayList<>();
            String qry = "SELECT * FROM musicplayer.playlist;";
            connection = db.getConnection();
             //connection= db.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(qry);
            while (rs.next()) {
                Playlist playlist = new Playlist();
                int id = rs.getInt(1);
                String name = rs.getString(2);
                playlist.setPlaylistid(id);
                playlist.setPlaylistname(name);
                playlists.add(playlist);
                 }
            if (playlists.isEmpty()) {
                System.out.println(">>> ⛔ NO Playlist Found <<<");
            }
            return playlists;
        }


        public List<Integer> getSongId(List<Playlist> list,int id) throws SQLException, ClassNotFoundException {
            List<Integer> songIds = new ArrayList<>();
                String qry = "SELECT songid FROM musicplayer.playlistsongs where playlistid=" + id + " ;";
                Connection connection = db.getConnection();
                Statement statement1 = connection.createStatement();
                ResultSet rs1 = statement1.executeQuery(qry);
                while (rs1.next()) {
                    int id1 = rs1.getInt(1);
                    songIds.add(id1);
                }
                if(songIds.isEmpty()){
                    System.out.println("No Songs Found");
                }
                       return songIds;
        }

        public void createPlayList (List<Music> list) throws SQLException, ClassNotFoundException {
            System.out.println(">>> Provide a Name For Your New PlayList <<<");
            String playlistName=scanner.nextLine();
            String qry = "INSERT INTO playlist (playlistname) VALUES ('"+playlistName +"');";
            connection = db.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(qry);
            System.out.println(">>> ✅ Playlist [ "+playlistName+ " ] Created Successfully, Now Songs By Selecting [ 4->3 ] <<<");
            }

        public void addInToPlaylist() throws SQLException, ClassNotFoundException {
            //List <Playlist> list=playList();
            List <Playlist> list=playList();
                System.out.format("%-9s\t%-20s\n", "PlayList id", "PlayList Name");
            for (Playlist play : list) {
                System.out.format("%-9s\t%-20s\n", play.getPlaylistid(), play.getPlaylistname());
            }
            System.out.println(">>> Select [ playlist Id ] to add Songs or [ 0 ] to Exit <<<");
            int id=scanner.nextInt();
            playlist.setPlaylistid(id);
            do{
                if(id!=0){
                List<Music> songsList=allSongsList();
                System.out.format("%-9s\t%-20s\t%-9s\t%-9s\t%-9s\n", "Song id", "Song Name", "Artist", "Genre", "Duration");
                for (Music music:songsList) {
                        System.out.format("%-9d\t%-20s\t%-9s\t%-9s\t%tT\n", music.getSongId(), music.getSongName(), music.getArtistName(), music.getGenreName(), music.getSongDuration());
                                    }
                System.out.println(">>> Enter [ Song Id ] to Add Song to Playlist [ 0 ] to Exit");
                try {
                    int id1=scanner.nextInt();
                music.setSongId(id1);
                if (id1!=0) {
                    Connection connection = db.getConnection();
                    String qry = "INSERT INTO playlistsongs VALUES (?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(qry);
                    preparedStatement.setInt(1, playlist.getPlaylistid());
                    preparedStatement.setInt(2, music.getSongId());
                    preparedStatement.executeUpdate();
                    System.out.println(">>> Song Added to Playlist Successfully <<<\n");

                }else {break;
                }   }catch (Exception e){
                    System.out.println(">>> Wrong Entry <<<\n");
                }
                }else {
                    break;
                }
            }while (true);
}

    public List<Music> viewPlaylist (List<Playlist> list,List<Music> fullist){
        List<Integer> songIds = new ArrayList<>();
        List<Music> musics = new ArrayList<>();
        String qry = new String();
        System.out.format("%-9s\t%-20s\n", "PlayList id", "PlayList Name");
        for (Playlist playlist : list) {
            System.out.format("%-9s\t%-20s\n", playlist.getPlaylistid(), playlist.getPlaylistname());
             qry = "SELECT songid FROM playlistsongs WHERE playlistid = '" + playlist.getPlaylistid() + "'";
        }
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(qry))
            {
                while (resultSet.next()) {
                    songIds.add(resultSet.getInt("songid"));
                   // System.out.println(resultSet.getInt("songid"));

                }
                for (Integer id : songIds) {
                    Music song = findSongById(id,fullist);

                    if (song != null) {
                        musics.add(song);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Something Wrong!!!");
            }

        return musics;
    }
    public Music findSongById(int id,List<Music> list) {
        //Music newList=new Music();
        for (Music song : list) {
            if (song.getSongId()==id) {

return song;
            }
        }
        return  null;
    }

}
