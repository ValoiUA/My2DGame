package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/images/sounds/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/images/sounds/coin.wav");
        soundURL[2] = getClass().getResource("/images/sounds/powerup.wav");
        soundURL[3] = getClass().getResource("/images/sounds/unlock.wav");
        soundURL[4] = getClass().getResource("/images/sounds/fanfare.wav");
    }
    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }catch (Exception e) {
        }

    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);

    }
    public void stop(){
        clip.stop();
    }
}
