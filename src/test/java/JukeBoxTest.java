import static org.junit.Assert.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.niit.Bean.Music;
import com.niit.DOA.JukeBoxDOA;
import org.junit.Before;
import org.junit.Test;

public class JukeBoxTest {

    JukeBoxDOA jukeBoxDOA;
    List<Music> list;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        jukeBoxDOA = new JukeBoxDOA();
        list = new ArrayList<>();
        Music music1 = new Music(1, "Song1", "ajay", "path/to/song1" , "pop", "01:00:00");
        Music music2 = new Music(2, "Song2", "anil", "path/to/song2" , "rock", "02:00:00");
        Music music3 = new Music(1, "Song3", "kiran", "path/to/song3" , "pop", "01:00:00");
        Music music4 = new Music(2, "Song4", "alex", "path/to/song4" , "melody", "02:00:00");
        list.add(music1);
        list.add(music2);
        list.add(music3);
        list.add(music4);
    }

    @Test
    public void testSearchSong() throws SQLException, ClassNotFoundException {
        List<Music> sample = jukeBoxDOA.allSongsList();
        Music music1 = new Music(1, "Black-Beatles","src/main/resources/Black-Beatles.wav","Ajay","Pop","00:03:50");
        Music music3 = new Music(4, "Sing-Me-To-Sleep","src/main/resources/Sing-Me-To-Sleep.wav","Kiran","Pop","00:04:10");
        List<Music> result=new ArrayList<>();
                result.add(music1);
                result.add(music3);
        assertEquals(result.size(), jukeBoxDOA.searchSong(sample,1).size());
    }

        @Test
        public void getAllSongDetailsTest() throws SQLException, ClassNotFoundException {
            assertEquals(5,jukeBoxDOA.allSongsList().size());
        }


    }
