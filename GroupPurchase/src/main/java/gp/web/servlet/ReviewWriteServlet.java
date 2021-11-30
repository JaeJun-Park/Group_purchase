package gp.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gp.web.service.ReviewService;
import gp.web.service.StudentService;

public class ReviewWriteServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		String writerNum = req.getParameter("writerNum");
		String evaluateeNum = req.getParameter("evaluateeNum");
		String strPostNum = req.getParameter("postNum");
		String strRating = req.getParameter("rating");
		String comment = req.getParameter("comment");
		
		if(strRating== null || strRating.equals("") ||comment== null || comment.equals(""))
		{
			req.getSession().setAttribute("messageType", "오류 메시지");
			req.getSession().setAttribute("messageContent", "모든 내용을 입력하세요.");
			resp.sendRedirect("./writeReview?postNum=" +strPostNum +"&evaluateeNum=" + evaluateeNum);
			return;
		}
		int postNum = Integer.parseInt(strPostNum);
		float rating = Integer.parseInt(strRating);
		int result = new ReviewService().writeReview(writerNum, evaluateeNum, postNum, rating, comment);
		if(result == 1)
		{
			req.getSession().setAttribute("messageType", "성공 메시지");
			req.getSession().setAttribute("messageContent", "리뷰 작성에 성공했습니다.");
			req.getSession().setAttribute("isLogin", true);

			resp.sendRedirect("./home");
			return;
		}
		else
		{
			req.getSession().setAttribute("messageType", "오류 메시지");
			req.getSession().setAttribute("messageContent", "리뷰 작성에 실패했습니다.");
			resp.sendRedirect("./home");
		}
			
	}
}
