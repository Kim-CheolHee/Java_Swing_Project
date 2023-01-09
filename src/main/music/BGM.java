package main.music;

import java.io.*;
import javax.sound.sampled.*;

public class BGM {
	public String bgmFolder = "sound"; // 폴더 경로 저장
	public Clip clip;
	public boolean isPlayed = true;

	public void stopBGM(){
		if(clip != null){
			clip.stop();
			System.out.println("음악이 멈추었습니다.");
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

				// 소리 설정
				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			
				// 볼륨 조정
				gainControl.setValue(-10.0f);

				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);

			}else{
				System.out.println("음악파일이 없습니다.");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
