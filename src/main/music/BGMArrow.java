package main.music;

import java.io.File;

import javax.sound.sampled.*;

import main.FrameMain;

public class BGMArrow {
	public void ArrowBGM(String bgmName){
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("sound/Arrow.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);

			// 소리 설정
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

			// 볼륨 조정
			gainControl.setValue(-10.0f);

			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
