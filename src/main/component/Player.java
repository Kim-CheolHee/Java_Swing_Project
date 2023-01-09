package main.component;

import java.util.*;
import javax.swing.*;

import lombok.*;
import main.FrameMain;
import main.music.*;
import main.service.BackgroundPlayerService;
import main.state.Moveable;
import main.state.PlayerWay;
import main.util.GameDTO;

@Getter
@Setter
public class Player extends JLabel implements Moveable {

	// Ŭ���� Ÿ�� ���� ����
	private FrameMain mContext;
	private List<Arrow> arrowList;
	private GameOver gameOver;

	// �ɸ��� ��ġ ����
	private int x;
	private int y;

	// �ɸ��� ����
	private PlayerWay playerWay;

	// �ɸ��� ������ ����, �̹���
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	private ImageIcon playerR, playerL, playerRdie, playerLdie;

	// ���� �浹�� ����
	private boolean leftWallCrash;
	private boolean rightWallCrash;

	// �ɸ��� �ӵ� ����
	private final int SPEED = 4; // ����� �빮�ڷ� �����Ѵ�.
	private final int JUMPSPEED = 2;

	// �ɸ��� ���� ����
	private int state = 0; // 0 : live , 1 : die

	// �ɸ��� ���
	private int lifeCount = 3;

	// �α��� ���̵� ȹ��
	public GameDTO dto;

	public Player(FrameMain mContext, GameDTO dto) {
		this.mContext = mContext;
		this.dto = dto;
		initObject();
		initSetting();
		initBackgroundPlayerService();
	}

	private void initObject() {
		playerR = new ImageIcon("image/archerR.png");
		playerL = new ImageIcon("image/archerL.png");
		playerRdie = new ImageIcon("image/archerRDie.png");
		playerLdie = new ImageIcon("image/archerLDie.png");
		arrowList = new ArrayList<Arrow>();
	}

	private void initSetting() {
		// �ɸ��� �ʱ� ��ġ
		x = 900;
		y = 300;

		// �ɸ��� �������� ó������ ���⿡ false �� �ο�
		left = false;
		right = false;
		up = false;
		down = false;
		leftWallCrash = false;
		rightWallCrash = false;

		// �ɸ��� �ʱ� ���� �� ����
		playerWay = playerWay.LEFT;
		state = 0;
		setIcon(playerL);
		setSize(50, 50);
		setLocation(x, y);
	}

	private void initBackgroundPlayerService() {
		// BackgroundPlayerServiceŬ������ Runnable�� implements �����Ƿ� ������ �Ű������� �ٷ� ���� ����
		new Thread(new BackgroundPlayerService(this)).start();
	}

	@Override
	public void attack() {
		new Thread(() -> {
			Arrow arrow = new Arrow(mContext, dto); // FrameMain���� space�� ���� attack�޼ҵ� ȣ�� �� ȭ���� ������
			mContext.add(arrow);
			arrowList.add(arrow);
			if (playerWay == playerWay.LEFT) { // player���⿡ ���� ȭ���� �̹���, ����(���� ȭ��) / ������(�Ͼ� ȭ��)
				arrow.left();
			} else {
				arrow.right();
			}
		}).start();
	}

	@Override
	public void left() {
//		System.out.println("left");
		playerWay = playerWay.LEFT;
		left = true;
		new Thread(() -> { // ���ٽ� ǥ�� ()->{}, �͸� Runnable �������̽��� �ִ� run �߻�޼ҵ带 ����ȭ ǥ��.
			while (left && getState() == 0) {
				setIcon(playerL);
				x -= SPEED;
				setLocation(x, y);
				try {
					Thread.sleep(10); // �ʹ� ���� �ݺ������� 0.01�� ����.
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void right() {
//		System.out.println("right");
		playerWay = playerWay.RIGHT;
		right = true;
		new Thread(() -> {
			while (right && getState() == 0) {
				setIcon(playerR);
				x += SPEED;
				setLocation(x, y);
				try {
					Thread.sleep(10); // �ʹ� ���� �ݺ������� 0.01�� ����
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void up() {
		System.out.println("����");
		up = true;
		new Thread(() -> {
			for (int i = 0; i < 130 / JUMPSPEED; i++) { // �������� �� �̵��Ÿ��� Y - 1 �������� ����� �� 130�� �̻���
				// ������ 130�� �Ѱ��� �������� ��´�. JUMSPEED�� ���� ���� �̵��Ÿ��� �޶������� 130�� JUMPSPEED�� �����ָ�
				// JUMP���� �ٲ㵵 ���� �� �̵��Ÿ��� ������ �ʴ´�.
				y -= JUMPSPEED;
				setLocation(x, y);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			up = false;
			down();
		}).start();
	}

	@Override
	public void down() {
		System.out.println("down");
		down = true;
		new Thread(() -> {
			while (down) {
				y += JUMPSPEED;
				setLocation(x, y);
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			down = false;
		}).start();
	}

	public void die() {
		new Thread(() -> {
			System.out.println("lifeCount ��: " + lifeCount);
			lifeCount -= 1;
			mContext.remove(mContext.getLife());
			mContext.repaint();
			setState(1);
			setIcon(PlayerWay.RIGHT == playerWay ? playerRdie : playerLdie);
			new BGMGameOver();
			try {
				Thread.sleep(1000);
			} catch (Exception e1) {
				System.out.println("�ɸ��� ���� ���� ���� �߻�");
				e1.printStackTrace();
			}
			mContext.remove(this);
			mContext.repaint();
//			mContext.reset();

//			if (lifeCount == 0) {
			try {
				System.out.println("lifeCount ��: " + lifeCount);
				setState(1);
				mContext.getBgm().stopBGM(); // ������� ���߱�.
				gameOver = new GameOver(mContext, dto);
				mContext.add(gameOver);
				Thread.sleep(100);
				mContext.remove(this);
				mContext.repaint();

				System.out.println("�÷��̾� ���.");
				Thread.sleep(3000);
				mContext.gameEnd(dto);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			}
		}).start();
	}

}
