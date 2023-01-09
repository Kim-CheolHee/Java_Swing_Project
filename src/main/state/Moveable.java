package main.state;

import main.component.Enemy;
import main.component.Enemy2;
import main.component.Enemy3;

public interface Moveable { // 케릭터의 움직임을 설정하는 인터페이스(상, 하, 좌, 우)
	public abstract void left();

	public abstract void right();

	public abstract void up();

	// default를 사용하면 인터페이스도 몸체가 있는 메소드를 만들 수 있음.(자바는 다중 상속이 안되기에 Adapter 사용성 제한)
	// 즉 default 타입의 메소드는 강제구현 의무에서 벗어남.
	default public void down() {
	};

	// MainFrame에서 spacebar를 누르면 작동하는 메소드 버블을 생성한다.
	default public void attack() {
	};

	// 화살이 적을 맞추면 작동하는 메소드.(Arrow.java)
	default public void attak(Enemy e) {
	};

	default public void attak(Enemy2 e2) {
	}

	default public void attak(Enemy3 e3) {
	}
}
