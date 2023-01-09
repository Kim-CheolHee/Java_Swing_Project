package main.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import main.component.Arrow;

public class BackgroundArrowService {

	private BufferedImage image;
	private Arrow arrow;

	public BackgroundArrowService(Arrow arrow) { // Arrow 객체를 매개변수로 활용 -> 컴포지션
		this.arrow = arrow;
		try { // 사용자에게 보여지는 backgroundMap이 아닌 RGB 색상으로 구성된 별도 맵 활용
			image = ImageIO.read(new File("image/backgroundMapService2.png"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean leftWall() {
		Color leftColor = new Color(image.getRGB(arrow.getX() - 10, arrow.getY() + 25));
		// 왼쪽벽 충돌 확인.
		if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
			return true;
		}
		return false;
	}

	public boolean rightWall() {
		Color rightColor = new Color(image.getRGB(arrow.getX() + 50 + 15, arrow.getY() + 25));
		// 오른쪽벽 충돌 확인.
		if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
			return true;
		}
		return false;
	}

	public boolean topWall() {
		Color topColor = new Color(image.getRGB(arrow.getX() + 25, arrow.getY() - 10));
		// 위쪽벽 충돌 확인.
		if (topColor.getRed() == 255 && topColor.getGreen() == 0 && topColor.getBlue() == 0) {
			return true;
		}
		return false;
	}

}
