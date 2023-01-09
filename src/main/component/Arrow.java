package main.component;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;
import main.FrameMain;
import main.service.BackgroundArrowService;
import main.state.Moveable;
import main.util.GameDTO;

@Getter
@Setter // �Һ� Ȱ��
public class Arrow extends JLabel implements Moveable {

	// main Ŭ���� Ÿ�� ���� ����, Player&Enemy&BackgroundArrowService�� �����ϴ� ��������
	private FrameMain mContext;
	private Player player;
	private List<Enemy> enemys;
	private List<Enemy2> enemys2;
	private List<Enemy3> enemys3;
	private Enemy removeEnemy; // ���� �����ϴ� ����
	private Enemy2 removeEnemy2;
	private Enemy3 removeEnemy3;
	private BackgroundArrowService backgroundArrowService;

	// ��ġ ����
	private int x;
	private int y;

	// ������ ����
	private boolean left;
	private boolean right;

	// ǥ���� ������ ��
	private int state; // 0(���󰡴� ȭ��), 1(���� ���� ȭ��)
	private ImageIcon bowR; // ������ ȭ��
	private ImageIcon bowL; // ���� ȭ��
	private ImageIcon bombRed; // ���� ȭ�쿡 ���� ����
	private ImageIcon bomb; // �÷��̾ ������ ����

	// ���� ����
	public int score;
	public GameDTO dto;

	public Arrow(FrameMain mContext, GameDTO dto) { // MainFrame�� ���ӵǴ� Arrow �⺻ ������
		this.mContext = mContext;
		this.player = mContext.getPlayer();
		this.enemys = mContext.getEnemys();
		this.enemys2 = mContext.getEnemys2();
		this.enemys3 = mContext.getEnemys3();
		this.dto = dto;
		initObject();
		initSetting();
	}

	private void initObject() {
		bowR = new ImageIcon("image/bowR.png");
		bowL = new ImageIcon("image/bowL.png");
		bomb = new ImageIcon("image/bomb.png");
		bombRed = new ImageIcon("image/bombRed.png");

		// ȭ���� ��ġ���� ������ �δ� backgroundArrowService ��ü ����
		backgroundArrowService = new BackgroundArrowService(this);
	}

	private void initSetting() {
		left = false;
		right = false;

		x = player.getX();
		y = player.getY();

		setIcon(bowR);
		setIcon(bowL);
		setSize(50, 50);
		state = 0;
	}

	@Override
	public void left() {
		left = true;
		// �߻�ü�� ������ ����Ǵ� ���� �ƴ����� for������ ���� ����
		Stop: for (int i = 0; i < 250; i++) {
			x--;
			setLocation(x, y);
			setIcon(bowL);

			if (backgroundArrowService.leftWall()) {
				left = false;
				break;
			}

			// ȭ���� x��ǥ���� ���� x��ǥ�� ���̰� 10�̰ų� ȭ���� y��ǥ���� ���� 0 ~ 50 ���� ���밪�� ������ ��
			for (Enemy e : enemys) {
				if (Math.abs(x - e.getX()) < 10 && Math.abs(y - e.getY()) > 0 && Math.abs(y - e.getY()) < 50) {
					if (e.getState() == 0) {
						attak(e);
						break Stop;
					}
				}
			}
			for (Enemy2 e2 : enemys2) {
				if (Math.abs(x - e2.getX()) < 10 && Math.abs(y - e2.getY()) > 0 && Math.abs(y - e2.getY()) < 50) {
					if (e2.getState() == 0) {
						attak(e2);
						break Stop;
					}
				}
			}
			for (Enemy3 e3 : enemys3) {
				if (Math.abs(x - e3.getX()) < 100 && Math.abs(y - e3.getY()) > 0 && Math.abs(y - e3.getY()) < 300) {
					if (e3.getState() == 0) {
						attak(e3);
						break Stop;
					}
				}
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		try {
			if (state == 0) { // �⺻ ȭ���� ��
				Thread.sleep(1);
			} else {
				Thread.sleep(10);
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		if (state == 0) { // ȭ���� ���� ����� �ƴ� ��
			clearArrow(); // ȭ��߻� �� �޸𸮿��� �Ҹ�
		}
	}

	@Override
	public void right() {
		right = true;
		Stop: for (int i = 0; i < 250; i++) {
			x++;
			setLocation(x, y);
			setIcon(bowR);

			if (backgroundArrowService.rightWall()) {
				right = false;
				break;
			}

			// ȭ���� x��ǥ���� ���� x��ǥ�� ���̰� 10�̰ų� ȭ���� y��ǥ���� ���� 0 ~ 50 ���� ���밪�� ������ ��
			for (Enemy e : enemys) {
				if (Math.abs(x - e.getX()) < 10 && Math.abs(y - e.getY()) > 0 && Math.abs(y - e.getY()) < 50) {
					if (e.getState() == 0) {
						attak(e);
						break Stop;
					}
				}
			}
			for (Enemy2 e2 : enemys2) {
				if (Math.abs(x - e2.getX()) < 10 && Math.abs(y - e2.getY()) > 0 && Math.abs(y - e2.getY()) < 50) {
					if (e2.getState() == 0) {
						attak(e2);
						break Stop;
					}
				}
			}
			for (Enemy3 e3 : enemys3) {
				if (Math.abs(x - e3.getX()) < 100 && Math.abs(y - e3.getY()) > 0 && Math.abs(y - e3.getY()) < 300) {
					if (e3.getState() == 0) {
						attak(e3);
						break Stop;
					}
				}
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		try {
			if (state == 0) { // �⺻ ȭ���� ��
				Thread.sleep(1);
			} else {
				Thread.sleep(10);
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		if (state == 0) { // ȭ���� ���� ����� �ƴ� ��
			clearArrow(); // ȭ��߻� �� �޸𸮿��� �Ҹ�
		}
	}

	@Override
	public void up() {
	}

	@Override
	public void attak(Enemy e) { // orangeMushroom
		state = 1; // ���� ���� ȭ��
		mContext.remainEnemy--;
		score += 300;
		dto.setScore(score);
		mContext.laScore.setText("" + score);
		e.setState(1); // ȭ�쿡 ���� �� ����
		setIcon(bombRed);
		mContext.remove(e); // ���� �޸𸮿��� ������� �Ѵ�.(�׷��� Garbage Collection�� ���� �ߵ����� �ʾ� ���� �Ⱥ������� ������, clearArrowed �޼ҵ忡�� ����)
		mContext.repaint();
		if (mContext.remainEnemy <= 0 && mContext.remainEnemy2 <= 0 && mContext.remainEnemy <= 0) {
			mContext.gameWin(dto);
		}
	}

	@Override
	public void attak(Enemy2 e2) { // slime
		state = 1; // ���� ���� ȭ��
		mContext.remainEnemy2--;
		score += 100;
		dto.setScore(score);
		mContext.laScore.setText("" + score);
		e2.setState(1); // ȭ�쿡 ���� �� ����
		setIcon(bombRed);
		mContext.remove(e2); // ���� �޸𸮿��� ������� �Ѵ�.(�׷��� Garbage Collection�� ���� �ߵ����� �ʾ� ���� �Ⱥ������� ������, clearArrowed �޼ҵ忡�� ����)
		mContext.repaint();
		if (mContext.remainEnemy <= 0 && mContext.remainEnemy2 <= 0 && mContext.remainEnemy <= 0) {
			mContext.gameWin(dto);
		}
	}

	@Override
	public void attak(Enemy3 e3) { // ballok
		state = 1; // ���� ���� ȭ��
		mContext.remainEnemy3--;
		score += 500;
		dto.setScore(score);
		mContext.laScore.setText("" + score);
		e3.setState(1); // ȭ�쿡 ���� �� ����
		setIcon(bombRed);
		mContext.remove(e3); // ���� �޸𸮿��� ������� �Ѵ�.(�׷��� Garbage Collection�� ���� �ߵ����� �ʾ� ���� �Ⱥ������� ������, clearArrowed �޼ҵ忡�� ����)
		mContext.repaint();
		if (mContext.remainEnemy <= 0 && mContext.remainEnemy2 <= 0 && mContext.remainEnemy <= 0) {
			mContext.gameWin(dto);
		}
	}

	private void clearArrow() {
		try {
			mContext.getPlayer().getArrowList().remove(this); // ȭ�� ��ü �޸𸮿��� ������
			mContext.remove(this); // MainFrame�� ȭ�� �޸𸮿��� �Ҹ�ȴ�.
			mContext.repaint(); // MainFrame�� ��ü�� �ٽ� �׸���.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clearArrowed() {
		new Thread(() -> {
			System.out.println("clearArrowed");
			try {
				setIcon(bomb);
				Thread.sleep(1000);
				// ȭ�� ��ü �޸𸮿��� ������
				mContext.getPlayer().getArrowList().remove(this);
				mContext.getEnemys().remove(removeEnemy); // MainFrame�� enemy ����
				mContext.getEnemys2().remove(removeEnemy2); // MainFrame�� enemy2 ����
				mContext.getEnemys3().remove(removeEnemy3); // MainFrame�� enemy3 ����
				mContext.remove(this);
				mContext.repaint();
			} catch (Exception e) {
			}
		}).start();
	}
}
