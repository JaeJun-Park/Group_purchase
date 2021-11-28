package gp.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gp.web.entity.Student;

@WebServlet("/update")
public class UpdateController extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
			HttpSession session = req.getSession();
			boolean isLogin = false;
			Student stu = null;
			if(session.getAttribute("isLogin")!= null)
				isLogin = (boolean) session.getAttribute("isLogin");
			if(session.getAttribute("student") != null)
				stu = (Student)session.getAttribute("student");
			
			if(isLogin == false || stu == null)
			{
				req.getSession().setAttribute("messageType", "오류 메시지");
				req.getSession().setAttribute("messageContent", "로그인이 되어있지 않습니다");
				resp.sendRedirect("./login");
			}
			else
			{
				req.setAttribute("student", stu);
				req.getRequestDispatcher("/WEB-INF/view/student/update.jsp").forward(req, resp); 
			}
		}
}
