package gp.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/chatroom")
public class ChatroomController extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		boolean isLogin = false;
		String roomNum = null;
		String studentNum = null;
		if(session.getAttribute("isLogin")!= null)
			isLogin = (boolean) session.getAttribute("isLogin");
		if(session.getAttribute("loginNum") != null)
			studentNum = (String)session.getAttribute("loginNum");
		if(req.getParameter("roomNum") != null)
			roomNum = req.getParameter("roomNum");
		
		
		if(isLogin == false ||studentNum == null || roomNum == null)
		{
			req.getSession().setAttribute("messageType", "오류 메시지");
			req.getSession().setAttribute("messageContent", "로그인이 되어있지 않습니다");
			resp.sendRedirect("./login");
		}
		else
		{
			req.setAttribute("isLogin", isLogin);
			req.setAttribute("studentNum", studentNum);
			req.setAttribute("roomNum", roomNum);			
			req.getRequestDispatcher("/WEB-INF/view/chat/chatroom.jsp").forward(req, resp); 
		}

	}

}
