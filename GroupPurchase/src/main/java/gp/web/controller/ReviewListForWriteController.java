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

@WebServlet("/reviewlistForWrite")
public class ReviewListForWriteController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		HttpSession session = req.getSession();
		if(loginCheck(session) == false)
		{
			resp.sendRedirect("./login");
			return;
		}
		
		String studentNum = null;
		if(session.getAttribute("loginNum")!= null)
			studentNum = (String) session.getAttribute("loginNum");	
		
		
		if(studentNum == null || studentNum.equals(""))
		{
			req.getSession().setAttribute("messageType", "���� �޽���");
			req.getSession().setAttribute("messageContent", "�����ͺ��̽� ������ �߻��߽��ϴ�.");
			resp.sendRedirect("./home");
			return;
		}
		
		req.setAttribute("studentNum", studentNum);
		req.getRequestDispatcher("/WEB-INF/view/review/listForWrite.jsp").forward(req, resp); 
		
		
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
