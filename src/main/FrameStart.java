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

	private void initObject() { // 컴포넌트들을 집어넣는 메소드.
		backgroundMap = new JLabel(new ImageIcon("image/backgroudFirst.png"));
		setContentPane(backgroundMap); // jframe -> pane -> label 이 아닌 jframe -> label로 셋팅.
		customCursor();

		bgm = new BGMFirst();
		bgm.playBGM("bgmOpening.wav");

		add(addImageId()); // 아이디 라벨 넣기
		add(addTextFieldId()); // 아이디 입력 필드
		add(addBtnLogin()); // 로그인 버튼
		add(addBtnExplane()); // 게임설명 버튼
	}

	private void initSetting() { // 프레임 기본 셋팅을 담당하는 메소드.
		setTitle("KimCheolHee 230108_2.0v");
		setSize(1000, 640);
		setLayout(null);
		setLocationRelativeTo(null); // jframe 창을 모니터 중앙에 배치.
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void initListener() {
		// KeyAdapter(추상클래스) 활용 KeyListener의 메소드 중 필요한 메소드만 사용.
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!fieldId.getText().equals("")) {
					// GameDTO 객체 생성 후 아이디 값을 dto에 저장
					GameDTO dto = new GameDTO();
					dto.setId(fieldId.getText());
					// GameDAO 객체 생성 후 addId 메소드를 호출하여 db연결 및 아이디 저장
					GameDAO dao = new GameDAO();
					if(dao.checkId(fieldId) == 0) { // result 1(중복된 아이디) 0(db에 없는 아이디)
						dao.addId(fieldId);
						bgm.stopBGM();
						new FrameMain(dto);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "중복된 아이디 입니다.", "알림창", JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "아이디를 입력하세요.", "알림창", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		btnExplane.addMouseListener(new MouseAdapter() {
			@Override // 마우스 클릭 시 FrameExplane 프레임 생성
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
