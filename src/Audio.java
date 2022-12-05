import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio implements Runnable {

    private static Audio instance;
    private Clip clip;
    public String filePath = "file:./src/sounds/";
    private Thread t;
    private String soundFile;
    private boolean play;
    private Object lockPlay;
    private Clip prevClip;
    private AudioInputStream prevStream;

    private Audio() {
        t = new Thread(this);
        soundFile = "game_begin.wav";
        play = false;
        lockPlay = new Object();
        t.start();
    }

    //gets instance (for singleton)
    public static synchronized Audio getInstance(){
        if(instance == null) instance = new Audio();
        return instance;
    }

    public void playSound(String type) {
        switch (type.toLowerCase()){
            case "gamebegin":
                soundFile = "game_begin.wav";
                break;
            case "start":
                soundFile = "start.wav";
                break;
            case "bob":
                soundFile = "bob.wav";
                break;
            case "upgrade":
                soundFile = "upgrade.wav";
                break;
            case "evillaugh":
                soundFile = "evil_laugh.wav";
                break;
            case "gamelost":
                soundFile = "game_lost.wav";
                break;
            case "no":
                soundFile = "no.wav";
                break;
            case "gamewon":
                soundFile = "win.wav";
                break;
            case "sauce": 
                soundFile = "lamb_sauce.wav";
                break;
            case "idiot":
                soundFile = "idiot_sandwich.wav";
                break;
            case "gnomelaugh":
                soundFile = "gnome_laugh.wav";
                break;
            case "gnomekill":
                soundFile = "gnome_kill.wav";
                break;
            case "pathsuccess":
                soundFile = "path_success.wav";
                break;
            case "imposterkill":
                soundFile = "imposter_stab.wav";
                break;
            case "sussound":
                soundFile = "sus_sound.wav";
                break;
            case "equip":
                soundFile = "equip.wav";
                break;
            case "unequip":
                soundFile = "unequip.wav";
                break;
            case "attack":
                soundFile = "attack.wav";
                break;
            case "dodge":
                soundFile = "dodge.wav";
                break;
            case "takedmg":
                soundFile = "take_damage.wav";
                break;
            case "death":
                soundFile = "death.wav";
                break;
            case "respawn":
                soundFile = "respawn.wav";
                break;
            case "legendarypickup":
                soundFile = "legendary_pickup.wav";
                break;
            case "enemydeath":
                soundFile = "enemy_death.wav";
                break;
            case "eaten":
                soundFile = "eaten.wav";
                break;
            case "pickup":
                soundFile = "pickup.wav";
                break;
            case "place":
                soundFile = "place.wav";
                break;
            case "failedingredientplacement":
                soundFile = "teleport.wav";
                break;
            case "sabotagesuccess":
                soundFile = "sabotage_success.wav";
                break;
            case "sabotagefail":
                soundFile = "sabotage_fail.wav";
                break;
            default:
                soundFile = "silent.wav";
        }
        synchronized (lockPlay) { play = true; }
    }

    private Clip audioPlayer(String fileName) {
        try {
            if (prevClip != null) prevClip.close();
            if (prevStream != null) prevStream.close();
            URL url = new URL(filePath + fileName);
            clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            clip.open(ais);
            clip.start();
            prevStream = ais;
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        prevStream = null;
        return null;
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
            prevClip = audioPlayer(soundFile);
            synchronized (lockPlay) { play = false; }
        }
    }

}