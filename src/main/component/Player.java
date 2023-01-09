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

	// 클래스 타입 변수 선언
	private FrameMain mContext;
	private List<Arrow> arrowList;
	private GameOver gameOver;

	// 케릭터 위치 상태
	private int x;
	private int y;

	// 케릭터 방향
	private PlayerWay playerWay;

	// 케릭터 움직임 상태, 이미지
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	private ImageIcon playerR, playerL, playerRdie, playerLdie;

	// 벽에 충돌한 상태
	private boolean leftWallCrash;
	private boolean rightWallCrash;

	// 케릭터 속도 상태
	private final int SPEED = 4; // 상수는 대문자로 선언한다.
	private final int JUMPSPEED = 2;

	// 케릭터 생존 여부
	private int state = 0; // 0 : live , 1 : die

	// 케릭터 목숨
	private int lifeCount = 3;

	// 로그인 아이디 획득
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
		// 케릭터 초기 위치
		x = 900;
		y = 300;

		// 케릭터 움직임이 처음에는 없기에 false 값 부여
		left = false;
		right = false;
		up = false;
		down = false;
		leftWallCrash = false;
		rightWallCrash = false;

		// 케릭터 초기 생성 시 방향
		playerWay = playerWay.LEFT;
		state = 0;
		setIcon(playerL);
		setSize(50, 50);
		setLocation(x, y);
	}

	private void initBackgroundPlayerService() {
		// BackgroundPlayerService클래스는 Runnable를 implements 했으므로 스레드 매개변수로 바로 선언 가능
		new Thread(new BackgroundPlayerService(this)).start();
	}

	@Override
	public void attack() {
		new Thread(() -> {
			Arrow arrow = new Arrow(mContext, dto); // FrameMain에서 space를 눌러 attack메소드 호출 시 화살이 생성됨
			mContext.add(arrow);
			arrowList.add(arrow);
			if (playerWay == playerWay.LEFT) { // player방향에 따른 화살의 이미지, 왼쪽(검은 화살) / 오른쪽(하얀 화살)
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
		new Thread(() -> { // 람다식 표현 ()->{}, 익명 Runnable 인터페이스에 있는 run 추상메소드를 간소화 표현.
			while (left && getState() == 0) {
				setIcon(playerL);
				x -= SPEED;
				setLocation(x, y);
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
		playerWay = playerWay.RIGHT;
		right = true;
		new Thread(() -> {
			while (right && getState() == 0) {
				setIcon(playerR);
				x += SPEED;
				setLocation(x, y);
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
		System.out.println("점프");
		up = true;
		new Thread(() -> {
			for (int i = 0; i < 130 / JUMPSPEED; i++) { // 점프했을 때 이동거리를 Y - 1 기준으로 잡았을 때 130이 이상적
				// 임으로 130을 한계의 기준으로 잡는다. JUMSPEED의 값에 따라 이동거리가 달라짐으로 130을 JUMPSPEED로 나눠주면
				// JUMP값을 바꿔도 점프 시 이동거리는 변하지 않는다.
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
			System.out.println("lifeCount 전: " + lifeCount);
			lifeCount -= 1;
			mContext.remove(mContext.getLife());
			mContext.repaint();
			setState(1);
			setIcon(PlayerWay.RIGHT == playerWay ? playerRdie : playerLdie);
			new BGMGameOver();
			try {
				Thread.sleep(1000);
			} catch (Exception e1) {
				System.out.println("케릭터 죽은 상태 예외 발생");
				e1.printStackTrace();
			}
			mContext.remove(this);
			mContext.repaint();
//			mContext.reset();

//			if (lifeCount == 0) {
			try {
				System.out.println("lifeCount 후: " + lifeCount);
				setState(1);
				mContext.getBgm().stopBGM(); // 배경음악 멈추기.
				gameOver = new GameOver(mContext, dto);
				mContext.add(gameOver);
				Thread.sleep(100);
				mContext.remove(this);
				mContext.repaint();

				System.out.println("플레이어 사망.");
				Thread.sleep(3000);
				mContext.gameEnd(dto);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			}
		}).start();
	}

}
