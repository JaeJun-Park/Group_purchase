package gp.web.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gp.web.entity.Review;
import gp.web.service.ReviewService;

public class ReviewListForWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String studentNum = request.getParameter("studentNum");
		if(studentNum == null || studentNum.equals("")) {
			response.getWriter().write("0");
		}
		else
		{
			try {
				response.getWriter().write(getList(studentNum));
			}catch (Exception e)
			{
				response.getWriter().write("");
			}
		}
	}
	
	public String getList(String studentNum)
	{
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ReviewService serv = new ReviewService(); 
		ArrayList<Review> reviewList = serv.getStudentNumCanWrite(studentNum);
		if(reviewList.size() == 0) return "";
		for(int i=0; i< reviewList.size(); i++)
		{
			result.append("[{\"value\":\"" + reviewList.get(i).getPostNum() + "\"},");
			result.append("{\"value\":\"" + reviewList.get(i).getEvaluateeNum() + "\"}]");
			if(i!=reviewList.size() -1) result.append(",");
			
		}
		result.append("], \"last\":\"" + reviewList.get(reviewList.size() -1).getReviewNum() + "\"}"); 
		return result.toString();
	}
}
