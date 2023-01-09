package main.component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;
import main.FrameMain;
import main.service.BackgroundEnemyService;
import main.state.EnemyWay;
import main.state.Moveable;

@Getter
@Setter
public class Enemy extends JLabel implements Moveable {

	// main Ŭ���� Ÿ�� ���� ����
	private FrameMain mContext;
	private Player player;

	// �� ��ġ ����
	private int x;
	private int y;

	// �� ����
	private EnemyWay enemyWay;

	// ������ ����
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	private int state; // 0(����ִ� ����), 1(ȭ�쿡 ���� ����)

	// �� �ӵ� ����
	private final int SPEED = 3; // ����� �빮�ڷ� �����Ѵ�.
	private final int JUMPSPEED = 1;

	// ����, ������ ���� �̹���
	private ImageIcon enemyR, enemyL;

	public Enemy(FrameMain mContext, EnemyWay enemyWay) {
		this.mContext = mContext;
		this.player = mContext.getPlayer();
		initObject();
		initSetting();
		initBackgroundEnemyService();
		initEnemyDirection(enemyWay);
	}

	private void initObject() {
		enemyR = new ImageIcon("image/monsterMushroomR.png");
		enemyL = new ImageIcon("image/monsterMushroomL.png");
	}

	private void initSetting() {
		// �� �ʱ� ��ġ
		x = 480;
		y = 178;

		// �ɸ��� �������� ó������ ���⿡ false �� �ο�
		left = false;
		right = false;
		up = false;
		down = false;

		state = 0;

		setSize(50, 50);
		setLocation(x, y);
	}

	private void initEnemyDirection(EnemyWay enemyWay) {
		if (EnemyWay.RIGHT == enemyWay) {
			enemyWay = EnemyWay.RIGHT;
			setIcon(enemyR);
			right();
		} else {
			enemyWay = EnemyWay.LEFT;
			setIcon(enemyL);
			left();
		}
	}

	private void initBackgroundEnemyService() {
		// BackgroundPlayerServiceŬ������ Runnable�� implements �����Ƿ� ������ �Ű������� �ٷ� ���� ����
		new Thread(new BackgroundEnemyService(this)).start();
	}

	@Override
	public void left() {
//		System.out.println("left");
		enemyWay = enemyWay.LEFT;
		left = true;
		new Thread(() -> { // ���ٽ� ǥ�� ()->{}, �͸� Runnable �������̽��� �ִ� run �߻�޼ҵ带 ����ȭ ǥ��.
			while (left) {
				setIcon(enemyL);
				x -= SPEED;
				setLocation(x, y);

				if (Math.abs(x - player.getX()) < 50 && Math.abs(y - player.getY()) < 50) {
					if (player.getState() == 0 && getState() == 0)
						player.die();
					System.out.println("orangeMushroom �÷��̾� ����");
				}

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
		enemyWay = enemyWay.RIGHT;
		right = true;
		new Thread(() -> {
			while (right) {
				setIcon(enemyR);
				x += SPEED;
				setLocation(x, y);

				if (Math.abs(x - player.getX()) < 50 && Math.abs(y - player.getY()) < 50) {
					if (player.getState() == 0 && getState() == 0)
						player.die();
					System.out.println("orangeMushroom �÷��̾� ����");
				}

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
//		System.out.println("����");
		up = true;
		new Thread(() -> {
			for (int i = 0; i < 160 / JUMPSPEED; i++) { // JUMSPEED�� ���� ���� �̵��Ÿ��� �޶������� 160�� JUMPSPEED�� �����ָ�
				// JUMP���� �ٲ㵵 ���� �� �̵��Ÿ��� ������ �ʴ´�.
				y -= JUMPSPEED;
				setLocation(x, y);

				if (Math.abs(x - player.getX()) < 50 && Math.abs(y - player.getY()) < 50) {
					if (player.getState() == 0 && getState() == 0)
						player.die();
					System.out.println("orangeMushroom �÷��̾� ����");
				}

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
//		System.out.println("down");
		down = true;
		new Thread(() -> {
			while (down) {
				y += JUMPSPEED;
				setLocation(x, y);

				if (Math.abs(x - player.getX()) < 50 && Math.abs(y - player.getY()) < 50) {
					if (player.getState() == 0 && getState() == 0)
						player.die();
					System.out.println("orangeMushroom �÷��̾� ����");
				}

				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			down = false;
		}).start();
	}

}
