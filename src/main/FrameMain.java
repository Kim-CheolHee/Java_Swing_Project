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

	// �͸����޼ҵ忡�� this�� �Ű������� ���� �ش� �޼ҵ�� �����ϱ� ������ this�� �ν��ϰ� �ϰ� ���� Ŭ����Ÿ���� ������ �����Ͽ�
	// this �ο�.
	private FrameMain mContext = this;

	private JLabel backgroundMap;
	private Player player;
	private Arrow arrow;

	// �÷��̾� ��� ����
	private JLabel life;
	private JLabel life2;
	private JLabel life3;
	private ImageIcon imageLife;
	private ImageIcon imageLife2;
	private ImageIcon imageLife3;
	private boolean LifeStatus = true;
	private ImageIcon playerL;

	// �� ���� ����
	private List<Enemy> enemys;
	private List<Enemy2> enemys2;
	private List<Enemy3> enemys3;
	public int remainEnemy = 2;
	public int remainEnemy2 = 2;
	public int remainEnemy3 = 1;

	// bgm ����
	private BGM bgm;
	private BGMArrow arrowBGM;
	private BGMGameWin bgmGameWin;

	// ���� ����
	public JLabel laScore;
	public int score = 1000;

	// ��������(lose or win) �� LastFrame���� ��ȯ
	private GameOver gameOver;
	private GameWin gameWin;
	public GameDTO dto;

	// id, score ���� �������� ���� dto ��ü�� �Ű������� �ֱ�
	public FrameMain(GameDTO dto) {
		this.dto = dto;
		initObject(dto);
		initSetting();
		initListener();
		setVisible(true);
	}

	private void initObject(GameDTO dto) { // ������Ʈ���� ����ִ� �޼ҵ�.
		backgroundMap = new JLabel(new ImageIcon("image/backgroundMap2.png"));
		setContentPane(backgroundMap); // jframe -> pane -> label �� �ƴ� jframe -> label�� ����.
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

	private void initSetting() { // ������ �⺻ ������ ����ϴ� �޼ҵ�.
		setTitle("KimCheolHee 230108_2.0v");
		setSize(1000, 640);
		setLayout(null);
		setLocationRelativeTo(null); // jframe â�� ����� �߾ӿ� ��ġ.
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
		// KeyAdapter(�߻�Ŭ����) Ȱ�� KeyListener�� �޼ҵ� �� �ʿ��� �޼ҵ常 ���.
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) { // ������ Ű������ ���� �����´�.
				case KeyEvent.VK_LEFT: // 37�� �Է��ص� ��.(0x25�� 16���� ���� ���� ������ 10�����δ� 37�̴�)
					// boolean Ÿ���� private ������ ������ �� is�� ������ �տ� �ٴ´�.
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
					if (!player.isUp() && !player.isDown() && player.getState() == 0) { // ���� �ٿ��� �ƴ� ���� ���Ѵ�.
						player.up();
					}
					break;
				case KeyEvent.VK_DOWN:
					if (!player.isUp() && !player.isDown() && player.getState() == 0) { // ���� �ٿ��� �ƴ� ���� �ٿ��Ѵ�.
						player.down(); // ��� �ٿ�Ǵ� bug ����
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
			public void keyReleased(KeyEvent e) { // Ű���带 ������ ���� �� �ߵ��Ǵ� �޼ҵ�
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
//		System.out.println("�÷��̾� ����");
//	}

	public void gameEnd(GameDTO dto) {
		this.dto = dto;
		LifeStatus = false;
//		reset();
		new FrameLast(dto);
		dispose();
	}
	
	public void gameWin(GameDTO dto) {
		System.out.println("�¸��Ͽ����ϴ�.");
		new BGMGameWin();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.dto = dto;
		LifeStatus = false;
		
		mContext.getBgm().stopBGM(); // ������ ���߾����ϴ�.
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
//				System.out.println("���� �߷� ���� ���� �޼ҵ� ���� �߻�");
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