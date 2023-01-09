package main.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import main.component.Enemy;
import main.component.Enemy2;

//Runnable �������̽��� ������ �޼ҵ尡 run() �ϳ� ���� �Լ��� �������̽�, run()�� while�� ���ѹݺ��ϸ� Enemy2�� ���¸� üũ�Ѵ�.
public class BackgroundEnemyService2 implements Runnable {

	private BufferedImage image;
	private Enemy2 enemy2;
	private int JUMPCOUNT = 0; // ���� ī��Ʈ
	private int FIRST = 0; // �ٴ� ���� ���� 0 �ٴ�, 1 �����
	private int BOTTOMCOLOR = -131072; // �ٴ� ����.

	// Enemy2 ��ü�� �Ű������� Ȱ�� -> ��������
	public BackgroundEnemyService2(Enemy2 enemy2) {
		this.enemy2 = enemy2;
		try { // �ɸ��� �浹���θ� ��ǥ�� �ϱ⿣ �ʹ� �����ؼ� ����ڿ��� �������� backgroundMap�� �ƴ� RGB �������� ������ ���� �� Ȱ��
			image = ImageIO.read(new File("image/backgroundMapService2.png"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void run() {
		while (enemy2.getState() == 0) { // ���ݹ��� ���°� �ƴ� ��.
			// �ɸ��� ����Ȯ��(���� ���� ��� ������ ����)
			Color leftColor = new Color(image.getRGB(enemy2.getX() - 10, enemy2.getY() + 25));
			Color rightColor = new Color(image.getRGB(enemy2.getX() + 50 + 15, enemy2.getY() + 25));
			// bottomColor�� -2��� �ٴ��� ����̶�� �ǹ�.
			int bottomColor = image.getRGB(enemy2.getX() + 10, enemy2.getY() + 50 + 5) // ���� �ϴ� -1�̸� ���
					+ image.getRGB(enemy2.getX() + 50 - 10, enemy2.getY() + 50 + 5); // ������ �ϴ� -1�̸� ���

			// �ٴ� �浹 Ȯ��
			if (bottomColor != -2) { // bottomColor�� ����� �ƴϸ� down�޼ҵ�� �۵����� ����
//				System.out.println("�����÷� : " + bottomColor);
//				System.out.println("�ٴڿ� �浹��");
				enemy2.setDown(false);
			} else { // bottomColor�� ����̶��
				if (!enemy2.isUp() && !enemy2.isDown()) { // ���� �׸��� �ٿ� ���°� �ƴ� ��
					enemy2.down();
				}
			}

			// �ٴ� ����
			if (bottomColor == BOTTOMCOLOR) {
				FIRST = 1;
			}

			// ����� ����.
			if (JUMPCOUNT >= 3) {
				JUMPCOUNT = 0;
				FIRST = 0;
			}

			// ������ ����
			if (JUMPCOUNT < 3 && FIRST == 1 && rightColor.getRed() == 255 && rightColor.getGreen() == 0
					&& rightColor.getBlue() == 0) {
				enemy2.setRight(false); // ���������� �̵��� ���߰�
				enemy2.setLeft(true); // �������� ������ȯ
				if (!enemy2.isUp() && !enemy2.isDown()) {
					JUMPCOUNT++;
					if (JUMPCOUNT == 3) // Ȥ�� JUMPCOUNT�� 3�� �Ǹ� ���� ���� �̵�
						enemy2.left();
						enemy2.up();
				}
				// ���� ����.
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
