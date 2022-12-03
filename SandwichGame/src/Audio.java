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

public class Audio {

    private Clip clip;
    private AudioInputStream audioInputStream;
    public String filePath = "file:C:/Users/user/Music/COE312/";
    private boolean isPlaybackCompleted;

    public Audio() {

    }

    public void playSound(String type) {
        switch(type.toLowerCase()){
            case "sauce":
            try {
                URL url = new URL(filePath + "lamb_sauce.wav");
                clip = AudioSystem.getClip();
                AudioInputStream ais = AudioSystem.getAudioInputStream(url);
                clip.open(ais);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
            
        }
    }

}