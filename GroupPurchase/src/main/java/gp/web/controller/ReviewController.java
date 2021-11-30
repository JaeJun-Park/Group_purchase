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

@WebServlet("/detailReview")
public class ReviewController extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		if(loginCheck(session) == false)
		{
			resp.sendRedirect("./login");
			return;
		}
		
		int reviewNum = 0;
		String strNum = null;
		String evaluateeNum = null;
		
		strNum = req.getParameter("reviewNum");
		evaluateeNum = req.getParameter("evaluateeNum");
		
		if(evaluateeNum == null || evaluateeNum.equals("") || strNum == null || strNum.equals(""))
		{
			req.getSession().setAttribute("messageType", "오류 메시지");
			req.getSession().setAttribute("messageContent", "데이터베이스 오류가 발생했습니다.");
			resp.sendRedirect("./home");
			return;
		}
		reviewNum = Integer.parseInt(strNum);
		if(reviewNum <= 0)
		{
			req.getSession().setAttribute("messageType", "오류 메시지");
			req.getSession().setAttribute("messageContent", "데이터베이스 오류가 발생했습니다.");
			resp.sendRedirect("./home");
			return;
		}
		
		Review rvw = null;
		ReviewService serv = new ReviewService();
		rvw = serv.getReview(reviewNum, evaluateeNum);
		
		if(rvw == null)
		{
			req.getSession().setAttribute("messageType", "오류 메시지");
			req.getSession().setAttribute("messageContent", "데이터베이스 오류가 발생했습니다.");
			resp.sendRedirect("./home");
			return;
		}
		else
		{
			req.setAttribute("review", rvw);
			req.getRequestDispatcher("/WEB-INF/view/review/review.jsp").forward(req, resp); 
		}
	}
	private boolean loginCheck(HttpSession session)
	{
		boolean isLogin = false;
		if(session.getAttribute("isLogin")!= null)
			isLogin = (boolean) session.getAttribute("isLogin");
		
		if(isLogin == false)
		{
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "로그인이 되어있지 않습니다");
		}
		return isLogin;
	}
}
