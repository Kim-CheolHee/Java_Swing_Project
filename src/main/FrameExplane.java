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

	private void initObject() { // ������Ʈ���� ����ִ� �޼ҵ�.
		AddPanel1(c);
		AddPanel2(c);
		customCursor();
	}

	private void initSetting() { // ������ �⺻ ������ ����ϴ� �޼ҵ�.
		setTitle("KimCheolHee 230108_2.0v");
		setSize(400, 500);
		c.setLayout(null);
		setLocation(320, 150);
	}

	public void AddPanel1(Container c) {
		panel1.setBounds(0, 0, 400, 130);
		panel1.setBackground(Color.cyan);

		// �÷��̾� �̹��� ȭ�鿡 ����
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

		// �� �Ű������� html �±� ��� ����
		textField = new JLabel(
				"<html>\r\n" + "<body>\r\n" + "    <h1 style=\"color: blue; text-align: center;\">���Ӽ���</h1>\r\n"
						+ "    <h2>���۹��</h2>\r\n" + "    <h3>  1. Ű���� ��, ��, ��, �� ��ư�� ���� �ɸ��͸� �����δ�.</h2>\r\n"
						+ "    <h3>  2. �����̽��ٸ� ������ ȭ���� �� �� �ִ�.</h2>\r\n" + "    <h2>���ӱ�Ģ</h2>\r\n"
						+ "    <h3>  1. �ɸ��Ͱ� ���Ϳ� ������ GameOver.</h2>\r\n" + "    <h3>  2. ���͸� ��� ������ �¸�!</h2>\r\n"
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
