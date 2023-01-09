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
	// 자바 빈 파일이 아님
	public Connection con; // 데이터베이스와의 연결
	public Statement stmt; // 구문 or 실행문(인파라미터가 없는 정적 쿼리문)
	public PreparedStatement psmt; // 인파라미터가 있는 동적 쿼리문(주로 사용)
	public ResultSet rs; // select 쿼리문 결과 저장(게시판에서 검색 결과)
	public ResultSet rs2;
	int result = 0;

	public static Connection connection() {

		Connection connection = null; // db접속 객체
		try {
			Class.forName("com.mysql.jdbc.Driver"); // mysql 연결 드라이브
			
//			String url = "jdbc:mysql://localhost:3306/jsp"; // MySQL localhost DB
			String url = "jdbc:mysql://kimcheolhee-mysql.mysql.database.azure.com:3306/azuredatabase?characterEncoding=UTF-8";
			String id = "manager";
//			String pass = "1234";
			String pass = "1q2w3e4r1!";
			connection = DriverManager.getConnection(url, id, pass);
			System.out.println("MySQL-DB 연결 성공(기본 생성자)");
		} catch (Exception e) {
			System.out.println("MySQL-DB 연결 실패");
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
			System.out.println("아이디 중복여부 확인 예외 발생(checkId)");
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
			System.out.println("JDBC 자원 해제 성공");
		} catch (Exception e) {
			System.out.println("JDBC 자원 해제 실패");
		}
	}

}