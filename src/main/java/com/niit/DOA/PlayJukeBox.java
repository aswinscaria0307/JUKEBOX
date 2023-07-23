package com.niit.DOA;

import com.niit.Bean.Music;
import com.niit.DBUtil.DBConnection;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public class PlayJukeBox {
    static Clip clip;
    int choice=0;
    static int count1=0;
    static String filePath;
    static JukeBoxDOA jukeBoxDOA;

    static {
        try {
            jukeBoxDOA = new JukeBoxDOA();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("⚠️ Issue with object created ");        }
    }

    String status="play";
    Long currentFrame;
    static AudioInputStream audioInputStream;
    DBConnection db=new DBConnection();
    Connection connection= db.getConnection();

    public void PlayAudio() throws SQLException, ClassNotFoundException {
    }

    public PlayJukeBox() throws SQLException, ClassNotFoundException {
    }

    public void playAudio(String source, int count) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        count1 = count;
        filePath = source;
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(count1);
        status="play";
    }
    public void play() {
        //clip.stop();
        if (status.equals("play"))
        {
            System.out.println(">>> Song is already Playing <<<");
            return;
        }else
        clip.start();
        status = "play";
    }
    public static void playNext(String source, int count) throws UnsupportedAudioFileException, SQLException, LineUnavailableException, IOException, ClassNotFoundException {
        clip.close();
        count1= count;
        filePath = source;
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(count1);

        if (jukeBoxDOA.currentSongIndex == jukeBoxDOA.allSongsList().size() - 1) {
            jukeBoxDOA.currentSongIndex = 0;
        }
    }
    public static void playPrevious(String source, int count) throws SQLException, ClassNotFoundException, UnsupportedAudioFileException, LineUnavailableException, IOException {
       clip.close();
        count1= count;
        filePath = source;
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(count1);
    }
    public void pause()
    {
        if (status.equalsIgnoreCase("paused"))
        {
            System.out.println(">>> Song is already paused <<<");
            return;
        }
        this.currentFrame = this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }

    public void resume() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (status.equals("play")) {
            System.out.println(">>> Song is already being played <<<");
            return;
        }
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        play();
    }
    public void restart() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        status = "stop";
        play();
    }
    public void stop() {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }
    public void forward(){
        this.currentFrame = clip.getMicrosecondPosition();
        currentFrame=currentFrame+ 20000000L;
        clip.setMicrosecondPosition(currentFrame);
        play();
    }
    public void backward(){
        this.currentFrame = clip.getMicrosecondPosition();
        currentFrame=currentFrame- 20000000L;
        clip.setMicrosecondPosition(currentFrame);
        play();
    }
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
    }

}




