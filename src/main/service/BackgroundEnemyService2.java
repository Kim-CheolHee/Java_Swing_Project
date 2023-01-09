package main.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import main.component.Enemy;
import main.component.Enemy2;

//Runnable 인터페이스는 구현할 메소드가 run() 하나 뿐인 함수형 인터페이스, run()의 while이 무한반복하며 Enemy2의 상태를 체크한다.
public class BackgroundEnemyService2 implements Runnable {

	private BufferedImage image;
	private Enemy2 enemy2;
	private int JUMPCOUNT = 0; // 점프 카운트
	private int FIRST = 0; // 바닥 도착 여부 0 바닥, 1 꼭대기
	private int BOTTOMCOLOR = -131072; // 바닥 빨강.

	// Enemy2 객체를 매개변수로 활용 -> 컴포지션
	public BackgroundEnemyService2(Enemy2 enemy2) {
		this.enemy2 = enemy2;
		try { // 케릭터 충돌여부를 좌표로 하기엔 너무 복잡해서 사용자에게 보여지는 backgroundMap이 아닌 RGB 색상으로 구성된 별도 맵 활용
			image = ImageIO.read(new File("image/backgroundMapService2.png"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void run() {
		while (enemy2.getState() == 0) { // 공격받은 상태가 아닐 때.
			// 케릭터 색상확인(라벨의 왼쪽 상단 꼭지점 기준)
			Color leftColor = new Color(image.getRGB(enemy2.getX() - 10, enemy2.getY() + 25));
			Color rightColor = new Color(image.getRGB(enemy2.getX() + 50 + 15, enemy2.getY() + 25));
			// bottomColor가 -2라면 바닥이 흰색이라는 의미.
			int bottomColor = image.getRGB(enemy2.getX() + 10, enemy2.getY() + 50 + 5) // 왼쪽 하단 -1이면 흰색
					+ image.getRGB(enemy2.getX() + 50 - 10, enemy2.getY() + 50 + 5); // 오른쪽 하단 -1이면 흰색

			// 바닥 충돌 확인
			if (bottomColor != -2) { // bottomColor가 흰색이 아니면 down메소드는 작동하지 않음
//				System.out.println("바텀컬러 : " + bottomColor);
//				System.out.println("바닥에 충돌함");
				enemy2.setDown(false);
			} else { // bottomColor가 흰색이라면
				if (!enemy2.isUp() && !enemy2.isDown()) { // 점프 그리고 다운 상태가 아닐 때
					enemy2.down();
				}
			}

			// 바닥 도착
			if (bottomColor == BOTTOMCOLOR) {
				FIRST = 1;
			}

			// 꼭대기 도착.
			if (JUMPCOUNT >= 3) {
				JUMPCOUNT = 0;
				FIRST = 0;
			}

			// 오른쪽 구석
			if (JUMPCOUNT < 3 && FIRST == 1 && rightColor.getRed() == 255 && rightColor.getGreen() == 0
					&& rightColor.getBlue() == 0) {
				enemy2.setRight(false); // 오른쪽으로 이동을 멈추고
				enemy2.setLeft(true); // 왼쪽으로 방향전환
				if (!enemy2.isUp() && !enemy2.isDown()) {
					JUMPCOUNT++;
					if (JUMPCOUNT == 3) // 혹은 JUMPCOUNT가 3이 되면 왼쪽 위로 이동
						enemy2.left();
						enemy2.up();
				}
				// 왼쪽 구석.
			} else if (JUMPCOUNT < 3 && FIRST == 1 && leftColor.getRed() == 255 && leftColor.getGreen() == 0
					&& leftColor.getBlue() == 0) {
				enemy2.setLeft(false);
				enemy2.setRight(true);
				if (!enemy2.isUp() && !enemy2.isDown()) {
					JUMPCOUNT++;
					if (JUMPCOUNT == 3)
						enemy2.right();
					enemy2.up();
				}
			} else if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
				enemy2.setLeft(false);
				if (!enemy2.isRight()) {
					enemy2.right();
				}
			} else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
				enemy2.setRight(false);
				if (!enemy2.isLeft()) {
					enemy2.left();
				}
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
