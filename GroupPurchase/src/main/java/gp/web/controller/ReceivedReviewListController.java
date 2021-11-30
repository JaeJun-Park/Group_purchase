package gp.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gp.web.entity.Review;
import gp.web.entity.Student;
import gp.web.service.ReviewService;
import gp.web.service.StudentService;

@WebServlet("/receivedReview")
public class ReceivedReviewListController extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		if(loginCheck(session) == false)
		{
			resp.sendRedirect("./login");
			return;
		}
		
		String studentNum = null;
		
		if(req.getParameter("studentNum") != null)
			studentNum = req.getParameter("studentNum");
		
		if(studentNum == null || studentNum.equals(""))
		{
			req.getSession().setAttribute("messageType", "���� �޽���");
			req.getSession().setAttribute("messageContent", "�����ͺ��̽� ������ �߻��߽��ϴ�.");
			resp.sendRedirect("./home");
			return;
		}
		Student stu = new Student();
		StudentService serv = new StudentService();
		stu = serv.getStudent("studentNum", studentNum);
		float avg = stu.getCredibility();
		ReviewService rvwServ = new ReviewService();
		int count = rvwServ.getReviewWriteCount(studentNum);
		
		if(session.getAttribute("loginNum").equals(studentNum))
		{
			req.setAttribute("test", true);
		}
		
		req.setAttribute("studentNum", studentNum);
		req.setAttribute("avg", avg);
		req.setAttribute("count", count);
		req.getRequestDispatcher("/WEB-INF/view/review/receivedReviewList.jsp").forward(req, resp); 

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
