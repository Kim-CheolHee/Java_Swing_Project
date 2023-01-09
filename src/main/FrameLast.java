package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.*;

import lombok.Getter;
import lombok.Setter;
import main.music.BGMLast;
import main.util.GameDAO;
import main.util.GameDTO;

@Getter
@Setter
public class FrameLast extends JFrame {

	private JLabel backgroundMap;
	private BGMLast bgm;
	private JLabel player;
	private ImageIcon playerL;
	private JLabel idfield;
	private JLabel scorefield;
	private FrameStart frameStart;
	public GameDTO dto;

	public FrameLast(GameDTO dto) {
		this.dto = dto;
		initObject();
		initSetting();
		setVisible(true);
	}

	private void initObject() { // 컴포넌트들을 집어넣는 메소드.
		backgroundMap = new JLabel(new ImageIcon("image/backgroudLast.png"));
		setContentPane(backgroundMap); // jframe -> pane -> label 이 아닌 jframe -> label로 셋팅.
		customCursor();

		bgm = new BGMLast();
		bgm.playBGM("bgmEnding.wav");

		// 플레이어 이미지 화면에 구현
		playerL = new ImageIcon("image/archerL.png");
		Image img = playerL.getImage();
		Image changeImg = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeImg);
		player = new JLabel(changeIcon);
		player.setSize(120, 120);
		player.setLocation(110, 480);
		add(player);

		// 플레이어 아이디 화면에 구현
		idfield = new JLabel(dto.getId(), SwingConstants.CENTER);
		idfield.setLocation(290, 525);
		idfield.setSize(130, 40);
		idfield.setForeground(Color.BLUE);
		idfield.setFont(new Font("Dialog", Font.BOLD, 20));
		add(idfield);

		// 플레이어 점수 화면에 구현
		scorefield = new JLabel(""+dto.getScore(), SwingConstants.CENTER);
		scorefield.setLocation(855, 525);
		scorefield.setSize(140, 40);
		scorefield.setForeground(Color.BLUE);
		scorefield.setFont(new Font("Dialog", Font.BOLD, 20));
		add(scorefield);
		
		// 점수 db에 저장
		GameDAO dao = new GameDAO();
		dao.addScore(""+dto.getScore(), dto.getId());

	}

	private void initSetting() { // 프레임 기본 셋팅을 담당하는 메소드.
		setTitle("KimCheolHee 230108_2.0v");
		setSize(1000, 640);
		setLayout(null);
		setLocationRelativeTo(null); // jframe 창을 모니터 중앙에 배치.
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void customCursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage("image/mouseCursorBlueSnail.png");
		Point point = new Point(20, 20);
		Cursor cursor = toolkit.createCustomCursor(cursorImage, point, "BlueSnail");
		backgroundMap.setCursor(cursor);
	}

}
