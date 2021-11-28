package gp.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/home")
public class HomeController extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		if(session.getAttribute("isLogin") == null)
		{
			session.setAttribute("isLogin", false);
			session.setAttribute("loginNum", "");
			session.setAttribute("student", null);
		}
		boolean isLogin = (boolean) session.getAttribute("isLogin");
		String sNum = (String)session.getAttribute("loginNum");
		req.setAttribute("isLogin", isLogin);
		req.setAttribute("sNum", sNum);
		req.getRequestDispatcher("/WEB-INF/view/home.jsp").forward(req, resp);
	}
}