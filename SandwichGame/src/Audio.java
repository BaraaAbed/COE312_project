import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio implements Runnable {

    private Clip clip;
    private AudioInputStream audioInputStream;
    public String filePath = "file:C:/Users/user/Music/COE312/";
    private boolean isPlaybackCompleted;
    private Thread t;
    private String soundFile;
    private boolean play;
    private Object lockPlay;

    public Audio() {
        t = new Thread(this);
        soundFile = "bla";
        play = false;
        lockPlay = new Object();
        t.start();
    }

    public void playSound(String type) {
        switch (type.toLowerCase()){
            case "gameBegin":
            break;
            case "start":
            break;
            case "whaddup":
            break;
            case "upgrade":
            break;
            case "evilLaugh":
            break;
            case "gameLost":
            break;
            case "no":
            break;
            case "gameWon":
            break;
            case "fire":
            break;
            case "sauce": 
            soundFile = "lamb_sauce.wav";
            break;
            case "idiot":
            playSound("idiot_sandwich.wav");
            break;
            case "gnomeLaugh":
            playSound("gnome_laugh.wav");
            break;
            case "gnomeKill":
            playSound("gnome_kill.wav");
            break;
            case "pathSuccess":
            //playSound("");
            //Thread.sleep();
            break;
            case "imposterKill":
            playSound("imposter_stab.wav");
            break;
            case "susSound":
            playSound("sus_sound.wav");
            break;
            case "equip":
            playSound("equip.wav");
            break;
            case "unequip":
            playSound("unequip.wav");
            break;
            case "attack":
            break;
            case "dodge":
            break;
            case "takeDmg":
            break;
            case "death":
            break;
            case "respawn":
            break;
            case "legendaryPickup":
            break;
            case "coins":
            break;
            case "enemyDeath":
            break;
            case "hint":
            break;
            case "eaten":
            break;
            case "pickup":
            break;
            case "place":
            break;
            case "failedIngredientPlacement":
            break;
            case "sabotageSuccess":
            break;
            case "sabotageFail":
            break;
            default:
            System.out.println("Error finding audio, check playSound");
        }
        synchronized (lockPlay) { play = true; }
    }

    public void audioPlayer(String fileName) {
        try {
            URL url = new URL(filePath + fileName);
            clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            clip.open(ais);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private boolean check() {
        synchronized (lockPlay) {
            if (!play) return true;
            else return false;
        }
    }

    @Override
    public void run() {
        while(true) {
            while(check());
            audioPlayer(soundFile);
            synchronized (lockPlay) { play = false; }
        }
    }

}