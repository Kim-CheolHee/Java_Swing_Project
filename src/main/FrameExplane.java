package main;

import java.awt.*;

import javax.swing.*;

import lombok.*;

@Getter
@Setter
public class FrameExplane extends JFrame {

	private Container c = getContentPane();
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JLabel player;
	private ImageIcon master;
	private JLabel textField;

	public FrameExplane() {
		initObject();
		initSetting();
		setVisible(true);
	}

	private void initObject() { // 컴포넌트들을 집어넣는 메소드.
		AddPanel1(c);
		AddPanel2(c);
		customCursor();
	}

	private void initSetting() { // 프레임 기본 셋팅을 담당하는 메소드.
		setTitle("KimCheolHee 230108_2.0v");
		setSize(400, 500);
		c.setLayout(null);
		setLocation(320, 150);
	}

	public void AddPanel1(Container c) {
		panel1.setBounds(0, 0, 400, 130);
		panel1.setBackground(Color.cyan);

		// 플레이어 이미지 화면에 구현
		master = new ImageIcon("image/archerL.png");
		Image img = master.getImage();
		Image changeImg = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeImg);
		player = new JLabel(changeIcon);
		player.setSize(50, 50);
		player.setLocation(0, 0);
		panel1.add(player);

		c.add(panel1);
	}

	public void AddPanel2(Container c) {
		panel2.setBounds(0, 130, 400, 370);
		panel2.setBackground(Color.yellow);

		// 라벨 매개변수에 html 태그 사용 가능
		textField = new JLabel(
				"<html>\r\n" + "<body>\r\n" + "    <h1 style=\"color: blue; text-align: center;\">게임설명</h1>\r\n"
						+ "    <h2>조작방법</h2>\r\n" + "    <h3>  1. 키보드 상, 하, 좌, 우 버튼을 눌러 케릭터를 움직인다.</h2>\r\n"
						+ "    <h3>  2. 스페이스바를 누르면 화살을 쏠 수 있다.</h2>\r\n" + "    <h2>게임규칙</h2>\r\n"
						+ "    <h3>  1. 케릭터가 몬스터에 닿으면 GameOver.</h2>\r\n" + "    <h3>  2. 몬스터를 모두 잡으면 승리!</h2>\r\n"
						+ "</body>\r\n" + "</html>");
		panel2.add(textField);

		c.add(panel2);
	}
	
	private void customCursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage("image/mouseCursorBlueSnail.png");
		Point point = new Point(20, 20);
		Cursor cursor = toolkit.createCustomCursor(cursorImage, point, "BlueSnail");
		c.setCursor(cursor);
	}

}
