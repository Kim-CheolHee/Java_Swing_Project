package main.state;

import main.component.Enemy;
import main.component.Enemy2;
import main.component.Enemy3;

public interface Moveable { // �ɸ����� �������� �����ϴ� �������̽�(��, ��, ��, ��)
	public abstract void left();

	public abstract void right();

	public abstract void up();

	// default�� ����ϸ� �������̽��� ��ü�� �ִ� �޼ҵ带 ���� �� ����.(�ڹٴ� ���� ����� �ȵǱ⿡ Adapter ��뼺 ����)
	// �� default Ÿ���� �޼ҵ�� �������� �ǹ����� ���.
	default public void down() {
	};

	// MainFrame���� spacebar�� ������ �۵��ϴ� �޼ҵ� ������ �����Ѵ�.
	default public void attack() {
	};

	// ȭ���� ���� ���߸� �۵��ϴ� �޼ҵ�.(Arrow.java)
	default public void attak(Enemy e) {
	};

	default public void attak(Enemy2 e2) {
	}

	default public void attak(Enemy3 e3) {
	}
}
