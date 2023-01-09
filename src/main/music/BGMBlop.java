package main.music;

import java.io.File;

import javax.sound.sampled.*;

import main.FrameMain;

public class BGMBlop {
	public void BlopBGM(String bgmName){
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("sound/Blop.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);

			// �Ҹ� ����
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

			// ���� ����
			gainControl.setValue(-10.0f);

			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
