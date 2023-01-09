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

	private void initObject() { // ������Ʈ���� ����ִ� �޼ҵ�.
		backgroundMap = new JLabel(new ImageIcon("image/backgroudLast.png"));
		setContentPane(backgroundMap); // jframe -> pane -> label �� �ƴ� jframe -> label�� ����.
		customCursor();

		bgm = new BGMLast();
		bgm.playBGM("bgmEnding.wav");

		// �÷��̾� �̹��� ȭ�鿡 ����
		playerL = new ImageIcon("image/archerL.png");
		Image img = playerL.getImage();
		Image changeImg = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeImg);
		player = new JLabel(changeIcon);
		player.setSize(120, 120);
		player.setLocation(110, 480);
		add(player);

		// �÷��̾� ���̵� ȭ�鿡 ����
		idfield = new JLabel(dto.getId(), SwingConstants.CENTER);
		idfield.setLocation(290, 525);
		idfield.setSize(130, 40);
		idfield.setForeground(Color.BLUE);
		idfield.setFont(new Font("Dialog", Font.BOLD, 20));
		add(idfield);

		// �÷��̾� ���� ȭ�鿡 ����
		scorefield = new JLabel(""+dto.getScore(), SwingConstants.CENTER);
		scorefield.setLocation(855, 525);
		scorefield.setSize(140, 40);
		scorefield.setForeground(Color.BLUE);
		scorefield.setFont(new Font("Dialog", Font.BOLD, 20));
		add(scorefield);
		
		// ���� db�� ����
		GameDAO dao = new GameDAO();
		dao.addScore(""+dto.getScore(), dto.getId());

	}

	private void initSetting() { // ������ �⺻ ������ ����ϴ� �޼ҵ�.
		setTitle("KimCheolHee 230108_2.0v");
		setSize(1000, 640);
		setLayout(null);
		setLocationRelativeTo(null); // jframe â�� ����� �߾ӿ� ��ġ.
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
