package main.music;

import java.io.File;
import javax.sound.sampled.*;

public class BGMGameOver {
    public BGMGameOver(){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("sound/GameOver.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            
            // �Ҹ� ����
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            
            // ���� ����
            gainControl.setValue(-30.0f);
            
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
