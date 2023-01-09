package main.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import main.component.Arrow;
import main.component.Player;
import main.music.BGMBlop;

// Runnable 인터페이스는 구현할 메소드가 run() 하나 뿐인 함수형 인터페이스, run()의 while이 무한반복하며 Player의 상태를 체크한다.
public class BackgroundPlayerService implements Runnable {

	private BufferedImage image;
	private Player player;
	private List<Arrow> arrowList;
	private BGMBlop blopBGM;

	// Player 객체를 매개변수로 활용 -> 컴포지션
	public BackgroundPlayerService(Player player) {
		this.player = player;
		this.arrowList = player.getArrowList();
		try { // 케릭터 충돌여부를 좌표로 하기엔 너무 복잡해서 사용자에게 보여지는 backgroundMap이 아닌 RGB 색상으로 구성된 별도 맵 활용
			image = ImageIO.read(new File("image/backgroundMapService2.png"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void run() {
		while (player.getState() == 0) { // while문으로 반복하지 않으면 색상이 최초 한번만 표시됨
			// 화살 충돌 체크
			for (int i = 0; i < arrowList.size(); i++) {
				Arrow bubble = arrowList.get(i);
				if (bubble.getState() == 1) {
					if ((Math.abs(player.getX() - bubble.getX()) < 10)
							&& Math.abs(player.getY() - bubble.getY()) < 50) {
						System.out.println("적 제거 완료");
						blopBGM = new BGMBlop();
						blopBGM.BlopBGM("Blop.wav");
						bubble.clearArrowed();
						break;
					}
				}
			}

			// 케릭터 색상확인(라벨의 왼쪽 상단 꼭지점 기준)
			Color leftColor = new Color(image.getRGB(player.getX() - 10, player.getY() + 25));
			Color rightColor = new Color(image.getRGB(player.getX() + 50 + 15, player.getY() + 25));
			Color downColor = new Color(player.getY() - 60);

			// bottomColor가 -2라면 바닥이 흰색이라는 의미.
			int bottomColor = image.getRGB(player.getX() + 10, player.getY() + 50 + 5) // -1
					+ image.getRGB(player.getX() + 50 - 10, player.getY() + 50 + 5); // -1

			// 바닥 충돌 확인
			if (downColor.getRed() == 255 && downColor.getGreen() == 0 && downColor.getBlue() == 0) {
				player.setDown(false);
			}
			if (bottomColor != -2) { // bottomColor가 흰색이 아니면 down메소드는 작동하지 않음
//				System.out.println("바텀컬러 : " + bottomColor);
//				System.out.println("바닥에 충돌함");
				player.setDown(false);
			} else { // bottomColor가 흰색이라면
				if (!player.isUp() && !player.isDown()) { // 점프 그리고 다운 상태가 아닐 때
					player.down();
				}
			}

			// 좌우벽 충돌 확인.
			if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
//				System.out.println("왼쪽 벽에 충돌함");
				player.setLeftWallCrash(true);
				player.setLeft(false);
			} else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
//				System.out.println("오른쪽 벽에 충돌함");
				player.setRightWallCrash(true);
				player.setRight(false);
			} else {
				player.setLeftWallCrash(false);
				player.setRightWallCrash(false);
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
