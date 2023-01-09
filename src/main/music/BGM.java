package main.music;

import java.io.*;
import javax.sound.sampled.*;

public class BGM {
	public String bgmFolder = "sound"; // ���� ��� ����
	public Clip clip;
	public boolean isPlayed = true;

	public void stopBGM(){
		if(clip != null){
			clip.stop();
			System.out.println("������ ���߾����ϴ�.");
		}
	}

	public void playBGM(String bgmName){
		if(!isPlayed){
			return;
		}

		stopBGM();
		try{
			File bgmPath = new File(bgmFolder + '/' + bgmName);
			if(bgmPath.exists()){
				AudioInputStream ais = AudioSystem.getAudioInputStream(bgmPath);
				clip = AudioSystem.getClip();
				clip.open(ais);

				// �Ҹ� ����
				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			
				// ���� ����
				gainControl.setValue(-10.0f);

				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);

			}else{
				System.out.println("���������� �����ϴ�.");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
