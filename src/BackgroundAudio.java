import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class BackgroundAudio implements Runnable{
    //variables
    private Thread t;
    private static BackgroundAudio instance;
    private boolean locChanged;
    private Object lockLoc;
    private String fileName;
    private String filePath;
    private Clip clip;
    private Clip prevClip;
    private AudioInputStream prevStream;
    
    //constructor
    private BackgroundAudio(){
        locChanged = false;
        filePath = "file:./src/sounds/";
        lockLoc = new Object();
        t = new Thread(this);
        t.start();
    }

    //gets instance (for singleton)
    public static synchronized BackgroundAudio getInstance(){
        if(instance == null) instance = new BackgroundAudio();
        return instance;
    }

    private Clip playBGSound(String file){
        try {
            if (prevClip != null) prevClip.close();
            if(prevStream != null) prevStream.close();
            URL url = new URL(filePath + fileName);
            clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            clip.open(ais);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            prevStream = ais;
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        prevStream = null;
        return null;
    }

    public void newBGSound(String loc) {
        switch (loc.toLowerCase()){
            case "house":
                fileName = "houseBG.wav";
                break;
            case "kitchen":
                fileName = "kitchenBG.wav";
                break;   
            case "road":
                fileName = "roadBG.wav";
                break;
            case "supermarket":
                fileName = "supermarketBG.wav";
                break;
            case "sewers":
                fileName = "sewersBG.wav";
                break;
            case "wilderness":
                fileName = "wildernessBG.wav";
                break;
            case "warehouse":
                fileName = "warehouseBG.wav";
                break;
            case "farm":
                fileName = "farmBG.wav";
                break;
            case "forest":
                fileName = "forestBG.wav";
                break;
            case "cave":
                if (Cave.getInstance().blocked) fileName = "fireBG.wav";
                else fileName = "caveBG.wav";
                break;
            case "rat castle":
                fileName = "ratCastleBG.wav";
                break;
            case "boss":
                fileName = "bossBG.wav";
                break;
            default:
                fileName = "silent.wav";
        }
        synchronized (lockLoc) { locChanged = true; }
    }

    private boolean check() {
        synchronized (lockLoc) {
            if (!locChanged) return true;
            else return false;
        }
    }

    @Override
    public void run() {
        while(check());
        synchronized (lockLoc) { locChanged = false; }
        while (true){
            prevClip = playBGSound(fileName);
            while(check());
            synchronized (lockLoc) { locChanged = false; }
            prevClip.stop();
        }
    }
}
