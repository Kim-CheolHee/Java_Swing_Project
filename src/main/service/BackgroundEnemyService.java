package main.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import main.component.Enemy;

//Runnable �������̽��� ������ �޼ҵ尡 run() �ϳ� ���� �Լ��� �������̽�, run()�� while�� ���ѹݺ��ϸ� Enemy�� ���¸� üũ�Ѵ�.
public class BackgroundEnemyService implements Runnable {

	private BufferedImage image;
	private Enemy enemy;
	private int JUMPCOUNT = 0; // ���� ī��Ʈ
	private int FIRST = 0; // �ٴ� ���� ���� 0 �ٴ�, 1 �����
	private int BOTTOMCOLOR = -131072; // �ٴ� ����.

	// Enemy ��ü�� �Ű������� Ȱ�� -> ��������
	public BackgroundEnemyService(Enemy enemy) {
		this.enemy = enemy;
		try { // �ɸ��� �浹���θ� ��ǥ�� �ϱ⿣ �ʹ� �����ؼ� ����ڿ��� �������� backgroundMap�� �ƴ� RGB �������� ������ ���� �� Ȱ��
			image = ImageIO.read(new File("image/backgroundMapService2.png"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void run() {
		while (enemy.getState() == 0) { // ���ݹ��� ���°� �ƴ� ��.
			// �ɸ��� ����Ȯ��(���� ���� ��� ������ ����)
			Color leftColor = new Color(image.getRGB(enemy.getX() - 10, enemy.getY() + 25));
			Color rightColor = new Color(image.getRGB(enemy.getX() + 50 + 15, enemy.getY() + 25));
			// bottomColor�� -2��� �ٴ��� ����̶�� �ǹ�.
			int bottomColor = image.getRGB(enemy.getX() + 10, enemy.getY() + 50 + 5) // ���� �ϴ� -1�̸� ���
					+ image.getRGB(enemy.getX() + 50 - 10, enemy.getY() + 50 + 5); // ������ �ϴ� -1�̸� ���

			// �ٴ� �浹 Ȯ��
			if (bottomColor != -2) { // bottomColor�� ����� �ƴϸ� down�޼ҵ�� �۵����� ����
//				System.out.println("�����÷� : " + bottomColor);
//				System.out.println("�ٴڿ� �浹��");
				enemy.setDown(false);
			} else { // bottomColor�� ����̶��
				if (!enemy.isUp() && !enemy.isDown()) { // ���� �׸��� �ٿ� ���°� �ƴ� ��
					enemy.down();
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
				enemy.setRight(false); // ���������� �̵��� ���߰�
				enemy.setLeft(true); // �������� ������ȯ
				if (!enemy.isUp() && !enemy.isDown()) {
					JUMPCOUNT++;
					if (JUMPCOUNT == 3) // Ȥ�� JUMPCOUNT�� 3�� �Ǹ� ���� ���� �̵�
						enemy.left();
						enemy.up();
				}
				// ���� ����.
			} else if (JUMPCOUNT < 3 && FIRST == 1 && leftColor.getRed() == 255 && leftColor.getGreen() == 0
					&& leftColor.getBlue() == 0) {
				enemy.setLeft(false);
				enemy.setRight(true);
				if (!enemy.isUp() && !enemy.isDown()) {
					JUMPCOUNT++;
					if (JUMPCOUNT == 3)
						enemy.right();
					enemy.up();
				}
			} else if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
				enemy.setLeft(false);
				if (!enemy.isRight()) {
					enemy.right();
				}
			} else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
				enemy.setRight(false);
				if (!enemy.isLeft()) {
					enemy.left();
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
