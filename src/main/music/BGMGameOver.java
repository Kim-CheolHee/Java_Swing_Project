package main.music;

import java.io.File;
import javax.sound.sampled.*;

public class BGMGameOver {
    public BGMGameOver(){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("sound/GameOver.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            
            // 소리 설정
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            
            // 볼륨 조정
            gainControl.setValue(-30.0f);
            
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
