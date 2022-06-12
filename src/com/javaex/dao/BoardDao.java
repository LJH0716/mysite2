package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {

	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			// System.out.println("접속성공");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 리스트
	public List<BoardVo> getList() {
		List<BoardVo> boardList = new ArrayList<BoardVo>();

		getConnection();

		try {
			// SQL 문 준비
			String query = "";
			query += " select  b.no, ";
			query += "         b.title, ";
			query += "         b.content, ";
			query += "         b.hit, ";
			query += "         b.reg_date, regDate ";
			query += "         b.user_no, userNo ";
			query += "         u.name ";
			query += " from board b, users u ";
			query += " where b.user_no = u.no ";
			query += " order by no desc ";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			// 실행

			rs = pstmt.executeQuery();

			// 4.결과처리
			// 리스트로 만들기

			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("regDate");
				int userNo = rs.getInt("userNo");

				BoardVo boardVo = new BoardVo(no, title, content, hit, name, regDate, userNo);
				boardList.add(boardVo);

			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();

		return boardList;
	}

	// 글쓰기
	public int write(BoardVo boardVo) {

		int count = -1;

		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행

			// SQL문 준비
			String query = "";
			query += " insert into board ";
			query += " values (seq_board_no.nextval, ?, ?, 0, sysdate, ?) ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getUserNo());

			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("[" + count + "건이 등록되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;

	}

	// 삭제
	public int delete(int no) {

		int count = 0;
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행

			// SQL문 준비
			String query = "";
			query += " delete from board ";
			query += " where no = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("[" + count + "건이 삭제 되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;
	}

	// 수정
	public int update(BoardVo boardVo) {

		int count = -1;
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행

			// SQL문 준비
			String query = "";
			query += " update board ";
			query += " set title = ?, ";
			query += "     content = ? ";
			query += " where no = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			// 실행
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getNo());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("[" + count + "건이 수정 되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;

	}

	// 사용자 정보 가져오기
	public BoardVo getBoard(int no) {

		BoardVo boardVo = null;

		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행

			// SQL문 준비
			String query = "";
			query += " select  b.no, ";
			query += "         b.title, ";
			query += "         b.content, ";
			query += "         b.hit, ";
			query += "         b.reg_date, regDate ";
			query += "         b.user_no, userNo ";
			query += "         u.name ";
			query += " from board b, users u ";
			query += " where b.user_no = u.no ";
			query += " and b.no = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			pstmt.setInt(1, no);

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("regDate");
				int userNo = rs.getInt("userNo");
				String name = rs.getString("name");

				boardVo = new BoardVo(no, title, content, hit, regDate, name, userNo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return boardVo;

	}
	
	//조회수
	public int hit(BoardVo boardVo) {
		
		int count = -1;
		
		getConnection();
		
		try {
			String query = ""; 
			query += " update board ";
			query += " set hit = hit + 1 ";
			query += " where no = ? ";

			
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);
			

			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			System.out.println("[" + count + "건의 조회수가 업데이트 되었습니다.]");
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		return count;
	}
}