package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	// 필드
	private static final long serialVersionUID = 1L;

	// default 생성자

	// 메서드
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 포스트 방식일 때 한글 깨짐 방지
		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");
		System.out.println(action);

		if ("list".equals(action)) { // 리스트

			BoardDao boardDao = new BoardDao();
			List<BoardVo> boardList = boardDao.getList();
			System.out.println(boardList);

			request.setAttribute("boardList", boardList);

			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");

		} else if ("read".equals(action)) { // 읽기

			int no = Integer.parseInt(request.getParameter("no"));

			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getBoard(no);
			
			//조회수(읽을 경우 +1)
			

			request.setAttribute("boardVo", boardVo);

			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");

		} else if ("modifyForm".equals(action)) { // 수정폼

			int no = Integer.parseInt(request.getParameter("no"));

			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getBoard(no);

			request.setAttribute("boardVo", boardVo);

			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");

		} else if ("modify".equals(action)) { // 수정

			int no = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			BoardVo boardVo = new BoardVo();
			boardVo.setNo(no);
			boardVo.setTitle(title);
			boardVo.setContent(content);

			BoardDao boardDao = new BoardDao();
			boardDao.update(boardVo);

			WebUtil.redirect(request, response, "/mysite2/board?action=list");

			
		} else if ("writeForm".equals(action)) { // 글쓰기폼
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");

			
		} else if ("write".equals(action)) { // 글쓰기

			
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			int no = authUser.getNo();

			String title = request.getParameter("title");
			String content = request.getParameter("content");

			BoardDao boardDao = new BoardDao();
			boardDao.write(new BoardVo(no, content, title));

			WebUtil.redirect(request, response, "/mysite2/board?action=list");

			
		} else if ("delete".equals(action)) { // 삭제

			
			int no = Integer.parseInt(request.getParameter("no"));

			BoardDao boardDao = new BoardDao();
			boardDao.delete(no);

			WebUtil.redirect(request, response, "/mysite2/board?action=list");

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
