package gp.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/chatpage")
public class ChatpageController extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		boolean isLogin = false;
		String studentNum = null;
		if(session.getAttribute("isLogin")!= null)
			isLogin = (boolean) session.getAttribute("isLogin");
		if(session.getAttribute("loginNum") != null)
			studentNum = (String)session.getAttribute("loginNum");
		
		if(isLogin == false ||studentNum == null)
		{
			req.getSession().setAttribute("messageType", "오류 메시지");
			req.getSession().setAttribute("messageContent", "로그인이 되어있지 않습니다");
			resp.sendRedirect("./login");
		}
		else
		{
			req.setAttribute("isLogin", isLogin);
			req.setAttribute("studentNum", studentNum);
			System.out.println(studentNum);
			
			req.getRequestDispatcher("/WEB-INF/view/student/chatpage.jsp").forward(req, resp); 
		}
	}
	

}
