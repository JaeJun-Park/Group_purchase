package gp.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gp.web.service.StudentService;

@WebServlet("/UserNumCheckServlet")
public class UserNumCheckServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		String userNum = req.getParameter("userNum");
		resp.getWriter().write(new StudentService().NumCheck(userNum) + "");
	}
}
