package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;
import main.component.Arrow;
import main.component.Enemy;
import main.component.Enemy2;
import main.component.Enemy3;
import main.component.GameOver;
import main.component.GameWin;
import main.component.Player;
import main.music.BGMArrow;
import main.music.BGMGameWin;
import main.music.BGM;
import main.state.EnemyWay;
import main.util.GameDTO;

@Getter
@Setter
public class FrameMain extends JFrame {

	// 익명구현메소드에서 this를 매개변수로 쓰면 해당 메소드로 인지하기 때문에 this로 인식하게 하고 싶은 클래스타입의 변수를 생성하여
	// this 부여.
	private FrameMain mContext = this;

	private JLabel backgroundMap;
	private Player player;
	private Arrow arrow;

	// 플레이어 목숨 관련
	private JLabel life;
	private JLabel life2;
	private JLabel life3;
	private ImageIcon imageLife;
	private ImageIcon imageLife2;
	private ImageIcon imageLife3;
	private boolean LifeStatus = true;
	private ImageIcon playerL;

	// 적 변수 선언
	private List<Enemy> enemys;
	private List<Enemy2> enemys2;
	private List<Enemy3> enemys3;
	public int remainEnemy = 2;
	public int remainEnemy2 = 2;
	public int remainEnemy3 = 1;

	// bgm 관련
	private BGM bgm;
	private BGMArrow arrowBGM;
	private BGMGameWin bgmGameWin;

	// 점수 관련
	public JLabel laScore;
	public int score = 1000;

	// 게임종료(lose or win) 후 LastFrame으로 전환
	private GameOver gameOver;
	private GameWin gameWin;
	public GameDTO dto;

	// id, score 값을 가져오기 위해 dto 객체를 매개변수로 넣기
	public FrameMain(GameDTO dto) {
		this.dto = dto;
		initObject(dto);
		initSetting();
		initListener();
		setVisible(true);
	}

	private void initObject(GameDTO dto) { // 컴포넌트들을 집어넣는 메소드.
		backgroundMap = new JLabel(new ImageIcon("image/backgroundMap2.png"));
		setContentPane(backgroundMap); // jframe -> pane -> label 이 아닌 jframe -> label로 셋팅.
		customCursor();
		
		player = new Player(mContext, dto);
		add(player);

		enemys = new ArrayList<Enemy>();
		enemys.add(new Enemy(mContext, EnemyWay.RIGHT));
		enemys.add(new Enemy(mContext, EnemyWay.LEFT));
		for (Enemy e : enemys) {
			add(e);
		}
		enemys2 = new ArrayList<Enemy2>();
		enemys2.add(new Enemy2(mContext, EnemyWay.RIGHT));
		enemys2.add(new Enemy2(mContext, EnemyWay.LEFT));
		for (Enemy2 e : enemys2) {
			add(e);
		}
		enemys3 = new ArrayList<Enemy3>();
		enemys3.add(new Enemy3(mContext, EnemyWay.RIGHT));
		for (Enemy3 e : enemys3) {
			add(e);
		}
//		getEnemyData(enemys, enemys2, enemys3);

		bgm = new BGM();
		bgm.playBGM("bgmMaple.wav");

		laScore = new JLabel();
		add(laScore);

		add(addLife());
		add(addLife2());
		add(addLife3());

	}

	private void initSetting() { // 프레임 기본 셋팅을 담당하는 메소드.
		setTitle("KimCheolHee 230108_2.0v");
		setSize(1000, 640);
		setLayout(null);
		setLocationRelativeTo(null); // jframe 창을 모니터 중앙에 배치.
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		laScore.setText("" + dto.getScore());
		laScore.setSize(100, 30);
		laScore.setLocation(900, 0);
		laScore.setFont(new Font("Serif", Font.BOLD, 30));
		laScore.setForeground(Color.WHITE);
	}

	private Component addLife() {
		imageLife = new ImageIcon("image/LifeCount.png");
		life = new JLabel(imageLife);
		life.setSize(30, 30);
		life.setLocation(470, 10);
		return life;
	}

	private Component addLife2() {
		imageLife2 = new ImageIcon("image/LifeCount.png");
		life2 = new JLabel(imageLife2);
		life2.setSize(30, 30);
		life2.setLocation(500, 10);
		return life2;
	}

	private Component addLife3() {
		imageLife3 = new ImageIcon("image/LifeCount.png");
		life3 = new JLabel(imageLife3);
		life3.setSize(30, 30);
		life3.setLocation(530, 10);
		return life3;
	}

	private void initListener() {
		// KeyAdapter(추상클래스) 활용 KeyListener의 메소드 중 필요한 메소드만 사용.
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) { // 눌러진 키보드의 값을 가져온다.
				case KeyEvent.VK_LEFT: // 37을 입력해도 됨.(0x25의 16진수 값을 갖고 있으며 10진수로는 37이다)
					// boolean 타입의 private 변수를 가져올 땐 is가 변수명 앞에 붙는다.
					if (!player.isLeft() && !player.isLeftWallCrash() && player.getState() == 0) {
						player.left();
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (!player.isRight() && !player.isRightWallCrash() && player.getState() == 0) {
						player.right();
					}
					break;
				case KeyEvent.VK_UP:
					if (!player.isUp() && !player.isDown() && player.getState() == 0) { // 업과 다운이 아닐 때만 업한다.
						player.up();
					}
					break;
				case KeyEvent.VK_DOWN:
					if (!player.isUp() && !player.isDown() && player.getState() == 0) { // 업과 다운이 아닐 때만 다운한다.
						player.down(); // 계속 다운되는 bug 존재
					}
					break;
				case KeyEvent.VK_SPACE:
					if (player.getState() == 0)
						arrowBGM = new BGMArrow();
					arrowBGM.ArrowBGM("Arrow.wav");
					player.attack();
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) { // 키보드를 누르고 뗐을 때 발동되는 메소드
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					player.setLeft(false);
					break;
				case KeyEvent.VK_RIGHT:
					player.setRight(false);
					break;
				}
			}
		});
	}

//	public void reset() {
//		player = new Player(mContext, dto);
//		add(player);
//		System.out.println("플레이어 리셋");
//	}

	public void gameEnd(GameDTO dto) {
		this.dto = dto;
		LifeStatus = false;
//		reset();
		new FrameLast(dto);
		dispose();
	}
	
	public void gameWin(GameDTO dto) {
		System.out.println("승리하였습니다.");
		new BGMGameWin();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.dto = dto;
		LifeStatus = false;
		
		mContext.getBgm().stopBGM(); // 음악이 멈추었습니다.
		gameWin = new GameWin(mContext, dto);
		mContext.add(gameWin);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mContext.remove(this);
		mContext.repaint();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new FrameLast(dto);
		dispose();
	}
	
	private void customCursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage("image/mouseCursorRedRibbonPig.png");
		Point point = new Point(20, 20);
		Cursor cursor = toolkit.createCustomCursor(cursorImage, point, "redRibbonPig");
		backgroundMap.setCursor(cursor);
	}

//	public synchronized void getEnemyData(List<Enemy> enemys, List<Enemy2> enemys2, List<Enemy3> enemys3) {
//		this.enemys = enemys;
//		this.enemys2 = enemys2;
//		this.enemys3 = enemys3;
//		if (enemys != null && enemys2 != null) {
//			try {
//				wait();
//			} catch (InterruptedException e) {
//				System.out.println("몬스터 발록 출현 지연 메소드 예외 발생");
//				e.printStackTrace();
//			}
//		} else {
//			enemys3 = new ArrayList<Enemy3>();
//			enemys3.add(new Enemy3(mContext, EnemyWay.RIGHT));
//			for (Enemy3 e : enemys3) {
//				add(e);
//			}
//		}
//		notify();
//	}

}