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

	// main 클래스 타입 변수 선언
	private FrameMain mContext;
	private Player player;

	// 적 위치 상태
	private int x;
	private int y;

	// 적 방향
	private EnemyWay enemyWay;

	// 움직임 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	private int state; // 0(살아있는 상태), 1(화살에 맞은 상태)

	// 적 속도 상태
	private final int SPEED = 3; // 상수는 대문자로 선언한다.
	private final int JUMPSPEED = 1;

	// 왼쪽, 오른쪽 방향 이미지
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
		// 적 초기 위치
		x = 480;
		y = 178;

		// 케릭터 움직임이 처음에는 없기에 false 값 부여
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
		// BackgroundPlayerService클래스는 Runnable를 implements 했으므로 스레드 매개변수로 바로 선언 가능
		new Thread(new BackgroundEnemyService(this)).start();
	}

	@Override
	public void left() {
//		System.out.println("left");
		enemyWay = enemyWay.LEFT;
		left = true;
		new Thread(() -> { // 람다식 표현 ()->{}, 익명 Runnable 인터페이스에 있는 run 추상메소드를 간소화 표현.
			while (left) {
				setIcon(enemyL);
				x -= SPEED;
				setLocation(x, y);

				if (Math.abs(x - player.getX()) < 50 && Math.abs(y - player.getY()) < 50) {
					if (player.getState() == 0 && getState() == 0)
						player.die();
					System.out.println("orangeMushroom 플레이어 죽임");
				}

				try {
					Thread.sleep(10); // 너무 빨리 반복됨으로 0.01초 슬립.
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
					System.out.println("orangeMushroom 플레이어 죽임");
				}

				try {
					Thread.sleep(10); // 너무 빨리 반복됨으로 0.01초 슬립
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void up() {
//		System.out.println("점프");
		up = true;
		new Thread(() -> {
			for (int i = 0; i < 160 / JUMPSPEED; i++) { // JUMSPEED의 값에 따라 이동거리가 달라짐으로 160을 JUMPSPEED로 나눠주면
				// JUMP값을 바꿔도 점프 시 이동거리는 변하지 않는다.
				y -= JUMPSPEED;
				setLocation(x, y);

				if (Math.abs(x - player.getX()) < 50 && Math.abs(y - player.getY()) < 50) {
					if (player.getState() == 0 && getState() == 0)
						player.die();
					System.out.println("orangeMushroom 플레이어 죽임");
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
					System.out.println("orangeMushroom 플레이어 죽임");
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
