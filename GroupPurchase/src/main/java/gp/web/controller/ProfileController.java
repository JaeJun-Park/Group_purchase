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

@WebServlet("/profile")
public class ProfileController extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
		String studentNum = null;
		
		if(req.getParameter("studentNum") != null)
			studentNum = req.getParameter("studentNum");
		
		if(studentNum == null || studentNum.equals(""))
		{
			req.getSession().setAttribute("messageType", "오류 메시지");
			req.getSession().setAttribute("messageContent", "데이터베이스 오류가 발생했습니다.");
			resp.sendRedirect("./home");
			return;
		}
		else if(studentNum.equals("나"))
		{
			resp.sendRedirect("./mypage");
		}
		else
		{
			StudentService serv = new StudentService();
			Student stu = new Student();
			stu = serv.getStudent("studentNum", studentNum);
		
			req.setAttribute("student", stu);
			req.getRequestDispatcher("/WEB-INF/view/student/profile.jsp").forward(req, resp); 
		}
	}
}
