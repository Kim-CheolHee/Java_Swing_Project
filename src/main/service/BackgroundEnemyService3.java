package main.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import main.component.Enemy;
import main.component.Enemy2;
import main.component.Enemy3;

//Runnable �������̽��� ������ �޼ҵ尡 run() �ϳ� ���� �Լ��� �������̽�, run()�� while�� ���ѹݺ��ϸ� Enemy3�� ���¸� üũ�Ѵ�.
public class BackgroundEnemyService3 implements Runnable {

	private BufferedImage image;
	private Enemy3 enemy3;
	private int JUMPCOUNT = 0; // ���� ī��Ʈ
	private int FIRST = 0; // �ٴ� ���� ���� 0 �ٴ�, 1 �����
	private int BOTTOMCOLOR = -131072; // �ٴ� ����.

	// Enemy2 ��ü�� �Ű������� Ȱ�� -> ��������
	public BackgroundEnemyService3(Enemy3 enemy3) {
		this.enemy3 = enemy3;
		try { // �ɸ��� �浹���θ� ��ǥ�� �ϱ⿣ �ʹ� �����ؼ� ����ڿ��� �������� backgroundMap�� �ƴ� RGB �������� ������ ���� �� Ȱ��
			image = ImageIO.read(new File("image/backgroundMapService2.png"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void run() {
		while (enemy3.getState() == 0) { // ���ݹ��� ���°� �ƴ� ��.
			// �ɸ��� ����Ȯ��(���� ���� ��� ������ ����)
			Color leftColor = new Color(image.getRGB(enemy3.getX() - 10, enemy3.getY() + 150));
			Color rightColor = new Color(image.getRGB(enemy3.getX() + 270 + 10, enemy3.getY() + 150));
			// bottomColor�� -2��� �ٴ��� ����̶�� �ǹ�.
			int bottomColor = image.getRGB(enemy3.getX() + 100, enemy3.getY() + 270 + 5) // ���� �ϴ� -1�̸� ���
					+ image.getRGB(enemy3.getX() + 150 - 10, enemy3.getY() + 270 + 5); // ������ �ϴ� -1�̸� ���

			// �ٴ� �浹 Ȯ��
			if (bottomColor != -2) { // bottomColor�� ����� �ƴϸ� down�޼ҵ�� �۵����� ����
//				System.out.println("�����÷� : " + bottomColor);
//				System.out.println("�ٴڿ� �浹��");
				enemy3.setDown(false);
			} else { // bottomColor�� ����̶��
				if (!enemy3.isUp() && !enemy3.isDown()) { // ���� �׸��� �ٿ� ���°� �ƴ� ��
					enemy3.down();
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
				enemy3.setRight(false); // ���������� �̵��� ���߰�
				enemy3.setLeft(true); // �������� ������ȯ
				if (!enemy3.isUp() && !enemy3.isDown()) {
					JUMPCOUNT++;
					if (JUMPCOUNT == 3) // Ȥ�� JUMPCOUNT�� 3�� �Ǹ� ���� ���� �̵�
						enemy3.left();
						enemy3.up();
				}
				// ���� ����.
			} else if (JUMPCOUNT < 3 && FIRST == 1 && leftColor.getRed() == 255 && leftColor.getGreen() == 0
					&& leftColor.getBlue() == 0) {
				enemy3.setLeft(false);
				enemy3.setRight(true);
				if (!enemy3.isUp() && !enemy3.isDown()) {
					JUMPCOUNT++;
					if (JUMPCOUNT == 3)
						enemy3.right();
					enemy3.up();
				}
			} else if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
				enemy3.setLeft(false);
				if (!enemy3.isRight()) {
					enemy3.right();
				}
			} else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
				enemy3.setRight(false);
				if (!enemy3.isLeft()) {
					enemy3.left();
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
