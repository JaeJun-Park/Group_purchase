package gp.web.servlet;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gp.web.service.ChatroomService;


@WebServlet("/ChatSubmitServlet")
public class ChatSubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String studentNum = request.getParameter("studentNum");
		String roomNum = request.getParameter("roomNum");
		String text = request.getParameter("chatContent");
		
		if(studentNum == null || studentNum.equals("") || roomNum == null || roomNum.equals("") || text == null || text.equals(""))
		{
			response.getWriter().write("0");
		}
		else
		{
			int rNum = Integer.parseInt(roomNum);
			text = URLDecoder.decode(text, "UTF-8");
			response.getWriter().write(new ChatroomService().submit(studentNum, rNum, text)+"");
		}
	}

}
