package gp.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gp.web.entity.Student;
import gp.web.service.ReviewService;
import gp.web.service.StudentService;

@WebServlet("/profile")
public class ProfileController extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String studentNum = null;
		HttpSession session = req.getSession();
		
		if(loginCheck(session) == false)
		{
			resp.sendRedirect("./login");
			return;
		}
		
		if(req.getParameter("studentNum") != null)
			studentNum = req.getParameter("studentNum");
		
		if(studentNum == null || studentNum.equals(""))
		{
			req.getSession().setAttribute("messageType", "���� �޽���");
			req.getSession().setAttribute("messageContent", "�����ͺ��̽� ������ �߻��߽��ϴ�.");
			resp.sendRedirect("./home");
			return;
		}
		else if(studentNum.equals("��"))
		{
			resp.sendRedirect("./mypage");
		}
		else
		{
			StudentService serv = new StudentService();
			Student stu = new Student();
			stu = serv.getStudent("studentNum", studentNum);
			ReviewService rvwServ = new ReviewService();
			int count = rvwServ.getReviewCount(stu.getStudentNum());
		
			req.setAttribute("student", stu);	
			req.setAttribute("count", count);
			req.getRequestDispatcher("/WEB-INF/view/student/profile.jsp").forward(req, resp); 
		}
	}
	
	private boolean loginCheck(HttpSession session)
	{
		boolean isLogin = false;
		if(session.getAttribute("isLogin")!= null)
			isLogin = (boolean) session.getAttribute("isLogin");
		
		if(isLogin == false)
		{
			session.setAttribute("messageType", "���� �޽���");
			session.setAttribute("messageContent", "�α����� �Ǿ����� �ʽ��ϴ�");
		}
		return isLogin;
	}
}
