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
@Setter // 롬복 활용
public class Arrow extends JLabel implements Moveable {

	// main 클래스 타입 변수 선언, Player&Enemy&BackgroundArrowService에 의존하는 컴포지션
	private FrameMain mContext;
	private Player player;
	private List<Enemy> enemys;
	private List<Enemy2> enemys2;
	private List<Enemy3> enemys3;
	private Enemy removeEnemy; // 적을 제거하는 변수
	private Enemy2 removeEnemy2;
	private Enemy3 removeEnemy3;
	private BackgroundArrowService backgroundArrowService;

	// 위치 상태
	private int x;
	private int y;

	// 움직임 상태
	private boolean left;
	private boolean right;

	// 표적을 명중할 때
	private int state; // 0(날라가는 화살), 1(적을 맞춘 화살)
	private ImageIcon bowR; // 오른쪽 화살
	private ImageIcon bowL; // 왼쪽 화살
	private ImageIcon bombRed; // 적이 화살에 맞은 상태
	private ImageIcon bomb; // 플레이어가 습득한 상태

	// 점수 저장
	public int score;
	public GameDTO dto;

	public Arrow(FrameMain mContext, GameDTO dto) { // MainFrame에 종속되는 Arrow 기본 생성자
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

		// 화살의 위치값에 제한을 두는 backgroundArrowService 객체 생성
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
		// 발사체는 무한정 진행되는 것이 아님으로 for문으로 값을 제한
		Stop: for (int i = 0; i < 250; i++) {
			x--;
			setLocation(x, y);
			setIcon(bowL);

			if (backgroundArrowService.leftWall()) {
				left = false;
				break;
			}

			// 화살의 x좌표값과 적의 x좌표값 차이가 10이거나 화살의 y좌표값이 적과 0 ~ 50 범위 절대값에 도달할 시
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
			if (state == 0) { // 기본 화살일 때
				Thread.sleep(1);
			} else {
				Thread.sleep(10);
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		if (state == 0) { // 화살이 적을 맞춘게 아닐 때
			clearArrow(); // 화살발사 후 메모리에서 소멸
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

			// 화살의 x좌표값과 적의 x좌표값 차이가 10이거나 화살의 y좌표값이 적과 0 ~ 50 범위 절대값에 도달할 시
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
			if (state == 0) { // 기본 화살일 때
				Thread.sleep(1);
			} else {
				Thread.sleep(10);
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		if (state == 0) { // 화살이 적을 맞춘게 아닐 때
			clearArrow(); // 화살발사 후 메모리에서 소멸
		}
	}

	@Override
	public void up() {
	}

	@Override
	public void attak(Enemy e) { // orangeMushroom
		state = 1; // 적을 맞춘 화살
		mContext.remainEnemy--;
		score += 300;
		dto.setScore(score);
		mContext.laScore.setText("" + score);
		e.setState(1); // 화살에 맞은 적 상태
		setIcon(bombRed);
		mContext.remove(e); // 적을 메모리에서 사라지게 한다.(그러나 Garbage Collection이 즉이 발동되지 않아 적이 안보이지만 존재함, clearArrowed 메소드에서 삭제)
		mContext.repaint();
		if (mContext.remainEnemy <= 0 && mContext.remainEnemy2 <= 0 && mContext.remainEnemy <= 0) {
			mContext.gameWin(dto);
		}
	}

	@Override
	public void attak(Enemy2 e2) { // slime
		state = 1; // 적을 맞춘 화살
		mContext.remainEnemy2--;
		score += 100;
		dto.setScore(score);
		mContext.laScore.setText("" + score);
		e2.setState(1); // 화살에 맞은 적 상태
		setIcon(bombRed);
		mContext.remove(e2); // 적을 메모리에서 사라지게 한다.(그러나 Garbage Collection이 즉이 발동되지 않아 적이 안보이지만 존재함, clearArrowed 메소드에서 삭제)
		mContext.repaint();
		if (mContext.remainEnemy <= 0 && mContext.remainEnemy2 <= 0 && mContext.remainEnemy <= 0) {
			mContext.gameWin(dto);
		}
	}

	@Override
	public void attak(Enemy3 e3) { // ballok
		state = 1; // 적을 맞춘 화살
		mContext.remainEnemy3--;
		score += 500;
		dto.setScore(score);
		mContext.laScore.setText("" + score);
		e3.setState(1); // 화살에 맞은 적 상태
		setIcon(bombRed);
		mContext.remove(e3); // 적을 메모리에서 사라지게 한다.(그러나 Garbage Collection이 즉이 발동되지 않아 적이 안보이지만 존재함, clearArrowed 메소드에서 삭제)
		mContext.repaint();
		if (mContext.remainEnemy <= 0 && mContext.remainEnemy2 <= 0 && mContext.remainEnemy <= 0) {
			mContext.gameWin(dto);
		}
	}

	private void clearArrow() {
		try {
			mContext.getPlayer().getArrowList().remove(this); // 화살 객체 메모리에서 날리기
			mContext.remove(this); // MainFrame의 화살 메모리에서 소멸된다.
			mContext.repaint(); // MainFrame의 전체를 다시 그린다.
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
				// 화살 객체 메모리에서 날리기
				mContext.getPlayer().getArrowList().remove(this);
				mContext.getEnemys().remove(removeEnemy); // MainFrame의 enemy 삭제
				mContext.getEnemys2().remove(removeEnemy2); // MainFrame의 enemy2 삭제
				mContext.getEnemys3().remove(removeEnemy3); // MainFrame의 enemy3 삭제
				mContext.remove(this);
				mContext.repaint();
			} catch (Exception e) {
			}
		}).start();
	}
}
