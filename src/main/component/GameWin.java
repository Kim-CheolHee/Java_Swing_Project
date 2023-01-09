package main.component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.FrameMain;
import main.util.GameDTO;

public class GameWin extends JLabel {

	// ������ ��������
	private FrameMain mContext;
	public GameDTO dto;

	// ��ġ ����
	private int x;
	private int y;

	// ������ ����
	private boolean down;

	private ImageIcon gameOver;

	public GameWin(FrameMain mContext, GameDTO dto) {
		this.mContext = mContext;
		this.dto = dto;
		initObject();
		initSetting();
	}

	private void initObject() {
		gameOver = new ImageIcon("image/GameWinText.png");
	}

	private void initSetting() {
		down = false;

		x = 150;
		y = 150;

		setIcon(gameOver);
		setSize(700, 187);
		setLocation(150, 50);

	}
	
}