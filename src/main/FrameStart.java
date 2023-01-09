package main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import lombok.*;
import main.music.BGMFirst;
import main.util.*;

@Getter
@Setter
public class FrameStart extends JFrame {

	private JLabel backgroundMap;
	private BGMFirst bgm;
	private JLabel labelId;
	private ImageIcon imageId;
	private JButton btnLogin;
	private ImageIcon imageLogin;
	private JButton btnExplane;
	private ImageIcon imageExplane;
	private JTextField fieldId;

	public FrameStart() {
		initObject();
		initSetting();
		initListener();
		setVisible(true);
	}

	private void initObject() { // ������Ʈ���� ����ִ� �޼ҵ�.
		backgroundMap = new JLabel(new ImageIcon("image/backgroudFirst.png"));
		setContentPane(backgroundMap); // jframe -> pane -> label �� �ƴ� jframe -> label�� ����.
		customCursor();

		bgm = new BGMFirst();
		bgm.playBGM("bgmOpening.wav");

		add(addImageId()); // ���̵� �� �ֱ�
		add(addTextFieldId()); // ���̵� �Է� �ʵ�
		add(addBtnLogin()); // �α��� ��ư
		add(addBtnExplane()); // ���Ӽ��� ��ư
	}

	private void initSetting() { // ������ �⺻ ������ ����ϴ� �޼ҵ�.
		setTitle("KimCheolHee 230108_2.0v");
		setSize(1000, 640);
		setLayout(null);
		setLocationRelativeTo(null); // jframe â�� ����� �߾ӿ� ��ġ.
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void initListener() {
		// KeyAdapter(�߻�Ŭ����) Ȱ�� KeyListener�� �޼ҵ� �� �ʿ��� �޼ҵ常 ���.
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!fieldId.getText().equals("")) {
					// GameDTO ��ü ���� �� ���̵� ���� dto�� ����
					GameDTO dto = new GameDTO();
					dto.setId(fieldId.getText());
					// GameDAO ��ü ���� �� addId �޼ҵ带 ȣ���Ͽ� db���� �� ���̵� ����
					GameDAO dao = new GameDAO();
					if(dao.checkId(fieldId) == 0) { // result 1(�ߺ��� ���̵�) 0(db�� ���� ���̵�)
						dao.addId(fieldId);
						bgm.stopBGM();
						new FrameMain(dto);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "�ߺ��� ���̵� �Դϴ�.", "�˸�â", JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "���̵� �Է��ϼ���.", "�˸�â", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		btnExplane.addMouseListener(new MouseAdapter() {
			@Override // ���콺 Ŭ�� �� FrameExplane ������ ����
			public void mouseClicked(MouseEvent e) {
				new FrameExplane();
			}
		});
	}

	private Component addImageId() {
		imageId = new ImageIcon("image/idLabel.png");
		labelId = new JLabel(imageId);
		labelId.setSize(50, 30);
		labelId.setLocation(480, 230);
		return labelId;
	}

	private Component addTextFieldId() {
		fieldId = new JTextField(20);
		fieldId.setSize(180, 25);
		fieldId.setLocation(545, 235);
		return fieldId;
	}

	private Component addBtnLogin() {
		imageLogin = new ImageIcon("image/btnLogin.png");
		btnLogin = new JButton(imageLogin);
		btnLogin.setSize(100, 55);
		btnLogin.setLocation(745, 230);
		return btnLogin;
	}

	private Component addBtnExplane() {
		imageExplane = new ImageIcon("image/btnExplane.png");
		btnExplane = new JButton(imageExplane);
		btnExplane.setSize(100, 40);
		btnExplane.setLocation(735, 350);
		return btnExplane;
	}

	private void customCursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage("image/mouseCursorRedRibbonPig.png");
		Point point = new Point(20, 20);
		Cursor cursor = toolkit.createCustomCursor(cursorImage, point, "redRibbonPig");
		backgroundMap.setCursor(cursor);
	}

	public static void main(String[] args) {
		new FrameStart();
	}

}
