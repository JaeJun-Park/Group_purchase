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

@WebServlet("/mypage")
public class MypageController extends HttpServlet
{
	@Override
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
			ReviewService rvwServ = new ReviewService();
			int count = rvwServ.getReviewCount(stu.getStudentNum());
			req.setAttribute("student", stu);
			req.setAttribute("isLogin", isLogin);
			req.setAttribute("count", count);
			req.getRequestDispatcher("/WEB-INF/view/student/mypage.jsp").forward(req, resp); 
		}
	}
}
