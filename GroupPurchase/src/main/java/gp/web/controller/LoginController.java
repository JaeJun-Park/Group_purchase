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
		
		System.out.println("login");
		String idT = req.getParameter("id");
		String pwT = req.getParameter("pw");
	
		String id = "";
		if(idT != null && !idT.equals(""))
			id = idT;
		
		String pw = "";
		if(pwT != null && !pwT.equals(""))
			pw = pwT;
		
		System.out.println(id);
		System.out.println(pw);
		
		StudentService serv = new StudentService();
		Student stu = new Student();
		
		stu = serv.login(id, pw);
		
		if(stu != null)
		{
			System.out.println("로그인성공");
			HttpSession session = req.getSession();		
			session.setAttribute("isLogin", true);
			session.setAttribute("loginNum", stu.getStudentNum());
			session.setAttribute("student", stu);
			resp.sendRedirect("./home");
		}
		else
		{
			System.out.println("로그인실패");
			req.getRequestDispatcher("/WEB-INF/view/student/login.jsp").forward(req, resp);
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
