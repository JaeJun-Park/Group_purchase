package gp.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gp.web.entity.Student;
import gp.web.service.StudentService;

@WebServlet("/login")
public class LoginController extends HttpServlet{

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String idT = req.getParameter("id");
		String pwT = req.getParameter("pw");
	
		String id = "";
		if(idT != null && !idT.equals(""))
			id = idT;
		
		String pw = "";
		if(pwT != null && !pwT.equals(""))
			pw = pwT;
		
		StudentService serv = new StudentService();
		Student stu = new Student();
		
		stu = serv.login(id, pw);
		
		if(stu != null)
		{
			HttpSession session = req.getSession();		
			session.setAttribute("isLogin", true);
			session.setAttribute("loginNum", stu.getStudentNum());
			session.setAttribute("student", stu);
			resp.sendRedirect("./home");
		}
		else
		{
			req.getSession().setAttribute("messageType", "오류 메시지");
			req.getSession().setAttribute("messageContent", "아이디와 비밀번호가 일치하지 않습니다.");
			resp.sendRedirect("./login");
		}
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String log = req.getParameter("c");
		
		if(log == null)
		{
			req.getRequestDispatcher("/WEB-INF/view/student/login.jsp").forward(req, resp);
		}
		else if(log.equals("out"))
		{
			HttpSession  session = req.getSession();
			session.setAttribute("isLogin", false);
			session.setAttribute("loginNum", "");
			session.setAttribute("student", null);
			resp.sendRedirect("./home");
		}
	}
}
