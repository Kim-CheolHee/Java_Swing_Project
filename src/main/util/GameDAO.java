package main.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTextField;

public class GameDAO {
	// �ڹ� �� ������ �ƴ�
	public Connection con; // �����ͺ��̽����� ����
	public Statement stmt; // ���� or ���๮(���Ķ���Ͱ� ���� ���� ������)
	public PreparedStatement psmt; // ���Ķ���Ͱ� �ִ� ���� ������(�ַ� ���)
	public ResultSet rs; // select ������ ��� ����(�Խ��ǿ��� �˻� ���)
	public ResultSet rs2;
	int result = 0;

	public static Connection connection() {

		Connection connection = null; // db���� ��ü
		try {
			Class.forName("com.mysql.jdbc.Driver"); // mysql ���� ����̺�
			
//			String url = "jdbc:mysql://localhost:3306/jsp"; // MySQL localhost DB
			String url = "jdbc:mysql://kimcheolhee-mysql.mysql.database.azure.com:3306/azuredatabase?characterEncoding=UTF-8";
			String id = "manager";
//			String pass = "1234";
			String pass = "1q2w3e4r1!";
			connection = DriverManager.getConnection(url, id, pass);
			System.out.println("MySQL-DB ���� ����(�⺻ ������)");
		} catch (Exception e) {
			System.out.println("MySQL-DB ���� ����");
		}
		return connection;
	}

	public int checkId(JTextField id) {

		String data = id.getText();
		
		String sql = "SELECT * FROM gamedb";
		con = GameDAO.connection();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String check_id = rs.getString(1);
				if (check_id.equals(data)) {
					result = 1;
					break;
				} else {
					result = 0;
				}
			}
		} catch (SQLException e1) {
			System.out.println("���̵� �ߺ����� Ȯ�� ���� �߻�(checkId)");
			e1.printStackTrace();
		}
		close();
		return result;
	}

	public int addId(JTextField id) {
		result = 0;
		int num = checkId(id);
		String user_id = id.getText();
		if (num != 1) {
			String sql = "INSERT INTO gamedb(id) VALUES( ? )";
			con = GameDAO.connection();
			try {
				psmt = con.prepareStatement(sql);
				psmt.setString(1, user_id);
				psmt.executeUpdate();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			result = 1;
		} else {
			result = 0;
		}
		close();
		return result;
	}

	public void addScore(String score, String id) {
		String user_score = score;
		String user_id = id;
		String sql = "UPDATE gamedb SET score=? WHERE id=?";
		con = GameDAO.connection();
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, user_score);
			psmt.setString(2, user_id);
			psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		close();
	}

	public void close() {
		try {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (psmt != null) psmt.close();
			if (con != null) con.close();
			System.out.println("JDBC �ڿ� ���� ����");
		} catch (Exception e) {
			System.out.println("JDBC �ڿ� ���� ����");
		}
	}

}