package main.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import main.component.Arrow;
import main.component.Player;
import main.music.BGMBlop;

// Runnable �������̽��� ������ �޼ҵ尡 run() �ϳ� ���� �Լ��� �������̽�, run()�� while�� ���ѹݺ��ϸ� Player�� ���¸� üũ�Ѵ�.
public class BackgroundPlayerService implements Runnable {

	private BufferedImage image;
	private Player player;
	private List<Arrow> arrowList;
	private BGMBlop blopBGM;

	// Player ��ü�� �Ű������� Ȱ�� -> ��������
	public BackgroundPlayerService(Player player) {
		this.player = player;
		this.arrowList = player.getArrowList();
		try { // �ɸ��� �浹���θ� ��ǥ�� �ϱ⿣ �ʹ� �����ؼ� ����ڿ��� �������� backgroundMap�� �ƴ� RGB �������� ������ ���� �� Ȱ��
			image = ImageIO.read(new File("image/backgroundMapService2.png"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void run() {
		while (player.getState() == 0) { // while������ �ݺ����� ������ ������ ���� �ѹ��� ǥ�õ�
			// ȭ�� �浹 üũ
			for (int i = 0; i < arrowList.size(); i++) {
				Arrow bubble = arrowList.get(i);
				if (bubble.getState() == 1) {
					if ((Math.abs(player.getX() - bubble.getX()) < 10)
							&& Math.abs(player.getY() - bubble.getY()) < 50) {
						System.out.println("�� ���� �Ϸ�");
						blopBGM = new BGMBlop();
						blopBGM.BlopBGM("Blop.wav");
						bubble.clearArrowed();
						break;
					}
				}
			}

			// �ɸ��� ����Ȯ��(���� ���� ��� ������ ����)
			Color leftColor = new Color(image.getRGB(player.getX() - 10, player.getY() + 25));
			Color rightColor = new Color(image.getRGB(player.getX() + 50 + 15, player.getY() + 25));
			Color downColor = new Color(player.getY() - 60);

			// bottomColor�� -2��� �ٴ��� ����̶�� �ǹ�.
			int bottomColor = image.getRGB(player.getX() + 10, player.getY() + 50 + 5) // -1
					+ image.getRGB(player.getX() + 50 - 10, player.getY() + 50 + 5); // -1

			// �ٴ� �浹 Ȯ��
			if (downColor.getRed() == 255 && downColor.getGreen() == 0 && downColor.getBlue() == 0) {
				player.setDown(false);
			}
			if (bottomColor != -2) { // bottomColor�� ����� �ƴϸ� down�޼ҵ�� �۵����� ����
//				System.out.println("�����÷� : " + bottomColor);
//				System.out.println("�ٴڿ� �浹��");
				player.setDown(false);
			} else { // bottomColor�� ����̶��
				if (!player.isUp() && !player.isDown()) { // ���� �׸��� �ٿ� ���°� �ƴ� ��
					player.down();
				}
			}

			// �¿캮 �浹 Ȯ��.
			if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
//				System.out.println("���� ���� �浹��");
				player.setLeftWallCrash(true);
				player.setLeft(false);
			} else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
//				System.out.println("������ ���� �浹��");
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
