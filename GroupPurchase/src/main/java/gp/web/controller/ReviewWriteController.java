package gp.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gp.web.entity.Review;
import gp.web.service.ReviewService;

@WebServlet("/writeReview")
public class ReviewWriteController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		HttpSession session = req.getSession();
		if(loginCheck(session) == false)
		{
			resp.sendRedirect("./login");
			return;
		}
		
		String writerNum = null;
		if(session.getAttribute("loginNum")!= null)
			writerNum = (String) session.getAttribute("loginNum");
		
		String evaluateeNum = null;
		String strPostNum = null;

		evaluateeNum = req.getParameter("evaluateeNum");
		strPostNum = req.getParameter("postNum");
		
		
		if(evaluateeNum == null || evaluateeNum.equals("") || strPostNum == null || strPostNum.equals("") || strPostNum.equals("0"))
		{
			req.getSession().setAttribute("messageType", "���� �޽���");
			req.getSession().setAttribute("messageContent", "�����ͺ��̽� ������ �߻��߽��ϴ�.");
			resp.sendRedirect("./home");
			return;
		}
		
		req.setAttribute("writerNum", writerNum);
		req.setAttribute("evaluateeNum", evaluateeNum);
		req.setAttribute("postNum", strPostNum);
		req.getRequestDispatcher("/WEB-INF/view/review/reviewWrite.jsp").forward(req, resp); 
		
		
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
