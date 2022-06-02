package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// action을 꺼낸다
		String action = request.getParameter("action");
		

		if ("joinForm".equals(action)) {//회원가입 폼
			System.out.println("UserController>joinForm");
			
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
		
		}else if ("join".equals(action)) {//회원가입
			System.out.println("UserController>join");
			
			//파라미터 꺼내기 * 4
			String id =request.getParameter("id");
			String name =request.getParameter("name");
			String password =request.getParameter("password");
			String gender =request.getParameter("gender");
			
			System.out.println(id);
			System.out.println(name);
			System.out.println(password);
			System.out.println(gender);
			
			//0x333 = Vo 만들기
			UserVo userVo = new UserVo(id, name, password, gender);
			
			UserDao userDao = new UserDao();
			userDao.insert(userVo);
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
		
		}else if ("loginForm".equals(action)) {//로그인 폼
			System.out.println("UserController>loginForm");
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
		
		}else if ("login".equals(action)) {//로그인
			System.out.println("UserController>login");
			
			//파라미터 꺼내기 * 2
			String id =request.getParameter("id");
			String password =request.getParameter("password");
		
			System.out.println(id);
			System.out.println(password);
			
			//0x333 = Vo 만들기
			//UserVo userVo = new UserVo(id, password);-->하고 생성자 만들어줘도 돼고,
			UserVo userVo = new UserVo();
			userVo.setId(id);
			userVo.setPassword(password); //-->2개 밖에 안되니까 set으로 집어넣어줘도 됨
			
			//dao
			UserDao userDao = new UserDao();
			UserVo authUser = userDao.getUser(userVo); //id,password --> user 전체
			
			//authUser 주소값이 있으면-->로그인 성공
			//authUser null이면-->로그인 실패
			if(authUser==null) {
				System.out.println("로그인 실패");
				
			}else {
				System.out.println("로그인 성공");
				
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authUser);
				
				//메인 리다이렉트
				WebUtil.redirect(request, response, "/mysite2/main");
				
				
			}
			
		}else if("logout".equals(action)) {
			System.out.println("UserController>logout");
			
			//세션값을 지운다
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			//메인으로 리다이렉트
			WebUtil.redirect(request, response, "/mysite2/main");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
